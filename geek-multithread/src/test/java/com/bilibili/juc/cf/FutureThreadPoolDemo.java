/**
 * @auther zzyy
 * @create 2022-01-15 15:20
 */
package com.bilibili.juc.cf;

import java.util.concurrent.*;

import static java.lang.System.currentTimeMillis;
import static java.lang.System.out;
import static java.lang.Thread.currentThread;
import static java.util.concurrent.TimeUnit.MILLISECONDS;


public class FutureThreadPoolDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //3个任务，目前开启多个异步任务线程来处理，请问耗时多少？

        ExecutorService threadPool = Executors.newFixedThreadPool(3);

        long startTime = currentTimeMillis();

        FutureTask<String> futureTask1 = new FutureTask<String>(() -> {
            try {
                MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "task1 over";
        });
        threadPool.submit(futureTask1);

        FutureTask<String> futureTask2 = new FutureTask<String>(() -> {
            try {
                MILLISECONDS.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "task2 over";
        });
        threadPool.submit(futureTask2);

        out.println(futureTask1.get());
        out.println(futureTask2.get());

        try {
            MILLISECONDS.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long endTime = currentTimeMillis();
        out.println("----costTime: " + (endTime - startTime) + " 毫秒");

        out.println(currentThread().getName() + "\t -----end");
        threadPool.shutdown();
    }

    private static void m1() {
        //3个任务，目前只有一个线程main来处理，请问耗时多少？
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

        long endTime = currentTimeMillis();
        out.println("----costTime: " + (endTime - startTime) + " 毫秒" +
                    currentThread().getName() + "\t -----end");
    }

}
