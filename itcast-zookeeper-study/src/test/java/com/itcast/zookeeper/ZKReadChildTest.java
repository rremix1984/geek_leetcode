package com.itcast.zookeeper;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@Slf4j
public class ZKReadChildTest {

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
    public void get1() throws Exception {
        List<String> res = zooKeeper.getChildren("/get", false);
        res.stream().forEach(
            log::info
        );
    }

    @Test
    public void get2() throws Exception {
        zooKeeper.getChildren("/get", false, new AsyncCallback.Children2Callback() {
            @Override
            public void processResult(int rc, String path, Object ctx, List<String> children, Stat stat) {
                // 0 读取成功
                log.info("rc -> {}", rc);
                // 节点路径
                log.info("path -> {}", path);
                // 上下文参数
                log.info("ctx -> {}", ctx);
                // 子节点
                children.stream().forEach(
                    log::info
                );
            }
        }, "I am context");
        Thread.sleep(10000);
        log.info("结束");
    }

}
