/**
 * @auther zzyy
 */
package com.bilibili.juc.tl;

import org.junit.Test;

import java.lang.ref.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.lang.System.out;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

@SuppressWarnings("all")
public class ReferenceDemo {

    @Test
    public void test() {
        MyObject myObject = new MyObject();
        ReferenceQueue<MyObject> referenceQueue = new ReferenceQueue<>();
        PhantomReference<MyObject> phantomReference = new PhantomReference<>(myObject, referenceQueue);
        //out.println(phantomReference.get());

        List<byte[]> list = new ArrayList<>();

        new Thread(() -> {
            while (true) {
                list.add(new byte[1 * 1024 * 1024]);
                try {
                    MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                out.println(phantomReference.get() + "\t" + "list add ok");
            }
        }, "t1").start();

        new Thread(() -> {
            while (true) {
                Reference<? extends MyObject> reference = referenceQueue.poll();
                if (reference != null) {
                    out.println("-----有虚对象回收加入了队列");
                    break;
                }
            }
        }, "t2").start();
    }

    private static void weakReference() {
        WeakReference<MyObject> weakReference = new WeakReference<>(new MyObject());
        out.println("-----gc before 内存够用： " + weakReference.get());

        System.gc();
        //暂停几秒钟线程
        try {
            SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        out.println("-----gc after 内存够用： " + weakReference.get());
    }

    private static void softReference() {
        SoftReference<MyObject> softReference = new SoftReference<>(new MyObject());
        //out.println("-----softReference:"+softReference.get());

        System.gc();
        try {
            SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        out.println("-----gc after内存够用: " + softReference.get());

        try {
            byte[] bytes = new byte[20 * 1024 * 1024];//20MB对象
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            out.println("-----gc after内存不够: " + softReference.get());
        }
    }

    private static void strongReference() {
        MyObject myObject = new MyObject();
        out.println("gc before: " + myObject);

        myObject = null;
        System.gc();//人工开启GC，一般不用

        //暂停毫秒
        try {
            MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        out.println("gc after: " + myObject);
    }
}

class MyObject {
    //这个方法一般不用复写，我们只是为了教学给大家演示案例做说明
    @Override
    protected void finalize() throws Throwable {
        // finalize的通常目的是在对象被不可撤销地丢弃之前执行清理操作。
        out.println("-------invoke finalize method~!!!");
    }

}