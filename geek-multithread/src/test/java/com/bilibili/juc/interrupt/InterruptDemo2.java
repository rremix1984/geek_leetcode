/**
 * @auther zzyy
 * @create 2022-01-19 21:35
 */
package com.bilibili.juc.interrupt;

import org.junit.Test;

import static java.lang.System.out;
import static java.lang.Thread.currentThread;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 *
 */
public class InterruptDemo2 {

    @Test
    public void test() {
        //实例方法interrupt()仅仅是设置线程的中断状态位设置为true，不会停止线程
        Thread t1 = new Thread(() -> {
            for (int i = 1; i <= 300; i++)
                out.println("-----: " + i);
            out.println("t1线程调用interrupt()后的的中断标识02：" + currentThread().isInterrupted());
        }, "t1");
        t1.start();

        out.println("t1线程默认的中断标识：" + t1.isInterrupted());//false

        //暂停毫秒
        try {
            MILLISECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        t1.interrupt();//true
        out.println("t1线程调用interrupt()后的的中断标识01：" + t1.isInterrupted());//true

        try {
            MILLISECONDS.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //????---false中断不活动的线程不会产生任何影响。
        out.println("t1线程调用interrupt()后的的中断标识03：" + t1.isInterrupted());
    }
}
