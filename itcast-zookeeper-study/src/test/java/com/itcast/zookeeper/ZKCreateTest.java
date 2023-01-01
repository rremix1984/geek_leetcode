package com.itcast.zookeeper;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import static org.apache.zookeeper.CreateMode.PERSISTENT;
import static org.apache.zookeeper.ZooDefs.Ids.OPEN_ACL_UNSAFE;
import static org.apache.zookeeper.ZooDefs.Ids.READ_ACL_UNSAFE;
import static org.apache.zookeeper.ZooDefs.Perms.READ;
import static org.apache.zookeeper.ZooDefs.Perms.WRITE;

@Slf4j
public class ZKCreateTest {
    public static final String ZK_IP_ADDRESS = "192.168.31.121:2181";
    public static final int TIMEOUT = 5000;

    ZooKeeper zooKeeper;

    @Before
    public void before() throws Exception {
        System.out.println("before --> 进行资源的创建...");
        try {
            // 计数器对象
            CountDownLatch countDownLatch = new CountDownLatch(1);

            // 客户端延迟连接，需要用计数器对象将主线程阻塞、等待
            // arg1: 服务器ip端口
            // arg2: 客户端与服务器之间会话超时时间（毫秒）
            // arg3: 监视器对象
            // 当客户端连接成功后，会收到一条watch信息
            zooKeeper = new ZooKeeper(ZK_IP_ADDRESS, TIMEOUT, event -> {
                if (event.getState() == Watcher.Event.KeeperState.SyncConnected) {
                    System.out.println("连接创建成功！");
                    countDownLatch.countDown();
                }
            });

            // 主线程阻塞等待连接对象，等待创建成功
            countDownLatch.await();
            // 打印会话编号
            log.info("sessionId --> {}", zooKeeper.getSessionId());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @After
    public void after() throws Exception {
        System.out.println("after --> 进行资源的关闭... ");
        zooKeeper.close();
    }

//    @Test
    public void create1() throws Exception {
        System.out.println("test --> 创建节点... ");
        // arg1: 创建节点的路径
        // arg2: 节点的数据（byte数组）
        // arg3: 权限列表 world:anyone:cdrwa
        // arg4: 节点类型 -> 持久化节点
        String res = zooKeeper.create("/create/node1",
                        "node1".getBytes(),
                        OPEN_ACL_UNSAFE,
                        PERSISTENT);
        log.info("res1 -> {}", res);
    }

//    @Test
    public void create2() throws Exception {
        System.out.println("create2 --> 创建节点... ");
        // arg1: 创建节点的路径
        // arg2: 节点的数据（byte数组）
        // arg3: 权限列表 world:anyone:cdrwa
        // arg4: 节点类型 -> 持久化节点
        String res = zooKeeper.create("/create/node2",
                "node2".getBytes(),
                READ_ACL_UNSAFE,
                PERSISTENT);
        log.info("res2 -> {}", res);
    }

//    @Test
    public void create3() throws Exception {
        System.out.println("create3 --> 创建节点... ");
        List<ACL> acls = new ArrayList<>();
        Id id = new Id("world", "anyone");
        acls.add(new ACL(READ, id));
        acls.add(new ACL(WRITE, id));
        String res = zooKeeper.create("/create/node3", "node3".getBytes(),
                        acls, PERSISTENT);
        log.info("res3 -> {}", res);
    }

//    @Test
    public void test4() throws Exception {
        // ip授权模式
        // 权限列表
        List<ACL> acls = new ArrayList<>();
        Id id1 = new Id("ip", "192.168.31.70");
        Id id2 = new Id("ip", "192.168.31.121");
        Id id3 = new Id("ip", "192.168.31.122");
        acls.add(new ACL(ZooDefs.Perms.ALL, id1));
        acls.add(new ACL(ZooDefs.Perms.ALL, id2));
        acls.add(new ACL(ZooDefs.Perms.ALL, id3));
        zooKeeper.create("/create/node444", "node444".getBytes(), acls, PERSISTENT);
    }

//    @Test
    public void test5() throws Exception{
        // auth授权模式
        // 添加授权用户
        zooKeeper.addAuthInfo("digest", "itcast:123456".getBytes());
        zooKeeper.create("/create/node5", "node5".getBytes(),
                ZooDefs.Ids.CREATOR_ALL_ACL, PERSISTENT);
    }

//    @Test
    public void test6() throws Exception {
        // auth授权模式
        // 添加授权用户
        zooKeeper.addAuthInfo("digest", "itcast:123456".getBytes());
        List<ACL> acls = new ArrayList<>();
        Id id = new Id("auth", "itcast");
        acls.add(new ACL(READ, id));

        // 如果要访问先要添加授权用户：addauth digest itcast:123456
        zooKeeper.create("/create/node6", "node6".getBytes(), acls, PERSISTENT);
    }

//    @Test
    public void test7() throws Exception {
        // digest授权模式
        // 权限列表
        List<ACL> acls = new ArrayList<>();
        Id id = new Id("digest", "itheima:qlzQzCLKhBROghkooLvb+Mlwv4A=");
        acls.add(new ACL(ZooDefs.Perms.ALL, id));

        // 如果要访问先要添加授权用户：addauth digest itheima:123456
        zooKeeper.create("/create/node7", "node7".getBytes(), acls, PERSISTENT);
    }

//    @Test
    public void test8() throws Exception {
        String res = zooKeeper.create("/create/node8", "node8".getBytes(), OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL);
        log.info(res);
    }

//    @Test
    public void test9() throws Exception {
        // 临时节点
        // Ids.OPEN_ACL_UNSAFE world:anyone:cdrwa
        String res = zooKeeper.create("/create/node9", "node9".getBytes(),
                OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        log.info(res);
    }

//    @Test
    public void test10() throws Exception {
        // 临时顺序节点
        String res = zooKeeper.create("/create/node10", "node10".getBytes(), OPEN_ACL_UNSAFE,
                        CreateMode.EPHEMERAL_SEQUENTIAL);
        log.info(res);
    }

    @Test
    public void test11() throws Exception {
        zooKeeper.create("/create/node11", "node11".getBytes(), OPEN_ACL_UNSAFE,
                PERSISTENT, new AsyncCallback.StringCallback() {
                    @Override
                    public void processResult(int rc, String path, Object ctx, String name) {
                        System.out.println(rc);
                        System.out.println(path);
                        System.out.println(ctx);
                        System.out.println(name);
                    }
                }, "I am context");
        Thread.sleep(10000);
        System.out.println("结束");
    }

}
