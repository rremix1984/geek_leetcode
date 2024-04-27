/**
 * @auther zzyy
 * @create 2022-01-15 15:20
 */
package com.bilibili.juc.cf;

import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import static java.lang.System.out;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * 1 get容易导致阻塞，一般建议放在程序后面，一旦调用不见不散，
 *   非要等到结果才会离开，不管你是否计算完成，容易程序堵塞。
 * 2 假如我不愿意等待很长时间，我希望过时不候，可以自动离开.
 */
@SuppressWarnings("all")
public class FutureAPIDemo {

    @Test
    public void test() throws ExecutionException, InterruptedException, TimeoutException {
        FutureTask<String> fTask = new FutureTask<>(() -> {
            String name = Thread.currentThread().getName();
            out.println(name + "\t -----come in");
            try {SECONDS.sleep(5);} catch (InterruptedException e) {e.printStackTrace();}
            return "task over";
        });
        Thread t1 = new Thread(fTask, "t1");
        t1.start();

        out.println(Thread.currentThread().getName() + "\t ----忙其它任务了");
        // 场景1：非要等到结果，不管你是否计算完成，容易产生程序阻塞
        // out.println(futureTask.get());

        // 场景2：过时不候，只等你3秒
        // out.println(futureTask.get(3, SECONDS));

        // 场景3：完成了再过来调用
        while (true) {
            if (fTask.isDone()) {
                out.println(fTask.get());
                break;
            } else {
                //暂停毫秒
                try {MILLISECONDS.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
                out.println("正在处理中，不要再催了，越催越慢 ，再催熄火");
            }
        }
    }

}