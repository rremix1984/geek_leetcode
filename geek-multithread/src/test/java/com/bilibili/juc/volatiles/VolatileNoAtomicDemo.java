/**
 * @auther zzyy
 * @create 2022-02-23 16:54
 */
package com.bilibili.juc.volatiles;

import com.bilibili.juc.volatiles.util.MyNumber;
import org.junit.Test;
import static java.lang.System.out;
import static java.util.concurrent.TimeUnit.SECONDS;

@SuppressWarnings("all")
public class VolatileNoAtomicDemo {

    @Test
    public void test() {
        MyNumber num = new MyNumber();
        for (int i = 1; i <= 10; i++)
            new Thread(() -> {
                for (int j = 1; j <= 1000; j++)
                    num.addPlusPlus();
            }, String.valueOf(i)).start();

        //暂停几秒钟线程
        try {SECONDS.sleep(2);} catch (InterruptedException e) {e.printStackTrace();}
        out.println(num.number);
    }

}
