package com.sgg.aqs;

import lombok.val;

import static java.lang.System.out;

/**
 * ReEnterLock
 */
@SuppressWarnings("all")
public class ReEnterLockDemo {

    static final Object lockA = new Object();

    public static void m1() {
        new Thread(() -> {
            val tName = Thread.currentThread().getName();
            synchronized (lockA) {
                out.println(tName + "\t -----外层调用");
                synchronized (lockA) {
                    out.println(tName + "\t -----中层调用");
                    synchronized (lockA) {
                        out.println(tName + "\t ---—-内层调用");
                    }
                }
            }
        }, "t1").start();
    }

    public synchronized void m11() {
        out.println("=====外");
        m2();
    }

    public synchronized void m2() {
        out.println("=====中");
        m3();
    }

    public synchronized void m3() {
        out.println("=====内");
    }

    public static void main(String[] args) {
        new ReEnterLockDemo().m11();
    }

}
