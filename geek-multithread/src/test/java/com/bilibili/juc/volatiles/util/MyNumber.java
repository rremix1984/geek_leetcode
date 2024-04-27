package com.bilibili.juc.volatiles.util;

public class MyNumber {

    public volatile int number;

    public void addPlusPlus() {
        number++;
    }

}
