package com.sgg.aqs;

import java.util.concurrent.locks.LockSupport;

import static java.lang.System.currentTimeMillis;
import static java.lang.System.out;
import static java.lang.Thread.currentThread;
import static java.util.concurrent.TimeUnit.SECONDS;
import static java.util.concurrent.locks.LockSupport.park;
import static java.util.concurrent.locks.LockSupport.unpark;

/**
 * LockSupport是用来创建锁和其他同步类的基本线程阻塞原语。
 * LockSupport类使用了一种名为Permit（许可）的概念来做到阻塞和唤醒线程的功能，每个线程都有一个许可（permit），permit只有两个值1和零，默认是零。
 * 可以把许可看成是一种（0,1）信号量（Semaphore），但与 Semaphore 不同的是，许可的累加上限是1。
 * ---------------------------------------------------------------------------------------------
 * LockSupport是一个线程阻塞工具类，所有的方法都是静态方法，可以让线程在任意位置阻寨，阻寨之后也有对应的唤醒方法。归根结底，LockSupport调用的Unsafe中的native代码。
 * LockSupport 提供park（）和 unpark（）方法实现阻塞线程和解除线程阻塞的过程LockSupport和每个使用它的线程都有一个许可（permit）关联。permit相当于1，0的开关，默认是O，调用一次unpark就加1变成1，
 * 调用一次park会消费permit，也就是将1变成O，同时park立即返回。
 * 如再次调用park会变成阻塞（因为permit为零了会阻塞在这里，一直到permit变为1），这时调用unpark会把permit置为1。
 * 每个线程都有一个相关的permit,permit最多只有一个，重复调用unpark也不会积累凭证。
 * <p>
 * 形象的理解
 * 线程阻塞需要消耗凭证（permit），这个凭证最多只有1个。
 * 当调用park方法时
 * 如果有凭证，则会面接消耗掉这个凭证然后正常退出；
 * 如果无凭证，就必须阻塞等待凭证可用；
 * 而unpark则相反，它会增加一个凭证，但凭证最多只能有1个，累加无效。
 */
public class LockSupportDemo {

    public static void main(String[] args) {//main方法，主线程一切程序入日
        Thread a = new Thread(() -> {
            // sleep(3L);
            out.println(tName() + "\t-----come in " + currentTimeMillis());
            park();//被服塞.等待通知等待成行，它要通过需要许可证
            // park();//被服塞.等待通知等待成行，它要通过需要许可证
            out.println(tName() + "\t------被唤醒 " + currentTimeMillis());
        }, "a");
        a.start();

        Thread b = new Thread(() -> {
            unpark(a);
            // unpark(a);
            out.println(tName() + "\t------通知了");
        }, "b");
        b.start();
    }

    private static void sleep(long i) {
        try {
            SECONDS.sleep(i);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static String tName() {
        return currentThread().getName();
    }


}
