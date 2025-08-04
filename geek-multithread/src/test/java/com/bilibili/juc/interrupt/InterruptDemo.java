/**
 * @auther zzyy
 * @create 2022-01-19 11:42
 */
package com.bilibili.juc.interrupt;

import org.junit.Test;

import static java.lang.System.out;
import static java.lang.Thread.currentThread;

/**
 * @auther zzyy
 * @create 2022-01-20 11:58
 */
@SuppressWarnings("all")
public class InterruptDemo {

    @Test
    public void test() {
        Thread thrd = currentThread();
        out.println(thrd.isInterrupted());
        out.println(thrd.getName() + "\t" + thrd.isInterrupted());
        out.println("----1");
        thrd.interrupt();// 中断标志位设置为true
        out.println("----2");
        out.println(thrd.getName() + "\t" + thrd.isInterrupted());
        out.println(thrd.getName() + "\t" + thrd.isInterrupted());
    }

}
