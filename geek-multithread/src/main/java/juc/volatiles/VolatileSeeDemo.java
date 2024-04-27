/**
 * @author zzyy
 * @create 2022-01-22 12:45
 */
package juc.volatiles;

import java.util.concurrent.TimeUnit;
import static java.lang.System.out;

/**
 *
 */
public class VolatileSeeDemo {

    //static boolean flag = true;
    static volatile boolean flag = true;

    public static void main(String[] args) {
        new Thread(() -> {
            out.println(Thread.currentThread().getName() + "\t -----come in");
            while (flag) {

            }
            out.println(Thread.currentThread().getName() + "\t -----flag被设置为false，程序停止");
        }, "t1").start();

        //暂停几秒钟线程
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        flag = false;
        out.println(Thread.currentThread().getName() + "\t 修改完成flag: " + flag);
    }

}
