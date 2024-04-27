/**
 * @auther zzyy
 * @create 2022-01-17 17:45
 */
package com.bilibili.juc.cf;

import java.util.concurrent.*;

import static java.lang.System.out;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

public class CompletableFutureWithThreadPoolDemo {

    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(5);

        try {
            CompletableFuture<Void> completableFuture = CompletableFuture.supplyAsync(() -> {
                try {
                    MILLISECONDS.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                out.println("1号任务" + "\t" + Thread.currentThread().getName());
                return "abcd";
            }, threadPool).thenRunAsync(() -> {
                try {
                    MILLISECONDS.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                out.println("2号任务" + "\t" + Thread.currentThread().getName());
            }).thenRun(() -> {
                try {
                    MILLISECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                out.println("3号任务" + "\t" + Thread.currentThread().getName());
            }).thenRun(() -> {
                try {
                    MILLISECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                out.println("4号任务" + "\t" + Thread.currentThread().getName());
            });
            out.println(completableFuture.get(2L, SECONDS));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
    }
}
