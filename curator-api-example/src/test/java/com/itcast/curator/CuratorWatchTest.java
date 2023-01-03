package com.itcast.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.*;
import org.junit.Test;

public class CuratorWatchTest extends BaseTest {

    @Test
    public void watch1() throws Exception {
        // 监视某个节点变化
        // arg1: 连接对象
        // arg2: 监视节点路径
        NodeCache nodeCache = new NodeCache(client, "/watcher1");
        // 启动监视器对象
        nodeCache.start();
        // 注册一个node监听器
        nodeCache.getListenable().addListener(new NodeCacheListener() {
            @Override
            public void nodeChanged() throws Exception {
                if (nodeCache.getCurrentData() != null) {
                    info("path: {}", nodeCache.getCurrentData().getPath());
                    info("data: {}", nodeCache.getCurrentData().getData());
                }
            }
        });
        Thread.sleep(100000);
        // 关闭监视器对象
        nodeCache.close();
    }

    @Test
    public void watch2() throws Exception {
        // 监视子节点变化
        // arg1: 连接对象
        // arg2: 监视的节点路径
        // arg3: 事件中是否可以获取节点的数据
        PathChildrenCache cache = new PathChildrenCache(client, "/watcher1", true);
        cache.start();
        cache.getListenable().addListener(new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent pathChildrenCacheEvent) throws Exception {
                // 节点的事件类型
                info("type -> {}", pathChildrenCacheEvent.getType());
                info("path -> {}", pathChildrenCacheEvent.getData().getPath());
                info("data -> {}", new String(pathChildrenCacheEvent.getData().getData()));
            }
        });
        Thread.sleep(20000);
        cache.close();
    }

}
