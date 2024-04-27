package com.sgg.interview;

import org.junit.Test;

import static java.lang.System.out;

public class PrintABC_Synchronized {
    //锁住的对象
    private final static Object lock = new Object();
    //A是否已经执行
    private static boolean flagA = false;
    //B是否已经执行过
    private static boolean flagB = false;

    @Test
    public void test() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            printA();
            printB();
            printC();
        }
    }

    public static void printA() {
        synchronized (lock) {
            out.print("A");
            flagA = true;
            //唤醒所有等待线程
            lock.notifyAll();
        }
    }

    public static void printB() throws InterruptedException {
        synchronized (lock) {
            //获取到锁，但是要等A执行
            while (!flagA)
                lock.wait();

            out.print("B");
            flagB = true;
            lock.notifyAll();
        }
    }

    public static void printC() throws InterruptedException {
        synchronized (lock) {
            //获取到锁，但是要等B执行
            while (!flagB)
                lock.wait();

            out.print("C");
        }
    }

}