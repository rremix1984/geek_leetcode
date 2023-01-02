package com.itcast.zookeeper;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.concurrent.CountDownLatch;

@Slf4j
public class ZKExistsTest extends BaseTest {

    @Test
    public void exists1() throws Exception {
        Stat stat = zooKeeper.exists("/get/node1", false);
        log.info("stat -> {}", stat);
    }

    @Test
    public void exists2() throws Exception {
        zooKeeper.exists("/get/node2", false, new AsyncCallback.StatCallback() {
            @Override
            public void processResult(int rc, String path, Object ctx, Stat stat) {
                // 0 代表成功
                log.info("rc -> {}", rc);
                // 节点路径
                log.info("path -> {}", path);
                // 上下文参数
                log.info("ctx -> {}", ctx);
                if (stat != null)
                    log.info("stat.version -> {}", stat.getVersion());
                else
                    log.error("no  stat!");
            }
        }, "I am context");
        Thread.sleep(10000);
        log.info("结束");
    }

}
