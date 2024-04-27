package com.bilibili.juc.locks;


import java.awt.print.Book;

import static java.lang.System.out;

/**
 * @auther zzyy
 * @create 2022-01-18 15:56
 */
public class LockSyncDemo {

    Object object = new Object();

    public void m1() {
        synchronized (object) {
            out.println("----hello synchronized code block");
            throw new RuntimeException("-----exp");
        }
    }

    public synchronized void m2() {
        out.println("----hello synchronized m2");
    }

    public static synchronized void m3() {
        out.println("----hello static synchronized m3");
    }


    public static void main(String[] args) {

    }
}
