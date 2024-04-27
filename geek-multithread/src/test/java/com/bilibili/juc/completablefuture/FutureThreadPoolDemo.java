/**
 * @auther zzyy
 * @create 2022-01-15 15:20
 */
package com.bilibili.juc.completablefuture;

import org.junit.Test;
import java.util.concurrent.*;
import static java.lang.System.*;
import static java.lang.Thread.currentThread;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * 假设：有3个任务，目前开启多个异步任务线程来处理，请问耗时多少？
 */
@SuppressWarnings("all")
public class FutureThreadPoolDemo {

    @Test
    public void test() throws ExecutionException, InterruptedException {
        ExecutorService threadPool = Executors.newFixedThreadPool(3);
        long startTime = currentTimeMillis();

        // 线程1：
        FutureTask<String> fTask1 = new FutureTask<String>(() -> {
            try {MILLISECONDS.sleep(500);} catch (InterruptedException e) {e.printStackTrace();}
            return "task1 over";
        });
        threadPool.submit(fTask1);

        // 线程2：
        FutureTask<String> fTask2 = new FutureTask<String>(() -> {
            try {MILLISECONDS.sleep(300);} catch (InterruptedException e) {e.printStackTrace();}
            return "task2 over";
        });
        threadPool.submit(fTask2);

        // 通过future 获取返回值
        out.println(fTask1.get());
        out.println(fTask2.get());
        try {MILLISECONDS.sleep(300);} catch (InterruptedException e) {e.printStackTrace();}

        out.println("----costTime: " + (currentTimeMillis() - startTime) + " 毫秒 \n" +
                    currentThread().getName() + "\t -----end");
        threadPool.shutdown();
    }

    /**
     * 3个任务，目前只有一个线程main来处理，请问耗时多少？
     */
    private static void m1() {
        long startTime = currentTimeMillis();
        //暂停毫秒
        try {
            MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            MILLISECONDS.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            MILLISECONDS.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        out.println("----costTime: " + (currentTimeMillis() - startTime) + " 毫秒" +
                    currentThread().getName() + "\t -----end");
    }

}
