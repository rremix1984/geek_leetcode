/**
 * @auther zzyy
 * @create 2022-01-17 18:59
 */
package com.bilibili.juc.completablefuture;

import java.util.concurrent.CompletableFuture;

import static java.lang.System.out;
import static java.util.concurrent.TimeUnit.SECONDS;


public class CompletableFutureCombineDemo {

    public static void main(String[] args) {
        CompletableFuture<Integer> completableFuture1 = CompletableFuture.supplyAsync(() -> {
            out.println(Thread.currentThread().getName() + "\t ---启动");
            //暂停几秒钟线程
            try {
                SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 10;
        });

        CompletableFuture<Integer> completableFuture2 = CompletableFuture.supplyAsync(() -> {
            out.println(Thread.currentThread().getName() + "\t ---启动");
            //暂停几秒钟线程
            try {
                SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 20;
        });

        CompletableFuture<Integer> result = completableFuture1.thenCombine(completableFuture2, (x, y) -> {
            out.println("-----开始两个结果合并");
            return x + y;
        });

        out.println(result.join());
    }

}
