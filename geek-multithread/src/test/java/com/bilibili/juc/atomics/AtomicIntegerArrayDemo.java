/**
 * @auther zzyy
 * @create 2022-02-26 10:20
 */
package com.bilibili.juc.atomics;

import java.util.concurrent.atomic.AtomicIntegerArray;

import static java.lang.System.out;

/**
 *
 */
public class AtomicIntegerArrayDemo {

    public static void main(String[] args) {
        AtomicIntegerArray aiArray = new AtomicIntegerArray(new int[5]);
        //AtomicIntegerArray aiArray = new AtomicIntegerArray(5);
        //AtomicIntegerArray aiArray = new AtomicIntegerArray(new int[]{1,2,3,4,5});
        for (int i = 0; i < aiArray.length(); i++)
            out.printf("%d \t ", aiArray.get(i));
        out.println();

        int tmp;
        tmp = aiArray.getAndSet(0, 1122);
        out.printf("%d \t %d \n", tmp, aiArray.get(0));

        tmp = aiArray.getAndIncrement(0);
        out.printf("%d \t %d \n", tmp, aiArray.get(0));
    }

}
