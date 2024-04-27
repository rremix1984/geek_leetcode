package com.bilibili.juc.locks.util;

import static java.lang.System.out;
import static java.util.concurrent.TimeUnit.SECONDS;

public class Phone {//资源类

    public synchronized void sendEmail() {
        try {
            SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        out.println("-----sendEmail");
    }

    public synchronized void sendSMS() {
        out.println("-----sendSMS");
    }

    public void hello() {
        out.println("-------hello");
    }

}