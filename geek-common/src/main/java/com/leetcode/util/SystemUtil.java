/**
 * copyright 2019-current year 上海山越信息科技有限公司
 */
package com.leetcode.util;

import java.util.*;

import static java.lang.Integer.parseInt;
import static java.lang.System.out;
import static java.util.Arrays.sort;

/**
 * 系统工具
 * @author wangxiaozhe
 */
public class SystemUtil {

    public static void print(TreeSet<Long> set) {
        // 打印一个TreeSet数组的元素
        Iterator<Long> iterator = set.iterator();
        while (iterator.hasNext())
            out.println(iterator.next());
        out.println("=======");
    }

    public static char[] int2char(int num) {
        return ("" + num).toCharArray();
    }

    public static int char2int(char[] s) {
        return parseInt(new String(s));
    }

    public static void print(TreeNode node) {
        // 打印一个TreeSet数组的元素
//        Iterator<Long> iterator = set.iterator();
//        while (iterator.hasNext()) {
            out.println(node.toString());
//        }
        out.println("=======");
    }

    /**
     * 打印出arr的元素在一行
     */
    public static void printArr(Integer[] arr) {
        for (int n : arr)
            out.printf("%d\t", n);
        out.println();
    }

    public static void printArrs(List<List<Integer>> arr) {
        for (List<Integer> irr : arr)
            for (Integer i : irr)
                out.printf("%d\t", i);
        out.println();
    }

    public static void printListNode(ListNode listNode) {
        out.println(listNode.toString());
    }

    public static void printListNodes(ListNode[] listNode) {
        for (ListNode node : listNode)
            out.println(node.toString());
        out.println();
    }

    public static void printArr(int[] arr) {
        for (int i : arr)
            out.printf("%d\t", i);
        out.println();
    }

    public static void printArr(int[][] arr) {
        for (int[] i : arr) {
            for (int j = 0; j < i.length; j++)
                out.printf("%d\t", j);
            out.println();
        }
    }

    public static void print(List<String> list) {
        for (String s : list)
            out.printf("%s\t", s);
        out.println();
    }

    public static void print(Object s) {
        out.println(s);
    }

    public static void printArr(boolean[] arr) {
        for (boolean i : arr)
            out.printf("%b\t", i);
        out.println();
    }

    public static void printArr(boolean[][] arr) {
        for (int j = 0; j < arr[0].length; j++)
            out.printf("%d\t\t", j);
        out.println();
        for (boolean[] i : arr) {
            for (boolean b : i)
                out.printf("%b\t", b);
            out.println();
        }
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

        if (source.size() != target.size())
            return false;

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
            out.printf("%d\t", integer);

        out.println();
    }

    public static void printArrayStr(List<String> list) {
        for (String integer : list)
            out.printf("%s\t", integer);

        out.println();
    }

    public static void printArr(String[] arr) {
        for (String s : arr)
            out.printf("%s\t", s);

        out.println();
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
