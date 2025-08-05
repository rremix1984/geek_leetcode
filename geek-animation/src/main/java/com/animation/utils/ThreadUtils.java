package com.animation.utils;

public class ThreadUtils {

    public static void execute(Runnable runnable) {
        new Thread(runnable).start();
    }
}