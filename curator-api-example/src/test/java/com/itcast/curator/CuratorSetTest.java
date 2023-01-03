package com.itcast.curator;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.junit.Before;
import org.junit.Test;

@Slf4j
public class CuratorSetTest extends BaseTest {

    @Before
    public void before() {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        client = CuratorFrameworkFactory.builder()
                .connectString(IPS)
                .sessionTimeoutMs(5000)
                .retryPolicy(retryPolicy)
                .namespace("set")
                .build();
        client.start();
    }

    @Test
    public void set1() throws Exception {
        // 更新节点
        client.setData()
                // arg1: 要更新的节点路径
                // arg2: 更新节点的数据
                .forPath("/node1", "node11".getBytes());
    }

    @Test
    public void set2() throws Exception {
        client.setData()
                // 指定版本号 -1 就是忽略版本号
                .withVersion(-1)
                .forPath("/node1", "node13".getBytes());
//        client.setData()
//                // 指定错误的版本号
//                .withVersion(1)
//                .forPath("/node1", "node13".getBytes());
    }

    @Test
    public void set3() throws Exception {
        // 异步方式修改节点
        client.setData()
                .withVersion(-1)
                // 异步方式修改
                .inBackground()
                .forPath("/node1", "node15".getBytes());
        Thread.sleep(5000);
    }

}
