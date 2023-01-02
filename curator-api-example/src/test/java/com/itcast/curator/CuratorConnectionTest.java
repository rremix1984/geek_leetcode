package com.itcast.curator;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.GetChildrenBuilder;
import org.apache.curator.retry.RetryOneTime;
import org.junit.Test;
import java.util.Collections;
import java.util.List;

@Slf4j
public class CuratorConnectionTest {

    public static final String IPS = "192.168.31.121:2181,192.168.31.122:2181,192.168.31.123:2181";
    public static final int TIMEOUT = 5000;

    @Test
    public void test() throws Exception {
        // 创建连接对象
        CuratorFramework client = CuratorFrameworkFactory.builder()
                // ip地址和端口号
                .connectString(IPS)
                // 超时时间
                .sessionTimeoutMs(TIMEOUT)
                // 重连机制，超时后的重试策略
                .retryPolicy(new RetryOneTime(3000))
                // 命名空间，例如：create 就是以create节点为父节点
                .namespace("create")
                // 构建连接对象
                .build();

        // 打开连接
        client.start();
        log.info("client连接打开: {}", client.isStarted());
        log.info(new String(client.getData().forPath("node1")));
        GetChildrenBuilder gcb = client.getChildren();
        List<String> list = gcb.forPath("");
        Collections.sort(list);
        list.forEach(System.out::println);

        // 关闭连接
        client.close();
    }

}
