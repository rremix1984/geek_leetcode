package com.itcast.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.zookeeper.data.Stat;
import org.junit.Test;

public class CuratorGetTest extends BaseTest {

    public CuratorGetTest() {
        super("get");
    }

    @Test
    public void get1() throws Exception {
        // 读取节点
        byte[] bytes = client.getData()
                .forPath("/node1");
        info(new String(bytes));
    }

    @Test
    public void get2() throws Exception {
        Stat stat = new Stat();
        // 读取数据时，读取节点的属性
        byte[] bytes = client.getData()
                    // 用于读取属性
                    .storingStatIn(stat)
                    .forPath("/node1");
        info(new String(bytes));
        info("{}", stat.getVersion());
    }

    @Test
    public void get3() throws Exception {
        // 异步方式读取节点
        client.getData()
                .inBackground(new BackgroundCallback() {
                    @Override
                    public void processResult(CuratorFramework client, CuratorEvent event) throws Exception {
                        info("path -> {}", event.getPath());
                        info("type -> {}", event.getType());
                        info("value -> {}", new String(event.getData()));
                    }
                }).forPath("/node1");
    }

}
