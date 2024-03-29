/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;

/**
    [ARRAY] ||
    (简单)
    (用简单的做法 O(n^2) 就显得没必要了)
    NO.1365. 有多少小于当前数字的数字
        给你一个数组 nums，对于其中每个元素 nums[i]，请你统计数组中
    比它小的所有数字的数目。换而言之，对于每个 nums[i] 你必须计算出有
    效的j的数量，其中 j 满足 j != i且nums[j] < nums[i]。以数组形式
    返回答案。
    示例 1：
        输入：nums = {8, 1, 2, 2, 3}
        输出：{4, 0, 1, 1, 3}
        解释：对于 nums{0} = 8 存在（4）个比它小的数字：（1，2，2 和 3）。
             对于 nums{1} = 1 存在（0）个比它小的数字。
             对于 nums{2} = 2 存在（1）个比它小的数字：（1）。
             对于 nums{3} = 2 存在（1）个比它小的数字：（1）。
             对于 nums{4} = 3 存在（3）个比它小的数字：（1，2 和 2）。
    示例 2：
        输入：nums = {6, 5, 4, 8}
        输出：{2, 1, 0, 3}
    示例 3：
        输入：nums = {7, 7, 7, 7}
        输出：{0, 0, 0, 0}
*/
public class NO1365_E_SmallerNumbersThanCurrent {

    @Test
    public void test() {
        assertArrayEquals(new int[]{4, 0, 1, 1, 3},
                smallerNumbersThanCurrent(new int[]{8, 1, 2, 2, 3}));
        assertArrayEquals(new int[]{2, 1, 0, 3},
                smallerNumbersThanCurrent(new int[]{6, 5, 4, 8}));
        assertArrayEquals(new int[]{0, 0, 0, 0},
                smallerNumbersThanCurrent(new int[]{7, 7, 7, 7}));
    }

    public int[] smallerNumbersThanCurrent(int[] nums) {
        // 2024/2/25 NO.3
        // 2024/3/27 NO.4 简单的做法，又有点太简单了
        int[] ret = new int[nums.length];

        return ret;
    }

}

















/*
// 方法1：时间复杂度高
public int[] smallerNumbersThanCurrent(int[] nums) {
    int[] ret = new int[nums.length];
    for (int i = 0; i < nums.length; i++)
        for (int num : nums)
            if (num < nums[i])
                ret[i]++;
    return ret;
}

// 方法2：
public int[] smallerNumbersThanCurrent(int[] nums) {
    int[] keg = new int[101];
    for (int i : nums) keg[i]++;
    int pre = 0,next = 0;
    for (int i = 0; i < 101; i++) {
        int val = keg[i];
        if (keg[i] > 0) keg[i] = pre;
        pre += val;
    }
    for (int i = 0; i < nums.length; i++) {
        nums[i] = keg[nums[i]];
    }
    return nums;
}
*/