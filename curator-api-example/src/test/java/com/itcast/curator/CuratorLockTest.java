package com.itcast.curator;

import org.apache.curator.framework.recipes.locks.InterProcessLock;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.framework.recipes.locks.InterProcessReadWriteLock;
import org.junit.Test;

public class CuratorLockTest extends BaseTest {

    @Test
    public void lock1() throws Exception {
        // 排它锁
        // arg1: 连接对象
        // arg2: 节点路径
        InterProcessLock lock = new InterProcessMutex(client, "/lock1");
        info("等待获取锁对象！");
        // 获取锁
        lock.acquire();
        for (int i = 0; i < 10; i++) {
            Thread.sleep(500);
            info("i -> {}", i);
        }
        // 释放锁
        lock.release();
        info("等待释放锁！");
    }

    @Test
    public void lock2() throws Exception {
        // 读写锁
        InterProcessReadWriteLock lock = new InterProcessReadWriteLock(client, "/lock1");
        InterProcessLock readLock = lock.readLock();
        info("等待获取【读锁】对象");
        // 获取锁
        readLock.acquire();
        for (int i = 0; i < 10; i++) {
            Thread.sleep(500);
            info("{}", i);
        }
        // 释放锁对象
        readLock.release();
        info("等待释【放读】锁");
    }

    @Test
    public void lock3() throws Exception {
        // 读写锁
        InterProcessReadWriteLock lock = new InterProcessReadWriteLock(client, "/lock1");
        InterProcessLock writeLock = lock.writeLock();
        info("等待获取【写锁】对象");
        // 获取锁
        writeLock.acquire();
        for (int i = 0; i < 10; i++) {
            Thread.sleep(500);
            info("{}", i);
        }
        // 释放锁对象
        writeLock.release();
        info("等待释放【写锁】");
    }

}
