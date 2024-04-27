package com.bilibili.juc.atomics;

import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.LongBinaryOperator;

import static java.lang.System.out;

/**
 * @auther zzyy
 * @create 2022-02-26 18:51
 */
public class LongAdderAPIDemo {

    public static void main(String[] args) {
        LongAdder longAdder = new LongAdder();

        longAdder.increment();
        longAdder.increment();
        longAdder.increment();

        out.println(longAdder.sum());

        LongAccumulator longAccumulator = new LongAccumulator((left, right) -> left + right, 0);

        longAccumulator.accumulate(1);//1
        longAccumulator.accumulate(3);//4

        out.println(longAccumulator.get());
    }
}
