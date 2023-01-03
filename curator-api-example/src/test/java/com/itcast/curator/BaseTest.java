package com.itcast.curator;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.junit.After;
import org.junit.Before;

@Slf4j
public class BaseTest {

    private String namespace;

    public BaseTest(String namespace) {
        this.namespace = namespace;
    }

    public static final String IPS = "192.168.31.121:2181,192.168.31.122:2181,192.168.31.123:2181";
    public static final int TIMEOUT = 5000;
    CuratorFramework client;
    @Before
    public void before() {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        client = CuratorFrameworkFactory.builder()
                .connectString(IPS)
                .sessionTimeoutMs(5000)
                .retryPolicy(retryPolicy)
                .namespace(namespace)
                .build();
        client.start();
    }

    @After
    public void after() {
        client.close();
        info("结束");
    }

    public void info(String msg) {
        log.info(msg);
    }

    public void info(String regex, Object... msg) {
        log.info(regex, msg);
    }

}
