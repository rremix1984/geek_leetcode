/**
 * @copyright wxz
 */
package com.leetcode.util;

/**
 * @author 大硕
 * 2019-03-31 1:00 PM
 **/
@SuppressWarnings("all")
public class SwapUtil<E> {

    @SuppressWarnings("all")
    public static <E> void swap(E[] nums, int i, int j) {
        E temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    public static void swap(char[] nums, int i, int j) {
        char temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    public static void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    public static <E> void swap(E[] nums, Integer i, Integer j) {
        E temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    @SuppressWarnings("all")
    public static <E> void swap(E[] pre, E[] cur) {
        E[] tmp = pre;
        pre = cur;
        cur = tmp;
    }

    @SuppressWarnings("all")
    public static <E> void swap(TreeNode<E> root) {
        TreeNode<E> tmp = root.left;
        root.left = root.right;
        root.right = tmp;
    }

    @SuppressWarnings("all")
    public static <E> void swap(TreeNode<E> t1, TreeNode<E> t2) {
        E temp = t1.val;
        t1.val = t2.val;
        t2.val = temp;
    }

    public static <E> void swapLR(TreeNode<E> root) {
        TreeNode<E> temp = root.left;
        root.left = root.right;
        root.right = temp;
    }

    @SuppressWarnings("all")
    public static <E> void swap(E t1, E t2) {
        E temp = t1;
        t1 = t2;
        t2 = temp;
    }

    public static <E> void swap(ListNode<E> left, ListNode<E> right) {
        E tmp = left.val;
        left.val = right.val;
        right.val = tmp;
    }

}