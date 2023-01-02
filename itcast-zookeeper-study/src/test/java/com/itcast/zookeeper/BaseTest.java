package com.itcast.zookeeper;

import com.itcast.watcher.ZKConnWatcherTest;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.junit.After;
import org.junit.Before;

import java.util.concurrent.CountDownLatch;

@Slf4j
public class BaseTest {

    public static final String ZK_IP_ADDRESS = "192.168.31.121:2181";
    public static final int TIMEOUT = 5000;

    // 连接对象
    ZooKeeper zooKeeper;

    @Before
    public void before() throws Exception {
        System.out.println("before --> 进行资源的创建...");
        try {
            // 计数器对象
            CountDownLatch countDownLatch = new CountDownLatch(1);

            // 客户端延迟连接，需要用计数器对象将主线程阻塞、等待
            // arg1: 服务器ip端口
            // arg2: 客户端与服务器之间会话超时时间（毫秒）
            // arg3: 监视器对象
            // 当客户端连接成功后，会收到一条watch信息
            zooKeeper = new ZooKeeper(ZK_IP_ADDRESS, TIMEOUT, event -> {
                if (event.getState() == Watcher.Event.KeeperState.SyncConnected) {
                    System.out.println("连接创建成功！");
                    countDownLatch.countDown();
                }
            });

            // 主线程阻塞等待连接对象，等待创建成功
            countDownLatch.await();
            // 打印会话编号
            log.info("sessionId --> {}", zooKeeper.getSessionId());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @After
    public void after() throws Exception {
        log.info("after --> 进行资源的关闭... ");
        zooKeeper.close();
    }

}
