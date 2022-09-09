package com.leetcode.util;

/**
 * @author 大硕
 * 2019-03-31 1:00 PM
 **/
@SuppressWarnings("all")
public class SwapUtil {

    @SuppressWarnings("all")
    public static void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    @SuppressWarnings("all")
    public static void swap(int[] pre, int[] cur) {
        int[] tmp = pre;
        pre = cur;
        cur = tmp;
    }

    @SuppressWarnings("all")
    public static void swap(char[] c, int i, int j) {
        char temp = c[i];
        c[i] = c[j];
        c[j] = temp;
    }

    @SuppressWarnings("all")
    public static void swap(TreeNode root) {
        TreeNode tmp = root.left;
        root.left = root.right;
        root.right = tmp;
    }

    @SuppressWarnings("all")
    public static void swap(TreeNode t1, TreeNode t2) {
        int temp = t1.val;
        t1.val = t2.val;
        t2.val = temp;
    }

    public static void swapLR(TreeNode root) {
        TreeNode temp = root.left;
        root.left = root.right;
        root.right = temp;
    }

    @SuppressWarnings("all")
    public static void swap(int t1, int t2) {
        int temp = t1;
        t1 = t2;
        t2 = temp;
    }

    @SuppressWarnings("all")
    public static void swap(ListNode left, ListNode right) {
        int tmp = left.val;
        left.val = right.val;
        right.val = tmp;
    }

}