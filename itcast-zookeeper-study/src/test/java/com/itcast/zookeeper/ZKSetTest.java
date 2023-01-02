package com.itcast.zookeeper;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.data.Stat;
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
public class ZKSetTest extends BaseTest {

//    @Test
    public void set1() throws Exception {
        // arg1: 节点路径
        // arg2: 修改的数据
        // arg3: 第三个参数 -1 代表版本号不再更新
//        zooKeeper.setData("/set/node1", "node11".getBytes(), -1);
        Stat stat = zooKeeper.setData("/set/node1", "node11".getBytes(), 14);
        log.info("{}", stat.getVersion());
    }

    @Test
    public void test2() throws Exception {
        zooKeeper.setData("/set/node1", "node13".getBytes(), -1, new AsyncCallback.StatCallback() {
            @Override
            public void processResult(int rc, String path, Object ctx, Stat stat) {
                // rc = 0 代表成功
                log.info("rc -> {}", rc);
                // 节点路径
                log.info("path -> {}", path);
                // 上下文参数对象
                log.info("ctx -> {}", ctx);
                // 当前节点的属性描述信息
                log.info("stat -> {}", stat);
            }
        }, "I am Context");
        Thread.sleep(10000);
        log.info("结束");
    }

}