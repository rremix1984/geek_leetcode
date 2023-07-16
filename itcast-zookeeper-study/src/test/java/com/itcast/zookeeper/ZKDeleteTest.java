package com.itcast.zookeeper;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.AsyncCallback;
import org.junit.Test;

@Slf4j
public class ZKDeleteTest extends BaseTest {

    @Test
    public void test1() throws Exception {
        zooKeeper.delete("/delete/node1", -1);
//        zooKeeper.delete("/delete/node2", 2);
    }

    @Test
    public void test2() throws Exception {
        // 异步使用方式
        zooKeeper.delete("/delete/node2", -1, new AsyncCallback.VoidCallback() {
            @Override
            public void processResult(int rc, String path, Object ctx) {
                // 0 代表删除成功
                log.info("rc -> {}", rc);
                log.info("path -> {}", path);
                log.info("ctx -> {}", ctx);
            }
        }, "I am context");
        Thread.sleep(10000);// 因为是异步执行，所以主线程睡眠一会儿
        log.info("结束");
    }

}