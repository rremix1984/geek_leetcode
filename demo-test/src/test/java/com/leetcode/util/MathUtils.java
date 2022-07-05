package com.leetcode.util;

import lombok.NoArgsConstructor;

import static java.util.Arrays.copyOf;
import static lombok.AccessLevel.PRIVATE;

/**
 * 工具类
 */
@NoArgsConstructor(access = PRIVATE)
public class MathUtils {

    /**
     * 三个值取最大
     */
    public static int maxs(int... a) {
        if (a == null || a.length == 0) {
            return -1;
        }

        if (a.length == 1) {
            return a[0];
        }

        return getmax(a);
    }

    public static int max(int... input) {
        int max = -2147483648;
        for (int element : input) {
            if (element > max) {
                max = element;
            }
        }
        return max;
    }

    private static int getmax(int[] a) {
        return getmax(a, Integer.MIN_VALUE);
    }

    /**
     * 递归方法 找最大值
     */
    private static int getmax(int[] a, int max) {
        int lastindex = a.length-1;
        int last = a[lastindex];
        if (a.length == 2) {
            return max(last, max);
        }
        //每次数组缩短一个元素，最后一个元素与缩短的数组进行 getmax 操作
        return getmax(copyOf(a, lastindex), max(max, last));
    }

    private static int max(int i, int j) {
        if (i >= j)
            return i;
        return j;
    }

    private static long max(long i, long j) {
        if (i >= j)
            return i;
        return j;
    }

    public static void main(String[] args) {
        System.out.println(maxs(0, 6, 3, 41111, 5, 2, 5, 8, 109));
    }
}
