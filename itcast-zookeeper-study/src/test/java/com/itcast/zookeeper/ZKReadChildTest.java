package com.itcast.zookeeper;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@Slf4j
public class ZKReadChildTest extends BaseTest {

    @Test
    public void get1() throws Exception {
        List<String> res = zooKeeper.getChildren("/get", false);
        res.stream().forEach(
            log::info
        );
    }

    @Test
    public void get2() throws Exception {
        zooKeeper.getChildren("/get", false, new AsyncCallback.Children2Callback() {
            @Override
            public void processResult(int rc, String path, Object ctx, List<String> children, Stat stat) {
                // 0 读取成功
                log.info("rc -> {}", rc);
                // 节点路径
                log.info("path -> {}", path);
                // 上下文参数
                log.info("ctx -> {}", ctx);
                // 子节点
                children.stream().forEach(
                    log::info
                );
            }
        }, "I am context");
        Thread.sleep(10000);
        log.info("结束");
    }

}
