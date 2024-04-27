package com.bilibili.juc.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.System.out;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * @auther zzyy
 * @create 2022-01-17 16:36
 */
public class CompletableFutureAPI2Demo {

    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(3);

        CompletableFuture.supplyAsync(() -> {
            //暂停几秒钟线程
            try {
                SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            out.println("111");
            return 1;
        }, threadPool).handle((f, e) -> {
            int i = 10 / 0;
            out.println("222");
            return f + 2;
        }).handle((f, e) -> {
            out.println("333");
            return f + 3;
        }).whenComplete((v, e) -> {
            if (e == null) {
                out.println("----计算结果： " + v);
            }
        }).exceptionally(e -> {
            e.printStackTrace();
            out.println(e.getMessage());
            return null;
        });

        out.println(Thread.currentThread().getName() + "----主线程先去忙其它任务");

        threadPool.shutdown();
    }
}
