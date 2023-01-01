package com.itcast.zookeeper;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.data.Stat;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import static org.apache.zookeeper.CreateMode.PERSISTENT;
import static org.apache.zookeeper.ZooDefs.Ids.OPEN_ACL_UNSAFE;
import static org.apache.zookeeper.ZooDefs.Ids.READ_ACL_UNSAFE;
import static org.apache.zookeeper.ZooDefs.Perms.READ;
import static org.apache.zookeeper.ZooDefs.Perms.WRITE;

@Slf4j
public class ZKSetTest {
    public static final String ZK_IP_ADDRESS = "192.168.31.121:2181";
    public static final int TIMEOUT = 5000;

    ZooKeeper zooKeeper;

    @Before
    public void before() throws Exception {
        try {
            // 计数器对象
            CountDownLatch countDownLatch = new CountDownLatch(1);

            // 客户端延迟连接，需要用计数器对象将主线程阻塞、等待
            // arg1: 服务器ip端口
            // arg2: 客户端与服务器之间会话超时时间（毫秒）
            // arg3: 监视器对象
            // 当客户端连接成功后，会收到一条watch信息
            zooKeeper = new ZooKeeper(ZK_IP_ADDRESS, TIMEOUT, event -> {
                if (event.getState() == Watcher.Event.KeeperState.SyncConnected)
                    countDownLatch.countDown();
            });

            // 主线程阻塞等待连接对象，等待创建成功
            countDownLatch.await();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @After
    public void after() throws Exception {
        zooKeeper.close();
    }

//    @Test
    public void set1() throws Exception {
        // arg1: 节点路径
        // arg2: 修改的数据
        // arg3: 第三个参数 -1 代表版本号不再更新
//        zooKeeper.setData("/set/node1", "node11".getBytes(), -1);
        Stat stat = zooKeeper.setData("/set/node1", "node11".getBytes(), 14);
        log.info("{}", stat.getVersion());
    }

    @Test
    public void test2() throws Exception {
        zooKeeper.setData("/set/node1", "node13".getBytes(), -1, new AsyncCallback.StatCallback() {
            @Override
            public void processResult(int rc, String path, Object ctx, Stat stat) {
                // rc = 0 代表成功
                log.info("rc -> {}", rc);
                // 节点路径
                log.info("path -> {}", path);
                // 上下文参数对象
                log.info("ctx -> {}", ctx);
                // 当前节点的属性描述信息
                log.info("stat -> {}", stat);
            }
        }, "I am Context");
        Thread.sleep(10000);
        log.info("结束");
    }

}
