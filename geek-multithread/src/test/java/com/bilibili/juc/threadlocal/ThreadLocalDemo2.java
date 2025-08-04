/**
 * @auther zzyy
 * .【强制】必须回收自定义的 ThreadLocal 变量，尤其在线程池场景下，线程经常会被复用，如果不清理
 * 自定义的 ThreadLocal 变量，可能会影响后续业务逻辑和造成内存泄露等问题。尽量在代理中使用
 * try-finally 块进行回收。
 */
package com.bilibili.juc.threadlocal;

import com.bilibili.juc.threadlocal.util.MyData;

import java.util.concurrent.ExecutorService;

import static java.lang.System.out;
import static java.util.concurrent.Executors.newFixedThreadPool;

public class ThreadLocalDemo2 {

    public static void main(String[] args) throws InterruptedException {
        MyData myData = new MyData();
        ExecutorService threadPool = newFixedThreadPool(3);
        try {
            for (int i = 0; i < 10; i++) {
                threadPool.submit(() -> {
                    try {
                        String name = Thread.currentThread().getName();
                        Integer beforeInt = myData.threadLocalField.get();
                        myData.add();
                        Integer afterInt = myData.threadLocalField.get();
                        out.println(name + "\t" + "beforeInt:" + beforeInt + "\t afterInt: " + afterInt);
                    } finally {
                        myData.threadLocalField.remove();
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
    }

}