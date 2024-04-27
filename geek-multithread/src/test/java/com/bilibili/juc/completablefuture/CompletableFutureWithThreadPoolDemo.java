/**
 * @auther zzyy
 * @create 2022-01-17 17:45
 */
package com.bilibili.juc.completablefuture;

import org.junit.Test;

import java.util.concurrent.*;

import static java.lang.System.out;
import static java.util.concurrent.Executors.newFixedThreadPool;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

@SuppressWarnings("all")
public class CompletableFutureWithThreadPoolDemo {

    @Test
    public void test() {
        ExecutorService threadPool = newFixedThreadPool(5);
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
