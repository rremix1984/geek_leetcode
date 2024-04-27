/**
 * @auther zzyy
 * @create 2022-01-20 16:14
 */
package com.bilibili.juc.LockSupport;

import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.System.currentTimeMillis;
import static java.lang.System.out;
import static java.util.concurrent.TimeUnit.SECONDS;
import static java.util.concurrent.locks.LockSupport.unpark;


public class LockSupportDemo {

    static int x = 0;
    static int y = 0;

    @Test
    public void test() {
        Thread t1 = new Thread(() -> {
            try {
                SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String name = Thread.currentThread().getName();
            out.println(name + "\t ----come in" + currentTimeMillis());
            LockSupport.park();
            out.println(name + "\t ----被唤醒" + currentTimeMillis());
        }, "t1");
        t1.start();

        //暂停几秒钟线程
        //try { SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }

        new Thread(() -> {
            unpark(t1);
            String name = Thread.currentThread().getName();
            out.println(name + "\t ----发出通知");
        }, "t2").start();

    }

    private static void lockAwaitSignal() {

        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();

        new Thread(() -> {
            try {
                SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.lock();
            String name = Thread.currentThread().getName();
            try {
                out.println(name + "\t ----come in");
                condition.await();
                out.println(name + "\t ----被唤醒");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }, "t1").start();

        //暂停几秒钟线程
        //try { SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }

        new Thread(() -> {
            lock.lock();
            try {
                String name = Thread.currentThread().getName();
                condition.signal();
                out.println(name + "\t ----发出通知");
            } finally {
                lock.unlock();
            }
        }, "t2").start();
    }

    private static void syncWaitNotify() {
        Object objectLock = new Object();

        new Thread(() -> {
            try {
                SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (objectLock) {
                String name = Thread.currentThread().getName();
                out.println(name + "\t ----come in");
                try {
                    objectLock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                out.println(name + "\t ----被唤醒");
            }
        }, "t1").start();

        //暂停几秒钟线程
        //try { SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }

        new Thread(() -> {
            synchronized (objectLock) {
                objectLock.notify();
                String name = Thread.currentThread().getName();
                out.println(name + "\t ----发出通知");
            }
        }, "t2").start();
    }

}
