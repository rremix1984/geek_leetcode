package com.itcast.curator;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static org.apache.zookeeper.CreateMode.PERSISTENT;
import static org.apache.zookeeper.ZooDefs.Ids.OPEN_ACL_UNSAFE;

@Slf4j
public class CuratorCreateTest extends BaseTest {

    public CuratorCreateTest() {
        super("create");
    }

    @Test
    public void create1() throws Exception {
        // log.info(new String(client.getData().forPath("")));
        // 新增节点
        client.create()
                // 节点的类型
                .withMode(PERSISTENT)
                // 节点的权限列表 world:anyone:cdrwa 权限
                .withACL(OPEN_ACL_UNSAFE)
                // 指定当前节点的节点路径 和 数据
                .forPath("/nodeA", "nodeA".getBytes());
    }

    @Test
    public void create2() throws Exception {
        // 创建权限列表
        List<ACL> list = new ArrayList<>();
        // 描述授权模式和授权对象
        Id id = new Id("ip","192.168.31.70");
        list.add(new ACL(ZooDefs.Perms.ALL, id));

        // 自定义权限列表
        client.create()
                // 节点的类型
                .withMode(PERSISTENT)
                // 节点的权限列表 world:anyone:cdrwa 权限
                .withACL(list)
                // 指定当前节点的节点路径 和 数据
                .forPath("/nodeC", "nodeC".getBytes());
        log.info("结束");
    }

    @Test
    public void create3() throws Exception {
        // 递归创建节点
        client.create()
                // 可递归创建多层目录
                .creatingParentsIfNeeded()
                .withMode(PERSISTENT)
                .withACL(OPEN_ACL_UNSAFE)
                // 指定当前节点的节点路径 和 数据
                .forPath("/nodeD/nodeD1/1/2/3", "nodeD1".getBytes());
        log.info("结束");
    }

    @Test
    public void create4() throws Exception {
        // 递归创建节点
        client.create()
                .creatingParentsIfNeeded()
                .withMode(PERSISTENT)
                .withACL(OPEN_ACL_UNSAFE)
                // 异步创建节点
                // 异步回调接口
                .inBackground(new BackgroundCallback() {
                    @Override
                    public void processResult(CuratorFramework client, CuratorEvent event) throws Exception {
                        log.info("path -> {}", event.getPath());
                        log.info("type -> {}", event.getType());
                    }
                })
                // 指定当前节点的节点路径 和 数据
                .forPath("/nodeE", "nodeE".getBytes());
        Thread.sleep(5000);
        log.info("结束");
    }

}
