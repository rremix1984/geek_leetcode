/**
 * @auther zzyy
 * @create 2022-04-08 18:18
 */
package com.bilibili.juc.rwlock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static java.lang.System.out;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;


public class ReentrantReadWriteLockDemo {
    public static void main(String[] args) {
        MyResource myResource = new MyResource();

        for (int i = 1; i <= 10; i++) {
            int finalI = i;
            new Thread(() -> {
                myResource.write(finalI + "", finalI + "");
            }, String.valueOf(i)).start();
        }

        for (int i = 1; i <= 10; i++) {
            int finalI = i;
            new Thread(() -> {
                myResource.read(finalI + "");
            }, String.valueOf(i)).start();
        }

        //暂停几秒钟线程
        try {
            SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 1; i <= 3; i++) {
            int finalI = i;
            new Thread(() -> {
                myResource.write(finalI + "", finalI + "");
            }, "新写锁线程->" + String.valueOf(i)).start();
        }
    }
}



class MyResource {//资源类，模拟一个简单的缓存

    Map<String, String> map = new HashMap<>();

    //=====ReentrantLock 等价于 =====synchronized，之前讲解过
    Lock lock = new ReentrantLock();

    //=====ReentrantReadWriteLock 一体两面，读写互斥，读读共享
    ReadWriteLock rwLock = new ReentrantReadWriteLock();

    public void write(String key, String value) {
        rwLock.writeLock().lock();
        try {
            String name = Thread.currentThread().getName();
            out.println(name + "\t" + "正在写入");
            map.put(key, value);
            //暂停毫秒
            try {
                MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            out.println(name + "\t" + "完成写入");
        } finally {
            rwLock.writeLock().unlock();
        }
    }

    public void read(String key) {
        rwLock.readLock().lock();
        try {
            String name = Thread.currentThread().getName();
            out.println(name + "\t" + "正在读取");
            String result = map.get(key);
            // 暂停200毫秒
            // try { MILLISECONDS.sleep(200); } catch (InterruptedException e) { e.printStackTrace(); }

            // 暂停2000毫秒,演示读锁没有完成之前，写锁无法获得
            try {
                MILLISECONDS.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            out.println(name + "\t" + "完成读取" + "\t" + result);
        } finally {
            rwLock.readLock().unlock();
        }
    }
}