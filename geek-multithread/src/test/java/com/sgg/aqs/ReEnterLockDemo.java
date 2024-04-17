package com.sgg.aqs;

import static java.lang.System.out;
import static java.lang.Thread.currentThread;

/**
 * 可重入锁：可以重复获取的锁
 */
public class ReEnterLockDemo{

    static final Object objectLockA = new Object();

    public static void m1() {
        new Thread(() -> {
            String tName = currentThread().getName();
            synchronized (objectLockA) {
                out.println(tName + "\t" + "-----外层调用");

                synchronized (objectLockA) {
                    out.println(tName + "\t" + "-----中层调用");

                    synchronized (objectLockA) {
                        out.println(tName + "\t" + "-----内层调用");
                    }
                }
            }
        }, "t1").start();
    }

    public static void main(String[] args) {
        m1();
    }

}