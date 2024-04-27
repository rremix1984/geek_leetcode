package com.bilibili.juc.cf.util;

import java.util.concurrent.Callable;

import static java.lang.System.out;

public class MyThread implements Callable<String> {

    @Override
    public String call() throws Exception {
        out.println("-----come in call() ");
        return "hello Callable";
    }

}