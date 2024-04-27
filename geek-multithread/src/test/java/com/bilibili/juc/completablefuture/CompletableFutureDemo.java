/**
 * @auther zzyy
 * @create 2022-01-14 11:02
 */
package com.bilibili.juc.completablefuture;

import com.bilibili.juc.completablefuture.util.MyThread;
import org.junit.Test;

import java.util.concurrent.*;

import static java.lang.System.out;

/**
 * CompletableFuture
 */
@SuppressWarnings("all")
public class CompletableFutureDemo {

    @Test
    public void test() throws ExecutionException, InterruptedException {
        FutureTask<String> task = new FutureTask<>(new MyThread());
        new Thread(task, "t1").start();
        
        // 获取结果
        out.println(task.get());
    }

}


