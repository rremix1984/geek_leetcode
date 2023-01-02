package com.itcast.example;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

@Slf4j
public class ZookeeperConnection {

    @Test
    public void test() {
        try {
            CountDownLatch countDownLatch = new CountDownLatch(1);
            ZooKeeper zooKeeper =
                    new ZooKeeper("192.168.31.121:2181,192.168.31.122:2181,192.168.31.123:2181",
                            5000, new Watcher() {
                        @Override
                        public void process(WatchedEvent event) {
                            if (event.getState() == Event.KeeperState.SyncConnected) {
                                log.info("连接创建成功！");
                                countDownLatch.countDown();
                            }
                        }
                    });
            countDownLatch.await();
            byte[] bytes = zooKeeper.getData("/tmp/node1", false, null);
            log.info(new String(bytes));
            Thread.sleep(5000);
            log.info("结束");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}
