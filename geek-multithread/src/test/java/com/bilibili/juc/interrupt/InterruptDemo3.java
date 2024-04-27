/**
 * @auther zzyy
 * @create 2022-01-20 10:52
 */
package com.bilibili.juc.interrupt;

import lombok.val;

import java.util.concurrent.TimeUnit;

import static java.lang.System.out;
import static java.lang.Thread.sleep;
import static java.util.concurrent.TimeUnit.SECONDS;


/**
    1 中断标志位，默认false
    2 t2 ----> t1发出了中断协商，t2调用t1.interrupt()，中断标志位true
    3 中断标志位true，正常情况，程序停止，^_^
    4 中断标志位true，异常情况，InterruptedException，将会把中断状态将被清除，并且将收到InterruptedException 。中断标志位false
    导致无限循环
    <p>
    5 在catch块中，需要再次给中断标志位设置为true，2次调用停止程序才OK
 */
public class InterruptDemo3 {

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            while (true) {
                val thrd = Thread.currentThread();
                if (thrd.isInterrupted()) {
                    out.println(thrd.getName() + "\t " +
                            "中断标志位：" + thrd.isInterrupted() + " 程序停止");
                    break;
                }

                try {
                    sleep(200);
                } catch (InterruptedException e) {
                    //为什么要在异常处，再调用一次？？
                    thrd.interrupt();
                    e.printStackTrace();
                }
                out.println("-----hello InterruptDemo3");
            }
        }, "t1");
        t1.start();

        //暂停几秒钟线程
        try {
            SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(() -> t1.interrupt(), "t2").start();
    }
}