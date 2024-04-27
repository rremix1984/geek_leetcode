package com.sgg.aqs;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.System.out;

/**
 * ReEnterLock
 */
public class ReEnterLockDemo1 {

    static final Lock lock = new ReentrantLock();

    public static void main(String[] args) {
        new Thread(() -> {
            lock.lock();
            lock.lock();
            String tName = Thread.currentThread().getName();
            try {
                out.println(tName + "\t————————外部");
                lock.lock();
                try {
                    out.println(tName + "\t————————内部");
                } finally {
                    lock.unlock();
                }
            } finally {
                // 由于加锁次数和释成次数不一样，第二个线始终无法获取到锁，导一直在等待。
                lock.unlock();//正常情况，加锁几次就要解锁几次
                lock.unlock();
            }
        }, "t1").start();

        new Thread (() -> {
            lock.lock();
            String tName = Thread.currentThread().getName();
            try {
                out.println(tName + "\t——————————调用开始");
            } finally {
                lock.unlock();
            }
        }, "t2").start();
    }

}