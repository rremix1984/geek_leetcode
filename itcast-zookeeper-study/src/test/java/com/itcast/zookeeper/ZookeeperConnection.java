package com.itcast.zookeeper;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

import static java.lang.System.out;

/**
 * 用zk客户端创建连接
 * @author rremix
 */
public class ZookeeperConnection {

    public static final String ZK_IP_ADDRESS = "192.168.31.121:2181";
    public static final int TIMEOUT = 5000;

    @Test
    public void test() {
        try {
            // 计数器对象
            CountDownLatch countDownLatch = new CountDownLatch(1);

            // 客户端延迟连接，需要用计数器对象将主线程阻塞、等待
            // arg1: 服务器ip端口
            // arg2: 客户端与服务器之间会话超时时间（毫秒）
            // arg3: 监视器对象
            // 当客户端连接成功后，会收到一条watch信息
            ZooKeeper zooKeeper = new ZooKeeper(ZK_IP_ADDRESS, TIMEOUT, event -> {
                if (event.getState() == Watcher.Event.KeeperState.SyncConnected) {
                    out.println("连接创建成功！");
                    countDownLatch.countDown();
                }
            });

            // 主线程阻塞等待连接对象，等待创建成功
            countDownLatch.await();
            // 打印会话编号
            out.println(zooKeeper.getSessionId());
            zooKeeper.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


}
