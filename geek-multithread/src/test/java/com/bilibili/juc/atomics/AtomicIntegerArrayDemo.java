package com.bilibili.juc.atomics;

import java.util.concurrent.atomic.AtomicIntegerArray;

import static java.lang.System.out;

/**
 * @auther zzyy
 * @create 2022-02-26 10:20
 */
public class AtomicIntegerArrayDemo
{
    public static void main(String[] args)
    {
        AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(new int[5]);
        //AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(5);
        //AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(new int[]{1,2,3,4,5});

        for (int i = 0; i <atomicIntegerArray.length(); i++) {
            out.println(atomicIntegerArray.get(i));
        }

        out.println();

        int tmpInt = 0;

        tmpInt = atomicIntegerArray.getAndSet(0,1122);
        out.println(tmpInt+"\t"+atomicIntegerArray.get(0));

        tmpInt = atomicIntegerArray.getAndIncrement(0);
        out.println(tmpInt+"\t"+atomicIntegerArray.get(0));
    }
}
