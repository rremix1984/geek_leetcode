package com.leetcode.util;

import lombok.extern.slf4j.Slf4j;

/**
 * @author 大硕
 * 2019-04-07 9:55 AM
 **/
@Slf4j
@SuppressWarnings("all")
public class LogUtil {

    public static void info(String msg) {
        log.info(msg);
    }

    public static void info(Object obj) {
        log.info("{}", obj);
    }

    public static void info(Object[] obj) {
        for (Object o : obj)
            log.info("{}", o);
    }

    public static void error(String msg) {
        log.error(msg);
    }

    public static void warn(String msg) {
        log.warn(msg);
    }

    public static void error(String msg, Throwable e) {
        log.error(msg, e);
    }

    public static void info(String format, Object... msg) {
        log.info(format, msg);
    }

    public static void error(String format, Object... msg) {
        log.error(format, msg);
    }

    public static void warn(String format, Object... msg) {
        log.warn(format, msg);
    }

    public static void infoNoBr(String s) {

    }
}