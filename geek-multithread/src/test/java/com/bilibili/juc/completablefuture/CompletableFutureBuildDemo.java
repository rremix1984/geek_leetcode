/**
 * @auther zzyy
 * @create 2022-01-16 16:27
 */
package com.bilibili.juc.completablefuture;

import java.util.concurrent.*;

import static java.lang.System.out;
import static java.util.concurrent.TimeUnit.SECONDS;


public class CompletableFutureBuildDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 方法1：默认线程池 ForkJoinPool
        // runAsync 返回的是 null，用的线程池是：ForkJoinPool
        CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {
            out.println(Thread.currentThread().getName());
            //暂停几秒钟线程
            try {SECONDS.sleep(1);} catch (InterruptedException e) {e.printStackTrace();}
        });
        out.println(completableFuture.get());

        // 方法2：指定一个线程池
        ExecutorService threadPool = Executors.newFixedThreadPool(3);
//        CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {
//            out.println(Thread.currentThread().getName());
//            //暂停几秒钟线程
//            try { SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }
//        }, threadPool);
//        out.println(completableFuture.get());

        // 方法3：指定一个线程池
//        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
//            out.println(Thread.currentThread().getName());
//            //暂停几秒钟线程
//            try {SECONDS.sleep(1);} catch (InterruptedException e) {e.printStackTrace();}
//            return "hello supplyAsync";
//        }, threadPool);
//
//        out.println(completableFuture.get());
//        threadPool.shutdown();
    }

}
