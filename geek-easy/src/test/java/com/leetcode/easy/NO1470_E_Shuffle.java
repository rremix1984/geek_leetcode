/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;

/**
    [ARRAY] |
    (简单)
    1470. 重新排列数组
        请你将数组按[x1,y1,x2,y2,...,xn,yn]格式重新排列，返回重排后的数组。
    示例 1：
        输入：nums = {2, 5, 1, 3, 4, 7},  n = 3
        输出：{2, 3, 5, 4, 1, 7}
        解释：由于x1=2, x2=5, x3=1, y1=3, y2=4, y3=7，
             所以答案为 {2, 3, 5, 4, 1, 7}
    示例 2：
        输入：nums = {1, 2, 3, 4, 4, 3, 2, 1},  n = 4
        输出：{1, 4, 2, 3, 3, 2, 4, 1}
    示例 3：
        输入：nums = {1, 1, 2, 2},  n = 2
        输出：{1, 2, 1, 2}
*/
public class NO1470_E_Shuffle {

    @Test
    public void test() {
        assertArrayEquals(new int[]{2, 3, 5, 4, 1, 7},
            shuffle(new int[]{2, 5, 1, 3, 4, 7}, 3));
        assertArrayEquals(new int[]{1, 4, 2, 3, 3, 2, 4, 1},
            shuffle(new int[]{1, 2, 3, 4, 4, 3, 2, 1}, 4));
        assertArrayEquals(new int[]{1, 2, 1, 2},
            shuffle(new int[]{1, 1, 2, 2}, 2));
    }

    public int[] shuffle(int[] nums, int n) {
        // 2024/2/27 NO.3
        int[] res = new int[2 * n];

        return res;
    }

}
















/*
// 方法1：
public int[] shuffle(int[] nums, int n) {
    int[] res = new int[2 * n];
    for (int i = 0; i < n; i++) {
        res[2 * i] = nums[i];
        res[2 * i + 1] = nums[i + n];
    }
    return res;
}
*/