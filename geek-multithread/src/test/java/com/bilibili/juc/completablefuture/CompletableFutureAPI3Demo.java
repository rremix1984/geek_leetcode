/**
 * @auther zzyy
 * @create 2022-01-17 17:18
 */
package com.bilibili.juc.completablefuture;

import java.util.concurrent.CompletableFuture;

import static java.lang.System.out;


public class CompletableFutureAPI3Demo {

    public static void main(String[] args) {
        /*CompletableFuture.supplyAsync(() -> {
            return 1;
        }).thenApply(f ->{
            return f + 2;
        }).thenApply(f ->{
            return f + 3;
        }).thenAccept(out::println);*/

        out.println(CompletableFuture.supplyAsync(() -> "resultA").thenRun(() -> {
        }).join());
        out.println(CompletableFuture.supplyAsync(() -> "resultA").thenAccept(r -> out.println(r)).join());
        out.println(CompletableFuture.supplyAsync(() -> "resultA").thenApply(r -> r + "resultB").join());

    }
}
