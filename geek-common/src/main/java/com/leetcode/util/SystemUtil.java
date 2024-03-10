package com.leetcode.util;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

public class SystemUtil {

    /**
     * 打印出arr的元素在一行
     */
    public static void printArr(int[] arr) {
        for (int i : arr)
            System.out.printf("%d\t", i);
        System.out.println();
    }

    public static boolean arraysAllMatch(int[] source, int[] target) {
        Arrays.sort(source);
        Arrays.sort(target);
        for (int i = 0; i < source.length; i++)
            if (source[i] != target[i])
                return false;
        return true;
    }

    public static boolean arrayAllMatch(List source, List target) {
        return new HashSet<>(source).containsAll(target);
    }

    public static void printArr(String[] arr) {
        for (String i : arr)
            System.out.printf("%s\t", i);
        System.out.println();
    }

}
