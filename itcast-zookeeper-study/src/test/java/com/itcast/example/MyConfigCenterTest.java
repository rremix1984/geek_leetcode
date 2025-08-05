package com.itcast.example;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.junit.Test;

@Data
@EqualsAndHashCode(callSuper=false)
public class MyConfigCenterTest extends BaseTest implements Watcher {

    @Override
    public void process(WatchedEvent event) {
        try {
            // 事件类型
            if (event.getType() == Event.EventType.None) {
                // 连接状态
                if (event.getState() == Event.KeeperState.SyncConnected) {
                    info("连接创建成功！");
                    countDownLatch.countDown();
                } else if (event.getState() == Event.KeeperState.Disconnected) {
                    info("断开连接！");
                } else if (event.getState() == Event.KeeperState.Expired) {
                    info("连接超时！");
                } else if (event.getState() == Event.KeeperState.AuthFailed) {
                    info("客户端身份认证失败！");
                }
            // 当节点数据（配置信息）发生变化时，重新加载
            } else if (event.getType() == Event.EventType.NodeDataChanged) {
                info("NodeDataChanged!");
                // 重新加载
                initValue();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    // 连接zookeeper服务器，读取配置信息
    public void initValue() {
        try {
            zooKeeper = new ZooKeeper(ZK_IP_ADDRESS, TIMEOUT, this);
            countDownLatch.await();
            this.url = new String(
                    zooKeeper.getData("/cfg/url", true, null));
            this.username = new String(
                    zooKeeper.getData("/cfg/username", true, null));
            this.password = new String(
                    zooKeeper.getData("/cfg/password", true, null));
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public MyConfigCenterTest() {
        initValue();
    }

    @Test
    public void test() throws Exception {
        MyConfigCenterTest cfg = new MyConfigCenterTest();
        for (int i = 0; i < 10; i++) {
            Thread.sleep(5000);
            info("url -> {}", cfg.getUrl());
            info("username -> {}", cfg.getUsername());
            info("password -> {}", cfg.getPassword());
            info("########################");
        }
    }


    // 用于本地化存储 配置信息
    private String url;
    private String username;
    private String password;

}
