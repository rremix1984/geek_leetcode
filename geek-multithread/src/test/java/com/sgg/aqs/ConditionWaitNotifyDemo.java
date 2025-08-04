package com.sgg.aqs;

import org.junit.Test;
import java.util.concurrent.locks.*;
import static java.lang.System.out;
import static java.lang.Thread.*;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * 通过 Condition 实现
 */
@SuppressWarnings("all")
public class ConditionWaitNotifyDemo {

    static final Lock lock = new ReentrantLock();
    static final Condition condition = lock.newCondition();

    @Test
    public void test() {
        new Thread(() -> {
            try {
                sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String tName = currentThread().getName();
            lock.lock();
            try {
                out.println(tName + "\t___--come in");
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                out.println(tName + "\t"+"----被唤醒");
            } finally {
                lock.unlock();
            }
        }, "A").start();

        new Thread (() -> {
            lock.lock();
            String tName = currentThread().getName();
            try {
                condition.signal();
                out.println(tName + "\t+“-----通知");
            } finally {
                lock.unlock();
            }
        }, "B").start();
    }

    public static void synchronizedWaitNotify() {
        new Thread(() -> {
            //暂停几秘钟线程
            try {
                SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String tName = currentThread().getName();
            synchronized (lock) {
                out.println(tName + "\t--come in");
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                out.println(tName + "\t被唤醒");
            }
        }, "A").start();

        new Thread(() -> {
            String tName = currentThread().getName();
            synchronized (lock) {
                lock.notify();
                out.println(tName + "\t-----通知");
            }
        }, "B").start();
    }
}
