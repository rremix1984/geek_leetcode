package com.leetcode.util;

import lombok.NoArgsConstructor;

import static java.lang.Integer.MIN_VALUE;
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

    /**
     * 三个值取最小
     */
    public static int mins(int... a) {
        if (a == null || a.length == 0)
            return -1;

        if (a.length == 1)
            return a[0];

        return getmin(a);
    }

    public static int max(int... input) {
        int max = MIN_VALUE;
        for (int element : input) {
            if (element > max) {
                max = element;
            }
        }
        return max;
    }

    private static int getmax(int[] a) {
        return getmax(a, MIN_VALUE);
    }

    private static int getmin(int[] a) {
        return getmin(a, Integer.MAX_VALUE);
    }

    /**
     * 递归方法 找最大值
     */
    private static int getmax(int[] a, int max) {
        int lastindex = a.length-1;
        int last = a[lastindex];
        if (a.length == 1) {
            return max(last, max);
        }
        //每次数组缩短一个元素，最后一个元素与缩短的数组进行 getmax 操作
        return getmax(copyOf(a, lastindex), max(max, last));
    }

    /**
     * 递归方法 找最小值
     */
    private static int getmin(int[] a, int min) {
        int lastindex = a.length - 1;
        int last = a[lastindex];
        if (a.length == 1)
            return min(last, min);

        //每次数组缩短一个元素，最后一个元素与缩短的数组进行 getmax 操作
        return getmin(copyOf(a, lastindex), min(min, last));
    }

    private static int max(int i, int j) {
        if (i >= j)
            return i;
        return j;
    }

    private static int min(int i, int j) {
        if (i <= j)
            return i;
        return j;
    }

    private static long max(long i, long j) {
        if (i >= j)
            return i;
        return j;
    }

    private static long min(long i, long j) {
        if (i <= j)
            return i;
        return j;
    }

    public static int bin2Dec(String binaryString){
        int sum = 0;
        for(int i = 0;i < binaryString.length();i++){
            char ch = binaryString.charAt(i);
            if(ch > '2' || ch < '0')
                throw new NumberFormatException(String.valueOf(i));
            sum = sum * 2 + (binaryString.charAt(i) - '0');
        }
        return sum;
    }

    public static void main(String[] args) {
        System.out.println(maxs(0, 6, 3, 41111, 5, 2, 5, 8, 109));
        System.out.println(mins(0, 6, 3));
        System.out.println(maxs(0, -6, -3, -41111, -5, -2, -5, -8, -109));
    }
}
