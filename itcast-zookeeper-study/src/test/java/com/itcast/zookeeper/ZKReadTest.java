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
public class ZKReadTest extends BaseTest {

    @Test
    public void get1() throws Exception {
        // arg1: 节点路径
        // arg2: 暂不描述
        // arg3: 读取节点属性的对象
        Stat stat = new Stat();
        byte[] res = zooKeeper.getData("/get/node1", false, stat);
        log.info(new String(res));
        log.info("version -> {}", stat.getVersion());
    }

    @Test
    public void get2() throws Exception {
        // 异步方式
        zooKeeper.getData("/get/node1", false, new AsyncCallback.DataCallback() {
            @Override
            public void processResult(int rc, String path, Object ctx, byte[] data, Stat stat) {
                // 0 代表读取成功
                log.info("rc -> {}", rc);
                // path 节点路径
                log.info("path -> {}", path);
                // ctx 上下文
                log.info("ctx -> {}", ctx);
                // data 读取到的数据
                log.info("data -> {}", new String(data));
                log.info("version -> {}", stat.getVersion());
            }
        }, "I am context");
        Thread.sleep(10000);
        log.info("结束");
    }

}
