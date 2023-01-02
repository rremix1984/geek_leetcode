package com.itcast.watcher;

import com.itcast.watcher.BaseTest;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.junit.Before;
import org.junit.Test;

@Slf4j
public class ZKWatcherExistsTest extends BaseTest {

    @Before
    public void before() throws Exception {
        try {
            // 客户端延迟连接，需要用计数器对象将主线程阻塞、等待
            // arg1: 服务器ip端口
            // arg2: 客户端与服务器之间会话超时时间（毫秒）
            // arg3: 监视器对象
            // 当客户端连接成功后，会收到一条watch信息
            zooKeeper = new ZooKeeper(ZK_IP_ADDRESS, TIMEOUT, new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                    log.info("连接对象的参数!");
                    if (event.getState() == Event.KeeperState.SyncConnected) {
                        countDownLatch.countDown();
                    }
                    log.info("path -> {}", event.getPath());
                    log.info("eventType -> {}", event.getType());
                }
            });
            // 阻塞线程，等待连接成功
            countDownLatch.await();
            // 会话ID
            log.info("sessionId: {}", zooKeeper.getSessionId());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void test() {

    }

}
