package com.bilibili.juc.atomics.util;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;

/**
 * 资源类
 */
public class ClickNumber {

    public int number = 0;
    public LongAdder longAdder = new LongAdder();
    public AtomicLong atomicLong = new AtomicLong(0);
    public LongAccumulator longAccumulator
            = new LongAccumulator((x, y) -> x + y, 0);

    public synchronized void clickBySynchronized() {
        number++;
    }

    public void clickByAtomicLong() {
        atomicLong.getAndIncrement();
    }

    public void clickByLongAdder() {
        longAdder.increment();
    }

    public void clickByLongAccumulator() {
        longAccumulator.accumulate(1);
    }

}