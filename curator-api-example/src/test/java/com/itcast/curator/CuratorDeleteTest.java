package com.itcast.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.junit.Test;

public class CuratorDeleteTest extends BaseTest {
    public CuratorDeleteTest() {
        super("delete");
    }

    @Test
    public void delete1() throws Exception {
        // 删除节点
        client.delete()
                .forPath("/node1");
    }

    @Test
    public void delete2() throws Exception {
        // 删除节点(版本)
        client.delete()
                .withVersion(-1)
                .forPath("/node1");
        Thread.sleep(5000);
    }

    @Test
    public void delete3() throws Exception {
        // 异步删除节点
        client.delete()
                .withVersion(-1)
                .inBackground()
                .forPath("/node1");
        Thread.sleep(5000);
    }

    @Test
    public void delete4() throws Exception {
        // 删除包含子节点
        client.delete()
                // 连子节点一并删除
                .deletingChildrenIfNeeded()
                .withVersion(-1)
                .inBackground()
                .forPath("/node1");
        Thread.sleep(5000);
    }

    @Test
    public void delete5() throws Exception {
        // 删除包含子节点
        client.delete()
                // 连子节点一并删除
                .deletingChildrenIfNeeded()
                .withVersion(-1)
                .inBackground(new BackgroundCallback() {
                    @Override
                    public void processResult(CuratorFramework client, CuratorEvent event) throws Exception {
                        info("路径{}", event.getPath());
                        info("事件类型:{}", event.getType());
                    }
                })
                .forPath("/nod1");
        Thread.sleep(5000);
    }

}
