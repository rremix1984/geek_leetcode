package com.leetcode.util;

import java.util.*;

public class SystemUtil {

    public static void print(Object o) {
        System.out.println(o);
    }

    /**
     * 打印出arr的元素在一行
     */
    public static void printArr(Integer[] arr) {
        for (int i : arr)
            System.out.printf("%d\t", i);
        System.out.println();
    }

    public static void printArr(int[] arr) {
        for (int i : arr)
            System.out.printf("%d\t", i);
        System.out.println();
    }

    public static void print(List<String> list) {
        for (String s : list)
            System.out.printf("%s", s);
        System.out.println();
    }

    public static void printArr(boolean[] arr) {
        for (boolean i : arr)
            System.out.printf("%b\t", i);
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
        return new HashSet<>(source).containsAll(target)
                && target.containsAll(new HashSet<>(source));
    }

    public static ArrayList<ArrayList<Integer>> getArrayList(int[][] ints) {
        // 检查输入数组是否为 null 或空，以处理边界条件
        if (ints == null || ints.length == 0)
            return new ArrayList<>();

        ArrayList<ArrayList<Integer>> list = new ArrayList<>();
        for (int[] anInt : ints) {
            // 使用泛型提升类型安全
            ArrayList<Integer> inner = new ArrayList<>();
            for (int i : anInt)
                inner.add(i);

            list.add(inner);
        }
        return list;
    }

    public static void printArrays(List<List<Integer>> lists) {
        for (List<Integer> list : lists) {
            printArray(list);
        }
    }

    public static void printArray(List<Integer> list) {
        for (Integer integer : list) {
            System.out.printf("%d\t", integer);
        }
        System.out.println();
    }

    public static void printArr(String[] arr) {
        for (String i : arr)
            System.out.printf("%s\t", i);
        System.out.println();
    }

}
