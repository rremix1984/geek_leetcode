/**
 * @auther zzyy
 * @create 2022-01-20 10:52
 */
package com.bilibili.juc.interrupt;

import org.junit.Test;

import java.util.concurrent.locks.LockSupport;

import static java.lang.System.out;
import static java.lang.Thread.currentThread;

/**
 * @auther zzyy
 * @create 2022-01-20 11:58
 */
@SuppressWarnings("all")
public class InterruptDemo3 {

    @Test
    public void test() {
        Thread thrd = currentThread();
        out.println(thrd.isInterrupted());
        out.println(thrd.getName() + "\t" + thrd.isInterrupted());
        out.println("----1");

        LockSupport.park();

        out.println("----2");
        thrd.interrupt();// 中断标志位设置为true
        out.println("----3");
        out.println(thrd.getName() + "\t" + thrd.isInterrupted());
        out.println(thrd.getName() + "\t" + thrd.isInterrupted());

        LockSupport.park();
        out.println("----4");
    }

}