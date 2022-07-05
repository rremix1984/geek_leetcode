/**
 * copyright 2022/1/19
 */
package com.leetcode.util;


import org.slf4j.Logger;

public class LogUtils {

    public static void info(Logger log, String s) {
        log.info(s);
    }

    public static void info(Logger log, boolean s) {
        log.info("{}",s);
    }

    public static void info(Logger log, int s) {
        log.info("{}",s);
    }

}
