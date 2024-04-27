/**
 * @auther zzyy
 * @create 2022-01-12 16:23
 */
package com.bilibili.juc.base;

import lombok.val;
import org.junit.Test;

import java.util.concurrent.*;
import static java.lang.System.out;
import static java.util.concurrent.TimeUnit.SECONDS;

@SuppressWarnings("all")
public class DaemonDemo {

    @Test
    public void test() {
        Thread t1 = new Thread(() -> {
            val thrd = Thread.currentThread();
            out.println(thrd.getName() + "\t 开始运行, " +
                    (thrd.isDaemon() ? "守护线程" : "用户线程"));
            try {
                SECONDS.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, "t1");
        t1.setDaemon(true);// 必须在start方法之前设置，否则不合法
        t1.start();

        //暂停几秒钟线程
        try {
            SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        out.println(Thread.currentThread().getName() + "\t ----end 主线程");
    }
}
