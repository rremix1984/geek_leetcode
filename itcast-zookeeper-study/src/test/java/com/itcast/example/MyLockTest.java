package com.itcast.example;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.Test;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@Slf4j
public class MyLockTest {
    public static final String ZK_IP_ADDRESS = "192.168.31.121:2181";
    // 计数器对象
    CountDownLatch countDownLatch = new CountDownLatch(1);
    public static final int TIMEOUT = 5000;
    // 连接对象
    ZooKeeper zooKeeper;
    private static final String LOCK_ROOT_PATH = "/Locks";
    private static final String LOCK_NODE_NAME = "Lock_";
    private String lockPath;

    // 打开zookeeper连接
    public MyLockTest() {
        try {
            zooKeeper = new ZooKeeper(ZK_IP_ADDRESS, TIMEOUT, new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                    if (event.getType() == Event.EventType.None) {
                        if (event.getState() == Event.KeeperState.SyncConnected) {
                            log.info("连接成功！");
                            countDownLatch.countDown();
                        }
                    }
                }
            });
            countDownLatch.await();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void acquireLock() throws Exception {
        // 创建锁节点
        createLock();

        // 尝试获取锁
        attemptLock();
    }

    // 创建锁节点
    public void createLock() throws Exception {
        // 判断Locks节点是否存在
        Stat stat = zooKeeper.exists(LOCK_ROOT_PATH, false);
        if (stat == null) {
            // arg2: 节点数据，不重要
            // arg3: 设定权限
            // arg4: 持久化or 临时
            zooKeeper.create(LOCK_ROOT_PATH, new byte[0],
                    ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }
        // 里面存的数据是：临时有序节点
        lockPath = zooKeeper.create(LOCK_ROOT_PATH + "/" + LOCK_NODE_NAME,
                new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        log.info("节点创建成功，lockPath -> {}", lockPath);
    }

    // 监视器对象，监视上一个节点是否被删除
    Watcher watcher = new Watcher() {
        @Override
        public void process(WatchedEvent event) {
            if (event.getType() == Event.EventType.NodeDeleted) {
                // 通知线程醒过来
                synchronized (this) {
                    notifyAll();
                }
            }
        }
    };

    // 尝试获取锁
    private void attemptLock() throws Exception {
        // 获取 /Locks 节点下的所有子节点
        List<String> children = zooKeeper.getChildren(LOCK_ROOT_PATH, false);

        // 对Locks所有子节点排序
        Collections.sort(children);
        int index = children.indexOf(lockPath.substring(LOCK_ROOT_PATH.length() + 1));

        // index == 0 代表有序节点是第 1 位的，代表获取锁成功
        // index != 0 代表获取锁失败!
        if (index == 0) {
            log.info("获取锁成功，直接返回~ ");
            return;
        } else {
            // 获取锁不成功，说明排队了
            // index - 1 上一个节点的索引位置
            // path 上一个节点路径
            String path = children.get(index - 1);
            Stat stat = zooKeeper.exists(LOCK_ROOT_PATH + "/" + path, watcher);
            // 有可能在上两行执行过程中，已经删除了上一个节点，那么 stat == null 了
            if (stat == null) {
                attemptLock();
            } else {
                // 有可能处于等待、执行两个状态之一
                // 当前客户端线程有可能在阻塞等待状态
                synchronized (watcher) {
                    watcher.wait();
                }
                // 接着获取锁
                attemptLock();
            }
        }
    }

    // 释放锁
    public void releaseLock() throws Exception {
        // 删除临时有序节点
        zooKeeper.delete(this.lockPath,-1);
        // 关闭连接对象
        zooKeeper.close();
        log.info("锁: {} 已经释放了 ", this.lockPath);
    }

    @Test
    public void test() throws Exception {
        MyLockTest lock = new MyLockTest();
        lock.createLock();
        Thread.sleep(5000);
    }

}