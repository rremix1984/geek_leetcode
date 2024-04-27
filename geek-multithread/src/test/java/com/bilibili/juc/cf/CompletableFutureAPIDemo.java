/**
 * @auther zzyy
 * @create 2022-01-17 15:20
 */
package com.bilibili.juc.cf;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static java.lang.System.out;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 *
 */
public class CompletableFutureAPIDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

    }

    /**
     * 获得结果和触发计算
     */
    private static void group1() throws InterruptedException, ExecutionException {

        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            //暂停几秒钟线程
            try {
                SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "abc";
        });

        //out.println(completableFuture.get());
        //out.println(completableFuture.get(2L, SECONDS));
        //out.println(completableFuture.join());

        //暂停几秒钟线程
        //try { SECONDS.sleep(2); } catch (InterruptedException e) { e.printStackTrace(); }

        //out.println(completableFuture.getNow("xxx"));
        out.println(completableFuture.complete("completeValue") + "\t" + completableFuture.get());
    }

}
