package com.itcast.watcher;

import com.itcast.watcher.ZKConnWatcherTest;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
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

    // 计数器对象
    static CountDownLatch countDownLatch = new CountDownLatch(1);

    @After
    public void after() throws Exception {
        log.info("after --> 进行资源的关闭... ");
        zooKeeper.close();
    }

    public void info(String msg) {
        log.info(msg);
    }

    public void info(String regex, Object msg) {
        log.info(regex, msg);
    }

}
