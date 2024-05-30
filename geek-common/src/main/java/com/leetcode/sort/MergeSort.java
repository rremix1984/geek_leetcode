/**
 * copyright 2022/1/19
 */
package com.leetcode.sort;

/**
    5、归并排序（Merge Sort）
        归并排序是建立在归并操作上的一种有效的排序算法。该算法是采用分治法（Divide and Conquer）的一个非常典型的应用。
        将已有序的子序列合并，得到完全有序的序列；即先使每个子序列有序，再使子序列段间有序。若将两个有序表合并成一个有序表，称为 2-路归并。
    5.1 算法描述
        把长度为n的输入序列分成两个长度为n/2的子序列；
        对这两个子序列分别采用归并排序；
        将两个排序好的子序列合并成一个最终的排序序列。
*/
public class MergeSort {

    public static int[] mergeSort(int[] arr, int left, int right) {
        return arr;
    }

}




















/**
// 方法1：
public static void mergeSort(int[] arr, int left, int right) {
    if (left >= right)
        return;

    int mid = left + (right - left) / 2;

    // 保证左边有序
    mergeSort(arr, left, mid);

    // 保证右边边有序
    mergeSort(arr, mid + 1, right);

    // 把左、右两个排好序的数组，合并起来
    merge(arr, left, mid, right);
}

public static void merge(int[] arr, int left, int mid ,int right) {
    int[] tmp = new int[right - left + 1];

    // i 是第一个数组起始位置
    int i = left;

    // j 是第二个数组起始位置
    int j = mid + 1;

    // 合并后的数组指标
    int k = 0;

    // 两个数组一起挪
    while (i <= mid && j <= right)
        tmp[k++] = arr[i] <= arr[j] ? arr[i++] : arr[j++];

    // 前面的剩下的继续挪动
    while (i <= mid)
        tmp[k++] = arr[i++];

    // 后面的剩下的继续挪动
    while (j <= right)
        tmp[k++] = arr[j++];

    // 改变原始数组
    System.arraycopy(tmp, 0, arr, left, tmp.length);
}
*/