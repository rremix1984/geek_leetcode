/**
 * @auther zzyy
 * @create 2022-01-20 11:58
 */
package com.bilibili.juc.interrupt;

import lombok.val;
import org.junit.Test;

import java.util.concurrent.locks.LockSupport;

import static java.lang.System.out;
import static java.lang.Thread.currentThread;
import static java.lang.Thread.interrupted;

/**
    测试当前线程是否被中断（检查中断标志），返回一个boolean并清除中断状态，
    第二次再调用时中断状态已经被清除，将返回一个false。
 */
@SuppressWarnings("all")
public class InterruptDemo4 {

    @Test
    public void test() {
        val thrd = currentThread();
        val name = thrd.getName();
        out.println(name + "\t" + interrupted());
        out.println(name  + "\t" + interrupted());
        out.println("----1");
        thrd.interrupt();// 中断标志位设置为true
        out.println("----2");
        out.println(name  + "\t" + interrupted());
        out.println(name  + "\t" + interrupted());

        LockSupport.park();

        interrupted();//静态方法

        thrd.isInterrupted();//实例方法
    }

}
