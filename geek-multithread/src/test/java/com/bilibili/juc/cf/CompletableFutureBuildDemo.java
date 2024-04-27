/**
 * @auther zzyy
 * @create 2022-01-16 16:27
 */
package com.bilibili.juc.cf;

import java.util.concurrent.*;

import static java.lang.System.out;
import static java.util.concurrent.TimeUnit.SECONDS;


public class CompletableFutureBuildDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService threadPool = Executors.newFixedThreadPool(3);

        /*CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {
            out.println(Thread.currentThread().getName());
            //暂停几秒钟线程
            try { SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }
        },threadPool);

        out.println(completableFuture.get());*/

        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            out.println(Thread.currentThread().getName());
            //暂停几秒钟线程
            try {
                SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "hello supplyAsync";
        }, threadPool);
        out.println(completableFuture.get());

        threadPool.shutdown();
    }

}
