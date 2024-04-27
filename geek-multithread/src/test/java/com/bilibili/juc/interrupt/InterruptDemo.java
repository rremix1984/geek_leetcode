/**
 * @auther zzyy
 * @create 2022-01-19 11:42
 */
package com.bilibili.juc.interrupt;

import lombok.val;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.lang.System.out;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class InterruptDemo {

    static volatile boolean isStop = false;
    static AtomicBoolean atomicBoolean = new AtomicBoolean(false);

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            while (true) {
                val thrd = Thread.currentThread();
                if (thrd.isInterrupted()) {
                    out.println(thrd.getName() + "\t isInterrupted()被修改为true，程序停止");
                    break;
                }
                out.println("t1 -----hello interrupt api");
            }
        }, "t1");
        t1.start();

        out.println("-----t1的默认中断标志位：" + t1.isInterrupted());

        //暂停毫秒
        try {
            MILLISECONDS.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //t2向t1发出协商，将t1的中断标志位设为true希望t1停下来
        new Thread(() -> {
            t1.interrupt();
        }, "t2").start();
        //t1.interrupt();

    }

    private static void m2_atomicBoolean() {
        new Thread(() -> {
            while (true) {
                if (atomicBoolean.get()) {
                    out.println(Thread.currentThread().getName() + "\t atomicBoolean被修改为true，程序停止");
                    break;
                }
                out.println("t1 -----hello atomicBoolean");
            }
        }, "t1").start();

        //暂停毫秒
        try {
            MILLISECONDS.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            atomicBoolean.set(true);
        }, "t2").start();
    }

    private static void m1_volatile() {
        new Thread(() -> {
            while (true) {
                if (isStop) {
                    out.println(Thread.currentThread().getName() + "\t isStop被修改为true，程序停止");
                    break;
                }
                out.println("t1 -----hello volatile");
            }
        }, "t1").start();

        //暂停毫秒
        try {
            MILLISECONDS.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            isStop = true;
        }, "t2").start();
    }
}
