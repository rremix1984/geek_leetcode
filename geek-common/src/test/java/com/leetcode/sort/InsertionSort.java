/**
 * copyright 2022/1/19
 */
package com.leetcode.sort;

import org.junit.Test;

import static com.leetcode.BaseTest.generateRandomArray;
import static java.lang.System.out;
import static java.util.Arrays.stream;
import static org.junit.Assert.assertArrayEquals;

/**
    插入排序（Insertion-Sort）
    的算法描述是一种简单直观的排序算法。它的工作原理是通过构建有序序列，
    对于未排序数据，在已排序序列中从后向前扫描，找到相应位置并插入。

    3.1 算法描述
        一般来说，插入排序都采用in-place在数组上实现。具体算法描述如下：
        从第一个元素开始，该元素可以认为已经被排序；
        取出下一个元素，在已经排序的元素序列中从后向前扫描；
        如果该元素（已排序）大于新元素，将该元素移到下一位置；
        重复步骤3，直到找到已排序的元素小于或者等于新元素的位置；
        将新元素插入到该位置后；
        重复步骤2~5。
*/
public class InsertionSort {

    @Test
    public void test() {
//        assertArrayEquals(new int[]{1, 2, 3, 4, 5, 6},
//                insertionSort(new int[]{1, 3, 5, 2, 4, 6}));
//        assertArrayEquals(new int[]{-99999, -1, 1, 8, 99, 9999999},
//                insertionSort(new int[]{-99999, 1, 8, 99, -1, 9999999}));
//        assertArrayEquals(new int[]{1, 2, 3, 5, 5, 6},
//                insertionSort(new int[]{1, 2, 5, 5, 3, 6}));
        int[] origin = generateRandomArray(100, 100);
        for (int i : stream(origin).sorted().toArray()) {
            out.print(i + " ");
        }
        out.println();
        for (int i : insertionSort(origin)) {
            out.print(i + " ");
        }
    }

    public static int[] insertionSort(int[] arr) {
        // 从第一个元素之后查找
        for (int i = 1; i < arr.length; i++) {
            int j = i - 1;
            int cur = arr[i];
            // 当找到的元素小于pre时，插入前面
            // 挨个【向后】 "挪" 动
            // 最后把 j + 1 这个位置让出来
            while (j >= 0 && arr[j] > cur) {
                arr[j + 1] = arr[j];
                j--;
            }
            // j + 1 这个位置让出来之后，把 cur 放进去
            arr[j + 1] = cur;
        }
        return arr;
    }

//    public static int[] insertionSort(int[] arr) {
//        return arr;
//    }
}
















/*
// 方法1：
public static int[] insertionSort(int[] arr) {
    // 从第一个元素之后查找
    for (int i = 1; i < arr.length; i++) {
        int j = i - 1;
        int cur = arr[i];
        // 当找到的元素小于pre时，插入前面
        // 挨个【向后】 "挪" 动
        // 最后把 j + 1 这个位置让出来
        while (j >= 0 && arr[j] > cur) {
            arr[j + 1] = arr[j];
            j--;
        }
        // j + 1 这个位置让出来之后，把 cur 放进去
        arr[j + 1] = cur;
    }
    return arr;
}
*/