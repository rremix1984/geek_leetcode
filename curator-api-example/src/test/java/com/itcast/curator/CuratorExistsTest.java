package com.itcast.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.zookeeper.data.Stat;
import org.junit.Test;

public class CuratorExistsTest extends BaseTest {

    public CuratorExistsTest() {
        super("get");
    }

    @Test
    public void exists1() throws Exception {
        Stat stat = client
            .checkExists()
            .forPath("node1");
        info("{}", stat);
    }

    @Test
    public void exists2() throws Exception {
        client.checkExists()
                .inBackground(new BackgroundCallback() {
                    @Override
                    public void processResult(CuratorFramework client, CuratorEvent event) throws Exception {
                        info(event.getPath());
                        info("{}", event.getType());
                        info("{}", event.getStat());
                    }
                }).forPath("node1");
        Thread.sleep(5000);
    }

}
