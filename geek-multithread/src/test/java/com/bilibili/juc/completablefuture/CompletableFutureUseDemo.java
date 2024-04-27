/**
 * @auther zzyy
 * @create 2022-01-16 16:53
 */
package com.bilibili.juc.completablefuture;

import org.junit.Test;

import java.util.concurrent.*;

import static java.lang.System.out;
import static java.util.concurrent.Executors.newFixedThreadPool;
import static java.util.concurrent.TimeUnit.SECONDS;

@SuppressWarnings("all")
public class CompletableFutureUseDemo {

    @Test
    public void test() throws ExecutionException, InterruptedException {
        ExecutorService threadPool = newFixedThreadPool(3);
        try {
            CompletableFuture.supplyAsync(() -> {
                out.println(Thread.currentThread().getName() + "----come in");
                int result = ThreadLocalRandom.current().nextInt(10);
                try {SECONDS.sleep(1);} catch (InterruptedException e) {e.printStackTrace();}
                out.println("-----1秒钟后出结果：" + result);
                if (result > 2) {
                    int i = 10 / 0;
                }
                return result;
            }, threadPool).whenComplete((v, e) -> {
                if (e == null)
                    out.println("-----计算完成，更新系统UpdateValue：" + v);
            }).exceptionally(e -> {
                e.printStackTrace();
                out.println("异常情况：" + e.getCause() + "\t" + e.getMessage());
                return null;
            });
            out.println(Thread.currentThread().getName() + "线程先去忙其它任务");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
        //主线程不要立刻结束，否则CompletableFuture默认使用的线程池会立刻关闭:暂停3秒钟线程
        //try { SECONDS.sleep(3); } catch (InterruptedException e) { e.printStackTrace(); }
    }

    private static void future1() throws InterruptedException, ExecutionException {
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
            out.println(Thread.currentThread().getName() + "----come in");
            int result = ThreadLocalRandom.current().nextInt(10);
            try {SECONDS.sleep(1);} catch (InterruptedException e) {e.printStackTrace();}
            out.println("-----1秒钟后出结果：" + result);
            return result;
        });
        out.println(Thread.currentThread().getName() + "线程先去忙其它任务");
        out.println(completableFuture.get());
    }
}
