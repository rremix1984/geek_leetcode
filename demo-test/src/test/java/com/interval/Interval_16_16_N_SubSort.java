/**
 * copyright@2019/07/27 lyc
 */
package com.interval;

import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;

/**
    [ARRAY] |
    (中等)
    Interval 16.16 部分排序
        给定一个整数数组，编写一个函数，找出索引m和n，只要将索引区间
    [m,n]的元素排好序，整个数组就是有序的。注意：n - m尽量最小，也就是说，
    找出符合条件的最短序列。函数返回值为[m, n]，若不存在这样的m和n
    （例如整个数组是有序的），请返回[-1, -1]。
    示例：
        输入： [1, 2, 4, 7, 10, 11, 7, 12, 6, 7, 16, 18, 19]
        输出： [3, 9]
    提示：
        0 <= len(array) <= 1000000
    Related Topics:栈,贪心,数组,双指针,排序,单调栈
*/
public class Interval_16_16_N_SubSort {

    @Test
    public void test() {
        assertArrayEquals(new int[]{3, 9},
            subSort(new int[]{1, 2, 4, 7, 10, 11, 7,
                    12, 6, 7, 16, 18, 19}));
    }

    public int[] subSort(int[] array) {
        // 2024/3/18 NO.1 不会做
        return new int[]{-1, -1};
    }

}
















/*
// 方法1：
public int[] subSort(int[] array) {
    int n = array.length;
    if (n <= 1)
        return new int[]{-1, -1};

    return dfs(array, 0, n - 1);
}

public static int[] dfs(int[] arr, int left, int right) {
    int l = -1;
    int r = -1;
    int max = arr[left];
    int min = arr[right];

    for (int i = left; i <= right; i++)
        if (arr[i] >= max)
            max = arr[i];
        else
            r = i;

    if (r == -1)
        return new int[]{-1, -1};

    for (int i = right; i >= left; i--)
        if (arr[i] <= min)
            min = arr[i];
        else
            l = i;

    return new int[]{l, r};
}
*/