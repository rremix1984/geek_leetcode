package com.bilibili.juc.syncup;

import static java.lang.System.out;

/**
 * @auther zzyy
 * 锁粗化
 * 假如方法中首尾相接，前后相邻的都是同一个锁对象，那JIT编译器就会把这几个synchronized块合并成一个大块，
 * 加粗加大范围，一次申请锁使用即可，避免次次的申请和释放锁，提升了性能
 */
public class LockBigDemo {
    static final Object objectLock = new Object();

    public static void main(String[] args) {
        new Thread(() -> {
            synchronized (objectLock) {
                out.println("111111");
            }
            synchronized (objectLock) {
                out.println("222222");
            }
            synchronized (objectLock) {
                out.println("333333");
            }
            synchronized (objectLock) {
                out.println("444444");
            }
            synchronized (objectLock) {
                out.println("111111");
                out.println("222222");
                out.println("333333");
                out.println("444444");
            }
        }, "t1").start();
    }
}
