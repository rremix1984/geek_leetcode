/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;

/**
    [ARRAY] |
    (简单)
    905. 按奇偶排序数组
        给你一个整数数组nums，将nums中的的所有偶数元素移动到数组的前面，
        后跟所有奇数元素。
        返回满足此条件的任一数组 作为答案。
    示例 1：
        输入：nums = {3, 1, 2, 4}
        输出：{2, 4, 3, 1}
        解释：{4, 2, 3, 1}、
             {2, 4, 1, 3}
          和 {4, 2, 1, 3} 也会被视作正确答案。
    示例 2：
        输入：nums = {0}
        输出：{0}
    提示：
        1 <= nums.length <= 5000
        0 <= nums[i] <= 5000
*/
public class NO905_E_SortArrayByParity {

    @Test
    public void test() {
        assertArrayEquals(new int[]{2, 4, 3, 1},
            sortArrayByParity(new int[]{3, 1, 2, 4}));
        assertArrayEquals(new int[]{0},
            sortArrayByParity(new int[]{0}));
    }

    public int[] sortArrayByParity(int[] nums) {
        // 2024/2/27 NO.3
        int[] res = new int[nums.length];
        return res;
    }

}


















/*
// 方法1：
public int[] sortArrayByParity(int[] nums) {
    int i = 0;
    int[] res = new int[nums.length];
    for (int num : nums)
        if (num % 2 == 0)
            res[i++] = num;

    for (int num : nums)
        if (num % 2 == 1)
            res[i++] = num;

    return res;
}

// 方法2：
public int[] sortArrayByParity(int[] nums) {
    // 2024/2/27 NO.3
    int[] res = new int[nums.length];
    int idx = 0;
    for (int i = 0; i < nums.length; i++) {
        if (nums[i] % 2 == 0)
            res[idx++] = nums[i];

        if (nums[i] % 2 == 1)
            res[nums.length - i - 1] = nums[i];
    }
    return res;
}
*/