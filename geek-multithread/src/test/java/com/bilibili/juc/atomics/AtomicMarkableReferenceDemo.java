/**
 * @auther zzyy
 * @create 2022-02-26 10:57
 */
package com.bilibili.juc.atomics;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicMarkableReference;
import static java.lang.System.out;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
    CAS----Unsafe----do while+ABA---AtomicStampedReference,AtomicMarkableReference
    <p>
        AtomicStampedReference,version号，+1；
        AtomicMarkableReference，一次，false，true
    <p>
 */
@SuppressWarnings("all")
public class AtomicMarkableReferenceDemo {

    static AtomicMarkableReference reference
            = new AtomicMarkableReference(100, false);

    @Test
    public void test() {
        new Thread(() -> {
            boolean marked = reference.isMarked();
            String name = Thread.currentThread().getName();
            out.println(name + "\t" + "默认标识：" + marked);

            //暂停1秒钟线程,等待后面的T2线程和我拿到一样的模式flag标识，都是false
            try {SECONDS.sleep(1);} catch (InterruptedException e) {e.printStackTrace();}
            boolean b = reference.compareAndSet(100, 1000, marked, !marked);
            out.println(name + "\t" + "t1线程 CASresult： " + b + "\n" +
                        name + "\t" + reference.isMarked() +"\n" +
                        name + "\t" + reference.getReference());
        }, "t1").start();

        new Thread(() -> {
            boolean marked = reference.isMarked();
            String name = Thread.currentThread().getName();
            out.println(name + "\t" + "默认标识：" + marked);

            try {SECONDS.sleep(2);} catch (InterruptedException e) {e.printStackTrace();}
            boolean b = reference.compareAndSet(100, 2000, marked, !marked);
            out.println(name + "\t" + "t2线程 CASresult： " + b + "\n" +
                        name + "\t" + reference.isMarked() +"\n" +
                        name + "\t" + reference.getReference());
        }, "t2").start();
    }

}