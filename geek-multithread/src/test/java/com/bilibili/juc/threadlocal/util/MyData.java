package com.bilibili.juc.threadlocal.util;

public class MyData {

    public ThreadLocal<Integer> threadLocalField = ThreadLocal.withInitial(() -> 0);

    public void add() {
        threadLocalField.set(1 + threadLocalField.get());
    }
}