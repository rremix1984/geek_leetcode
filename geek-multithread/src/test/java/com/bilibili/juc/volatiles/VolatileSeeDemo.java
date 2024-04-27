/**
 * @author zzyy
 * @create 2022-01-22 12:45
 */
package com.bilibili.juc.volatiles;

import static java.lang.System.out;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 *
 */
public class VolatileSeeDemo {

    //static boolean flag = true;
    static volatile boolean flag = true;

    public static void main(String[] args) {
        String name = Thread.currentThread().getName();
        new Thread(() -> {
            out.println(name + "\t -----come in");
            while (flag) {

            }
            out.println(name + "\t -----flag被设置为false，程序停止");
        }, "t1").start();

        //暂停几秒钟线程
        try {
            SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        flag = false;
        out.println(Thread.currentThread().getName() + "\t 修改完成flag: " + flag);
    }

}
