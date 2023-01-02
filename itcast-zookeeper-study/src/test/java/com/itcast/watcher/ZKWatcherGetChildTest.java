package com.itcast.watcher;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.junit.Before;
import org.junit.Test;

public class ZKWatcherGetChildTest extends BaseTest {

    @Before
    public void before() throws Exception {
        try {
            zooKeeper = new ZooKeeper(ZK_IP_ADDRESS, TIMEOUT, event -> {
                info("连接对象的参数!");
                if (event.getState() == Watcher.Event.KeeperState.SyncConnected) {
                    countDownLatch.countDown();
                }
                info("path -> {}", event.getPath());
                info("eventType -> {}", event.getType());
            });
            // 阻塞线程，等待连接成功
            countDownLatch.await();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void watcherGetData1() throws InterruptedException, KeeperException {
        // arg1: 节点路径
        // arg2: 使用连接对象中的watcher
        zooKeeper.getChildren("/watcher3", true);
        Thread.sleep(10000);
        info("结束");
    }

    @Test
    public void watcherGetData2() throws InterruptedException, KeeperException {
        // arg1: 节点路径
        // arg2: 使用连接对象中的watcher
        zooKeeper.getChildren("/watcher3", event -> {
            info("自定义watcher");
            info("path -> {}", event.getPath());
            info("eventType -> {}", event.getType());
        });
        Thread.sleep(10000);
        info("结束");
    }

    @Test
    public void watcherGetData3() throws InterruptedException, KeeperException {
        Watcher watcher = new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                try {
                    info("自定义watcher");
                    info("path -> {}", event.getPath());
                    info("eventType -> {}", event.getType());
                    // 如果是删除就不要重复注册了，因为一定是失败的
                    if (event.getType() == Event.EventType.NodeChildrenChanged) {
                        zooKeeper.getChildren("/watcher3", this);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        // arg1: 节点路径
        // arg2: 使用连接对象中的watcher
        zooKeeper.getChildren("/watcher3", watcher);
        Thread.sleep(10000);
        info("结束");
    }

    @Test
    public void watcherGetData4() throws InterruptedException, KeeperException {
        // arg1: 节点路径
        // arg2: 使用连接对象中的watcher
        zooKeeper.getChildren("/watcher3", event -> {
            info("自定义watcher1");
            info("path -> {}", event.getPath());
            info("eventType -> {}", event.getType());
        });

        zooKeeper.getChildren("/watcher3", event -> {
            info("自定义watcher2");
            info("path -> {}", event.getPath());
            info("eventType -> {}", event.getType());
        });
        Thread.sleep(10000);
        info("结束");
    }

}
