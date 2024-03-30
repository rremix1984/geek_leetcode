/**
 * copyright 2019-current year 上海山越信息科技有限公司
 */
package com.leetcode.util;

import java.util.*;
import static java.util.Arrays.sort;

/**
 * 系统工具
 * @author wangxiaozhe
 */
public class SystemUtil {

    public static void print(TreeSet<Long> set) {
        // 打印一个TreeSet数组的元素
        Iterator<Long> iterator = set.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
        System.out.println("=======");
    }

    public static void print(TreeNode node) {
        // 打印一个TreeSet数组的元素
//        Iterator<Long> iterator = set.iterator();
//        while (iterator.hasNext()) {
            System.out.println(node.toString());
//        }
        System.out.println("=======");
    }

    /**
     * 打印出arr的元素在一行
     */
    public static void printArr(Integer[] arr) {
        for (int n : arr)
            System.out.printf("%d\t", n);
        System.out.println();
    }

    public static void printArrs(List<List<Integer>> arr) {
        for (List<Integer> irr : arr) {
            for (Integer i : irr) {
                System.out.printf("%d\t", i);
            }
        }
        System.out.println();
    }

    public static void printListNode(ListNode listNode) {
        System.out.println(listNode.toString());
    }

    public static void printListNodes(ListNode[] listNode) {
        for (ListNode node : listNode) {
            System.out.println(node.toString());
        }
        System.out.println();
    }

    public static void printArr(int[] arr) {
        for (int i : arr)
            System.out.printf("%d\t", i);
        System.out.println();
    }

    public static void print(List<String> list) {
        for (String s : list)
            System.out.printf("%s\t", s);
        System.out.println();
    }

    public static void print(Object s) {
        System.out.println(s);
    }

    public static void printArr(boolean[] arr) {
        for (boolean i : arr)
            System.out.printf("%b\t", i);
        System.out.println();
    }

    public static boolean arraysAllMatch(int[] source, int[] target) {
        sort(source);
        sort(target);
        for (int i = 0; i < source.length; i++)
            if (source[i] != target[i])
                return false;
        return true;
    }

    public static boolean arrayAllMatch(List source, List target) {
        if (source == null && target == null)
            return true;

        if (source == null && target != null)
            return false;

        if (source != null && target == null)
            return false;

        if (source.size() != target.size()) {
            return false;
        }

        HashMap<Object, Integer> sourceMap = new HashMap<>();
        for (Object item : source)
            sourceMap.put(item, sourceMap.getOrDefault(item, 0) + 1);

        HashMap<Object, Integer> targetMap = new HashMap<>();
        for (Object item : target)
            targetMap.put(item, targetMap.getOrDefault(item, 0) + 1);

        return sourceMap.equals(targetMap);
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
        for (List<Integer> list : lists)
            printArray(list);
    }

    public static void printArray(List<Integer> list) {
        for (Integer integer : list)
            System.out.printf("%d\t", integer);

        System.out.println();
    }

    public static void printArrayStr(List<String> list) {
        for (String integer : list)
            System.out.printf("%s\t", integer);

        System.out.println();
    }

    public static void printArr(String[] arr) {
        for (String s : arr)
            System.out.printf("%s\t", s);

        System.out.println();
    }

    public static void preOrder(TreeNode root, List res) {
        if (root == null)
            return;

        res.add(root.val);
        preOrder(root.left, res);
        preOrder(root.right, res);
    }

    public static void postOrder(TreeNode root, List res) {
        if (root == null)
            return;

        preOrder(root.left, res);
        preOrder(root.right, res);
        res.add(root.val);
    }

    public static void inOrder(TreeNode root, List res) {
        if (root == null)
            return;

        preOrder(root.left, res);
        res.add(root.val);
        preOrder(root.right, res);
    }
}
