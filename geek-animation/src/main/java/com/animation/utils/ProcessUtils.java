package com.animation.utils;

import java.lang.management.ManagementFactory;

public class ProcessUtils {

    public static String getCurrentProcessId() {
        return ManagementFactory.getRuntimeMXBean().getName().split("@")[0];
    }
}