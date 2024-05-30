/**
 * copyright 2022/1/19
 */
package com.leetcode.sort;

import org.junit.jupiter.api.Test;
import static com.leetcode.util.SwapUtil.swap;
import static com.leetcode.util.SystemUtil.*;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

/**
    [ARRAY] |||||||
    (简单)
    冒泡排序
        是一种简单的排序算法。它重复地走访过要排序的数列，一次比较两个元素，
        如果它们的顺序错误就把它们交换过来。走访数列的工作是重复地进行直到没
        有再需要交换，也就是说该数列已经排序完成。这个算法的名字由来是因为越
        小的元素会经由交换慢慢“浮”到数列的顶端。
    例1：
        输入：[5, 4, 3, 2, 1]
        输出：[1, 2, 3, 4, 5]
    例2：
        输入：[4, 2, 3, 5, 1]
        输出：[1, 2, 3, 4, 5]
    1.1 算法描述
        比较相邻的元素。如果第一个比第二个大，就交换它们两个；
        对每一对相邻元素作同样的工作，从开始第一对到结尾的最后一对，这样在最
        后的元素应该会是最大的数；针对所有的元素重复以上的步骤，除了最后一个；
        重复步骤1~3，直到排序完成。
*/
public class BubbleSort {

    @Test
    public void test() {
        assertArrayEquals(new int[]{1, 2, 3, 4, 5},
                bubbleSort(new int[]{5, 4, 3, 2, 1}));
        assertArrayEquals(new int[]{1, 2, 3, 4, 5},
                bubbleSort(new int[]{4, 2, 3, 5, 1}));
    }

    public static int[] bubbleSort(int[] arr) {
        // 2024/3/19 NO.1 没做出来
        // 2024/3/20 NO.2 做出来了
        // 2024/3/21 NO.3
        // 2024/3/23 NO.4 一遍过
        // 2024/3/25 No.5 一遍过
        // 2024/4/1  NO.6 忘了怎么做了
        // 2024/5/30 NO.7

        return arr;
    }

}















/*
// 方法1：冒泡排序
public int[] bubbleSort(int[] arr) {
    for (int i = 0; i < arr.length - 1; i++) {
        // 相邻元素两两对比
        // arr.length - i - 1 代表着
        // 最后的 i 个元素已经是有序的了，不需要重新排序
        for (int j = 0; j < arr.length - i - 1; j++)
            if (arr[j] > arr[j + 1])
                swap(arr, j + 1, j);
    }
    return arr;
}
*/