/**
 * copyright 2022/1/19
 */
package com.leetcode.sort;

import org.junit.Test;
import static com.leetcode.util.LogUtil.info;
import static com.leetcode.util.SwapUtil.swap;

/**
    选择排序(Selection-sort)
    是一种简单直观的排序算法。它的工作原理：首先在未排序序列中找到最小
    （大）元素，存放到排序序列的起始位置，然后，再从剩余未排序元素中继续
    寻找最小（大）元素，然后放到已排序序列的末尾。以此类推，直到所有元素
    均排序完毕。

    2.1 算法描述
        n个记录的直接选择排序可经过n-1趟直接选择排序得到有序结果。
        具体算法描述如下：
            初始状态：无序区为R[1..n]，有序区为空；
            第i趟排序(i = 1, 2, 3 … n - 1)开始时，当前有序区和无序区分别为
            R[1..i-1] 和 R(i..n）。该趟排序从当前无序区中-选出关键字最小的记录
            R[k]，将它与无序区的第1个记录R交换，使 R[1..i] 和 R[i+1 .. n) 分别
            变为记录个数增加1个的新有序区和记录个数减少1个的新无序区；n-1趟结束，
            数组有序化了。
*/
public class SelectionSort_x2 {

    @Test
    public void test() {
        info(selectionSort(new int[]{1, 3, 5, 2, 4, 6}));
        info(selectionSort(new int[]{-99999, 1, 8, 99, -1, 9999999}));
        info(selectionSort(new int[]{1, 2, 5, 5, 3, 6}));
        info(selectionSort(null));
    }

    public static int[] selectionSort(int[] arr) {
        if (arr == null || arr.length == 0)
            return arr;

        for (int i = 0; i < arr.length - 1; i++) {
            int min = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] > arr[j])
                    swap(arr, j, min);
            }
        }
        return arr;
    }
}















/**
public int[] selectionSort(int[] arr) {
    if (arr == null || arr.length == 0)
        return new int[0];

    int len = arr.length;
    for (int i = 0; i < len - 1; i++) {
        int min = i;
        for (int j = i + 1; j < len; j++)
            // 寻找最小的数，将最小数的索引保存
            if (arr[j] < arr[min])
                min = j;
        swap(arr, i, min);
    }
    return arr;
}
*/