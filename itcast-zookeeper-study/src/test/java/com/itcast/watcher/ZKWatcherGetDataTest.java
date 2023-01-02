package com.itcast.watcher;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

@Slf4j
public class ZKWatcherGetDataTest extends BaseTest {

    @Before
    public void before() throws Exception {
        try {
            zooKeeper = new ZooKeeper(ZK_IP_ADDRESS, TIMEOUT, event -> {
                log.info("连接对象的参数!");
                if (event.getState() == Watcher.Event.KeeperState.SyncConnected) {
                    countDownLatch.countDown();
                }
                log.info("path -> {}", event.getPath());
                log.info("eventType -> {}", event.getType());
            });
            // 阻塞线程，等待连接成功
            countDownLatch.await();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void watcherGetData1() throws InterruptedException, KeeperException {
        zooKeeper.getData( "/watcher2", true, null);
        TimeUnit.SECONDS.sleep(50000);
        info("结束");
    }

    @Test
    public void watcherGetData2() throws InterruptedException, KeeperException {
        // arg1: 节点路径
        // arg2: 自定义watcher对象
        zooKeeper.getData("/watcher2", new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                info("自定义watcher");
                info("path -> {}", event.getPath());
                info("eventType -> {}", event.getType());
            }
        }, null);
        TimeUnit.SECONDS.sleep(50000);
        info("结束");
    }

    @Test
    public void watcherGetData3() throws InterruptedException, KeeperException {
        // watcher一次性
        Watcher watcher = new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                try {
                    log.info("自定义watcher");
                    log.info("path -> {}", event.getPath());
                    log.info("eventType -> {}", event.getType());
                    // 如果是删除就不要重复注册了，因为一定是失败的
                    if (event.getType() == Event.EventType.NodeDataChanged) {
                        zooKeeper.getData("/watcher2", this, null);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        // arg1: 节点路径
        // arg2: 自定义watcher对象
        zooKeeper.getData("/watcher2", watcher, null);
        TimeUnit.SECONDS.sleep(50000);
        info("结束");
    }

    @Test
    public void watcherGetData4() throws InterruptedException, KeeperException {
        // 注册多个监听对象
        // 监听器1：
        zooKeeper.getData("/watcher2", event -> {
            log.info("自定义watcher1");
            log.info("path -> {}", event.getPath());
            log.info("eventType -> {}", event.getType());
        },null);

        // 监听器2：
        zooKeeper.getData("/watcher2", event -> {
            log.info("自定义watcher2");
            log.info("path -> {}", event.getPath());
            log.info("eventType -> {}", event.getType());
        },null);

        Thread.sleep(10000);
        log.info("结束");
    }

}
