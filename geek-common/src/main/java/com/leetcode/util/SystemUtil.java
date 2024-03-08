package com.leetcode.util;

public class SystemUtil {

    /**
     * 打印出arr的元素在一行
     */
    public static void printArr(int[] arr) {
        for (int i : arr)
            System.out.printf("%d\t", i);
        System.out.println();
    }

    public static void printArr(String[] arr) {
        for (String i : arr)
            System.out.printf("%s\t", i);
        System.out.println();
    }

}
