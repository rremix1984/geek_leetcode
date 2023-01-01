package com.itcast.zookeeper;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

@Slf4j
public class ZKDeleteTest {

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

    @Test
    public void test1() throws Exception {
        zooKeeper.delete("/delete/node1", -1);
//        zooKeeper.delete("/delete/node2", 2);
    }

    @Test
    public void test2() throws Exception {
        // 异步使用方式
        zooKeeper.delete("/delete/node2", -1, new AsyncCallback.VoidCallback() {
            @Override
            public void processResult(int rc, String path, Object ctx) {
                // 0 代表删除成功
                log.info("rc -> {}", rc);
                log.info("path -> {}", path);
                log.info("ctx -> {}", ctx);
            }
        }, "I am context");
        Thread.sleep(10000);// 因为是异步执行，所以主线程睡眠一会儿
        log.info("结束");
    }

}