/**
 * copyright 2022/1/19
 */
package com.interval.easy;

import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;

/**
    [ARRAY] |
    (简单)
    面试题 10.01. 合并排序的数组
        给定两个排序后的数组A和B，其中A的末端有足够的缓冲空间容纳B。
        编写一个方法，将B合并入A并排序。
        初始化A和B的元素数量分别为m和n。
    示例:
        输入:
            A = [1,2,3,0,0,0], m = 3
            B = [2,5,6],       n = 3
        输出: [1,2,2,3,5,6]
        说明:
            A.length == n + m
*/
public class Interval_10_01_E_Merge {

    @Test
    public void test() {
        int[] target = new int[]{1, 2, 3, 0, 0, 0};
        merge(target, 3, new int[]{2, 5, 6}, 3);
        assertArrayEquals(new int[]{1, 2, 2, 3, 5, 6}, target);
    }

    public void merge(int[] nums1, int m, int[] nums2, int n) {
        // 2024/2/27 NO.3 三指针
    }

}


















/*
// 方法1：
public void merge(int[] nums1, int m, int[] nums2, int n) {
    int[] tmp = new int[m + n];
    int index = 0;
    int i = 0;
    int j = 0;
    while (i < m && j < n)
        if (nums1[i] <= nums2[j])
            tmp[index++] = nums1[i++];
        else
            tmp[index++] = nums2[j++];

    while (i < m)
        tmp[index++] = nums1[i++];

    while (j < n)
        tmp[index++] = nums2[j++];

    //再把数组temp中的值赋给nums1
    // if (m + n >= 0) System.arraycopy(temp, 0, nums1, 0, m + n);
    for (int k = 0; k < m + n; k++)
        nums1[k] = tmp[k];
}

// 方法2：归并排序
public void merge(int[] nums1, int m, int[] nums2, int n) {
    int i = m - 1;
    int j = n - 1;
    int end = m + n - 1;
    while (j >= 0)
        if (i >= 0 && nums1[i] > nums2[j])
            nums1[end--] = nums1[i--];
        else
            nums1[end--] = nums2[j--];
}
*/