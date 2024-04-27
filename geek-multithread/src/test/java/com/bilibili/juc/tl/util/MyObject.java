package com.bilibili.juc.tl.util;

import static java.lang.System.out;

public class MyObject {

    //这个方法一般不用复写，我们只是为了教学给大家演示案例做说明
    @Override
    protected void finalize() throws Throwable {
        // finalize的通常目的是在对象被不可撤销地丢弃之前执行清理操作。
        out.println("-------invoke finalize method~!!!");
    }

}