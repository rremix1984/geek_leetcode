/**
 * @auther zzyy
 * @create 2022-01-12 16:03
 */
package com.bilibili.juc.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 守护线程 daemonThread
 */
public class ThreadBaseDemo {

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {

        }, "t1");
        t1.start();
    }

}

// java = C++ ---》  (C++)-- = java