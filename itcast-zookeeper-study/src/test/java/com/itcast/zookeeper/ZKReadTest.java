package com.itcast.zookeeper;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

@Slf4j
public class ZKReadTest {
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
        // arg1: 节点路径
        // arg2: 暂不描述
        // arg3: 读取节点属性的对象
        Stat stat = new Stat();
        byte[] res = zooKeeper.getData("/get/node1", false, stat);
        log.info(new String(res));
        log.info("version -> {}", stat.getVersion());
    }

    @Test
    public void get2() throws Exception {
        // 异步方式
        zooKeeper.getData("/get/node1", false, new AsyncCallback.DataCallback() {
            @Override
            public void processResult(int rc, String path, Object ctx, byte[] data, Stat stat) {
                // 0 代表读取成功
                log.info("rc -> {}", rc);
                // path 节点路径
                log.info("path -> {}", path);
                // ctx 上下文
                log.info("ctx -> {}", ctx);
                // data 读取到的数据
                log.info("data -> {}", new String(data));
                log.info("version -> {}", stat.getVersion());
            }
        }, "I am context");
        Thread.sleep(10000);
        log.info("结束");
    }

}
