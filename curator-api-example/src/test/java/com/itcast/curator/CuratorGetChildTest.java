package com.itcast.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.junit.Test;

import java.util.List;

public class CuratorGetChildTest extends BaseTest {

    @Test
    public void getChild1() throws Exception {
        List<String> list = client.getChildren()
                .forPath("/get");
        list.forEach(
                s -> info(s)
        );
    }

    @Test
    public void getChild2() throws Exception {
        client.getChildren()
                .inBackground(new BackgroundCallback() {
                    @Override
                    public void processResult(CuratorFramework client, CuratorEvent event) throws Exception {
                        info(event.getPath());
                        info("{}", event.getType());
                        List<String> list = event.getChildren();
                        list.forEach(
                                s -> info(s)
                        );
                    }
                }).forPath("/get");
        Thread.sleep(5000);
    }

}
