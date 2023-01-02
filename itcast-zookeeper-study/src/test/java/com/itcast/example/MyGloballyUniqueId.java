package com.itcast.example;

import org.apache.zookeeper.*;
import org.junit.Test;

public class MyGloballyUniqueId extends BaseTest implements Watcher {

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
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    // 构造方法中打开连接
    public MyGloballyUniqueId() {
        try {
            // 打开连接对象
            zooKeeper = new ZooKeeper(ZK_IP_ADDRESS, TIMEOUT, this);
            // 阻塞线程，等待连接创建成功
            countDownLatch.await();
            Thread.sleep(10000);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    // 生成唯一id的方法
    public String getUniqueId() {
        String path = "";
        try {
            // 创建临时有序节点
            path = zooKeeper.create("/uniqueId", new byte[0],
                    ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        // uniqueId0000000001 ，因为前面有8个字符，所以substring(9)
        return path.substring(9);
    }

    @Test
    public void test() {
        MyGloballyUniqueId global = new MyGloballyUniqueId();
        for (int i = 0; i <= 5; i++) {
            String uid = global.getUniqueId();
            info("uid -> {}", uid);
        }
    }

}