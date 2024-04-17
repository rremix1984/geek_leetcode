package com.sgg.aqs;

import lombok.val;

import static java.lang.System.out;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Object 的 wait 和 Notify 方法
 * 要求：t1线程等待3秒钟，3秒紳后t2线程喚醒t1线程繼续工作
 * 以下异常情况：
 * 2 wait方法和notify方法，两个都去掉同步代码块后看运行效哭
 *  2.1 异常情况
 *      Exception in thread "t]" java. Lang. IllegalMonitorStateException at java. Lang.Object.wait(Native Method)
 *      Exception in thread "t2" java. Lang. IllegalMonitorStateException at java. Lang.Object.notify(Native Method)
 *  2.2 结
 *      Object类中的 wait、notify、notifyALL用于线理等待和晚醒的方法，都必须在synchronized内部执行（必须用到关健字synchronized）。
 */
public class ObjectWaitNotifyDemo {

    static final Object lock = new Object();

    public static void main(String[] args) {//main方法，主线程一切程序入口｛
        new Thread(() -> {
            //暂停几秒钟线程
            try {
                SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            val tName = Thread.currentThread().getName();
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
            String tName = Thread.currentThread().getName();
            synchronized (lock) {
                lock.notify();
                out.println(tName + "\t-----通知");
            }
        }, "B").start();
    }
}