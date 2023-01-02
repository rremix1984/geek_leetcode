package com.itcast.watcher;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

@Slf4j
public class ZKConnWatcherTest implements Watcher {
    // 计数器对象
    CountDownLatch countDownLatch = new CountDownLatch(1);
    public static final String ZK_IP_ADDRESS = "192.168.31.121:2181";
    public static final int TIMEOUT = 5000;
    // 连接对象
    ZooKeeper zooKeeper;

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
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void before() throws Exception {
        try {
            // 计数器对象
            CountDownLatch countDownLatch = new CountDownLatch(1);

            // 客户端延迟连接，需要用计数器对象将主线程阻塞、等待
            // arg1: 服务器ip端口
            // arg2: 客户端与服务器之间会话超时时间（毫秒）
            // arg3: 监视器对象
            // 当客户端连接成功后，会收到一条watch信息
            zooKeeper = new ZooKeeper(ZK_IP_ADDRESS, TIMEOUT, new ZKConnWatcherTest());
            // 阻塞线程，等待连接成功
            countDownLatch.await();
            // 会话ID
            log.info("{}", zooKeeper.getSessionId());
            Thread.sleep(10000);
            zooKeeper.close();
            log.info("结束");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void test() {

    }

}
