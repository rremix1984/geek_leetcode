package com.bilibili.juc.locks.util;

import static java.lang.System.out;

/**
 * 资源类，模拟3个售票员卖完50张票
 */
public class Ticket {

    private int number = 50;
    final Object lockObject = new Object();

    public void sale() {
        synchronized (lockObject) {
            String name = Thread.currentThread().getName();
            if (number > 0)
                out.printf(name + "卖出第：%d \t 张，还剩下: %d \n", (number--), number);
        }
    }

}

