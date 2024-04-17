package com.sgg.aqs;

import lombok.val;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.System.out;
import static java.lang.Thread.currentThread;
import static java.util.concurrent.TimeUnit.SECONDS;

public class ConditionWaitNotifyDemo {

    static final Lock lock = new ReentrantLock();
    static final Condition condition = lock.newCondition();

    public static void main(String[] args) {
        new Thread(() -> {
            try {
                SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            val tName = currentThread().getName();
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
            val tName = currentThread().getName();
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

            val tName = currentThread().getName();
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
