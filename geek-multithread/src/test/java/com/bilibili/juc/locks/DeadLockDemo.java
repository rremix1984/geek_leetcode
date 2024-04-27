/**
 * @auther zzyy
 * @create 2022-01-18 19:37
 */
package com.bilibili.juc.locks;

import lombok.val;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static java.lang.System.out;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 *
 */
public class DeadLockDemo {

    @Test
    public void test() {
        final Object objectA = new Object();
        final Object objectB = new Object();
        new Thread(() -> {
            synchronized (objectA) {
                val name = Thread.currentThread().getName();
                out.println(name + "\t 自己持有A锁，希望获得B锁");
                try {SECONDS.sleep(1);} catch (InterruptedException e) {e.printStackTrace();}
                synchronized (objectB) {
                    out.println(name + "\t 成功获得B锁");
                }
            }
        }, "A").start();

        new Thread(() -> {
            synchronized (objectB) {
                val name = Thread.currentThread().getName();
                out.println(name + "\t 自己持有B锁，希望获得A锁");
                try {SECONDS.sleep(1);} catch (InterruptedException e) {e.printStackTrace();}
                synchronized (objectA) {
                    out.println(name + "\t 成功获得A锁");
                }
            }
        }, "B").start();
    }

}
