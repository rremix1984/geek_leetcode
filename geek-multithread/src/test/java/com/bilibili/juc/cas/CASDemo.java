package com.bilibili.juc.cas;

import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.System.out;

/**
 * @auther zzyy
 * @create 2022-02-24 11:15
 */
public class CASDemo {

    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(5);

        out.println(atomicInteger.compareAndSet(5, 2022) + "\t" + atomicInteger.get());
        out.println(atomicInteger.compareAndSet(5, 2022) + "\t" + atomicInteger.get());

        atomicInteger.getAndIncrement();
    }

}
