package com.itcast.watcher;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.junit.Test;
import java.util.concurrent.CountDownLatch;

@Slf4j
public class ZKConnWatcherTest extends BaseTest implements Watcher {
    // 计数器对象
    CountDownLatch countDownLatch = new CountDownLatch(1);

    // 当有连接发生时，调用这个process方法
    @Override
    public void process(WatchedEvent event) {
        try {
            // 事件类型
            if (event.getType() == Event.EventType.None) {
                // 连接状态
                if (event.getState() == Event.KeeperState.SyncConnected) {
                    log.info("连接创建成功！");
                    countDownLatch.countDown();
                } else if (event.getState() == Event.KeeperState.Disconnected) {
                    log.info("断开连接！");
                } else if (event.getState() == Event.KeeperState.Expired) {
                    log.info("连接超时！");
                } else if (event.getState() == Event.KeeperState.AuthFailed) {
                    log.info("客户端身份认证失败！");
                }
            } else if (event.getType() == Event.EventType.NodeCreated) {

            } else if (event.getType() == Event.EventType.NodeDataChanged) {

            } else if (event.getType() == Event.EventType.NodeDeleted) {

            } else if (event.getType() == Event.EventType.NodeChildrenChanged) {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test() throws InterruptedException {
        Thread.sleep(10000);
    }

}
