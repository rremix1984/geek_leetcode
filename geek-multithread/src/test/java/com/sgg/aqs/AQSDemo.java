package com.sgg.aqs;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.System.out;
import static java.util.concurrent.TimeUnit.MINUTES;

/**
    面试题：【Java集合类】
    1、从集合开始吧，介绍一下常用的集合类，哪些是有序的，哪些是无序的
    2、hashmap是如何寻址的，哈希碰撞后是如何存储数据的，1.8后什么时候变成红黑树、说下红票理，红黑树有什么好处
    3、ConcurrentHashMap 間g分長金名經理校金，一个里面会有几个段 segment， jdk1.8后有优化
    4、Reentrantlock 实现原理，简单说下aas
    5、synchronized实现原理，monitor对象什么时候生成的？知道monitor的 monitorenter 和 moni
        这两个是怎么保证同步的吗，或者说，这两个操作计算机底层是如何执行的
    6、刚刚你提到了synchronized的优化过程，详细说一下吧。偏向锁和轻量级锁有什么区别？
    7、线程池几个参数说下，你们项目中如何根据实际场景设置参数的，为什么cpu密集设置的线程集型少

 * AQS使用一个volatile的int类型的成员变量来表示同步状态，通过内置的FIFO队列来完成资源获取的
 * 排队工作将每条要去抢占资源的线程封装成一个Node节点来实现锁的分配，通过CAS完成对State值的修改。
 */
public class AQSDemo {

    static Lock lock = new ReentrantLock();

    public static void main(String[] args) {
        // 带入一个銀行办理业务的案例来模拟我们的AQS如何进行线程的管理和通知唤醒机制
        // 3个线程模拟3个来銀行网点，受理窗口办理业务的顾客
        // A 顾客就是第一个顾客，此时受理窗口没有任何人，A可以直接去办理
        new Thread (() -> {
            lock.lock();
            try {
                out.println("-----A thread come in");
            //暂停几秘钟线程
            } finally {
                lock.unlock();
                try {
                    MINUTES.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "A").start();

        // 第二个线程
        // 第2个顾客，第2个线程-—-->，由于受理业务的窗口只有一个（只能一个线程持有锁），
        // 此时 B 只能等待，
        // 进入候客区
        new Thread (() -> {
            lock.lock();
            try {
                out.println("-----B thread come in");
                //暂停几秘钟线程
            } finally {
                lock.unlock();
                try {
                    MINUTES.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "B").start();

        // 第3个顾客，第3个线程-—-->，由于受理业务的窗口只有一个（只能一个线程持有锁），此时c只能等待，
        // 进入候客区
        new Thread (() -> {
            lock. lock();
            try {
                out.println("-----C thread come in");
            } finally {
                lock.unlock();
//                lock.unlock();
                try {
                    MINUTES.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "C").start();

    }


}
