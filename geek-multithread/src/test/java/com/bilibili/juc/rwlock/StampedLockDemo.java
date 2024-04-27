package com.bilibili.juc.rwlock;;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.StampedLock;

import static java.lang.System.out;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * <p>
 * StampedLock = ReentrantReadWriteLock + 读的过程中也允许获取写锁介入
 */
public class StampedLockDemo {

    static int number = 37;
    static StampedLock stampedLock = new StampedLock();

    public void write() {
        long stamp = stampedLock.writeLock();
        out.println(Thread.currentThread().getName() + "\t" + "写线程准备修改");
        try {
            number = number + 13;
        } finally {
            stampedLock.unlockWrite(stamp);
        }
        out.println(Thread.currentThread().getName() + "\t" + "写线程结束修改");
    }

    //悲观读，读没有完成时候写锁无法获得锁
    public void read() {
        long stamp = stampedLock.readLock();
        out.println(Thread.currentThread().getName() + "\t" + " come in readlock code block，4 seconds continue...");
        for (int i = 0; i < 4; i++) {
            //暂停几秒钟线程
            try {
                SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            out.println(Thread.currentThread().getName() + "\t" + " 正在读取中......");
        }

        try {
            int result = number;
            out.println(Thread.currentThread().getName() + "\t" + " 获得成员变量值result：" + result);
            out.println("写线程没有修改成功，读锁时候写锁无法介入，传统的读写互斥");
        } finally {
            stampedLock.unlockRead(stamp);
        }
    }

    //乐观读，读的过程中也允许获取写锁介入
    public void tryOptimisticRead() {
        long stamp = stampedLock.tryOptimisticRead();
        int result = number;
        //故意间隔4秒钟，很乐观认为读取中没有其它线程修改过number值，具体靠判断
        out.println("4秒前stampedLock.validate方法值(true无修改，false有修改)" + "\t" + stampedLock.validate(stamp));
        for (int i = 0; i < 4; i++) {
            try {
                SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            out.println(Thread.currentThread().getName() + "\t" + "正在读取... " + i + " 秒" +
                    "后stampedLock.validate方法值(true无修改，false有修改)" + "\t" + stampedLock.validate(stamp));
        }
        if (!stampedLock.validate(stamp)) {
            out.println("有人修改过------有写操作");
            stamp = stampedLock.readLock();
            try {
                out.println("从乐观读 升级为 悲观读");
                result = number;
                out.println("重新悲观读后result：" + result);
            } finally {
                stampedLock.unlockRead(stamp);
            }
        }
        out.println(Thread.currentThread().getName() + "\t" + " finally value: " + result);
    }


    public static void main(String[] args) {
        StampedLockDemo resource = new StampedLockDemo();

        /*传统版
        new Thread(() -> {
            resource.read();
        },"readThread").start();

        //暂停几秒钟线程
        try { SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }

        new Thread(() -> {
            out.println(Thread.currentThread().getName()+"\t"+"----come in");
            resource.write();
        },"writeThread").start();

        //暂停几秒钟线程
        try { SECONDS.sleep(4); } catch (InterruptedException e) { e.printStackTrace(); }

        out.println(Thread.currentThread().getName()+"\t"+"number:" +number);*/

        new Thread(() -> {
            resource.tryOptimisticRead();
        }, "readThread").start();

        //暂停2秒钟线程,读过程可以写介入，演示
        //try { SECONDS.sleep(2); } catch (InterruptedException e) { e.printStackTrace(); }

        //暂停6秒钟线程
        try {
            SECONDS.sleep(6);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            out.println(Thread.currentThread().getName() + "\t" + "----come in");
            resource.write();
        }, "writeThread").start();


    }
}
