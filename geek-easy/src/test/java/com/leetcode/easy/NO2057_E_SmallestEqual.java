/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

/**
    [ARRAY]
    (简单)
    2057. 值相等的最小索引
        给你一个下标从0开始的整数数组nums，
        返回nums中满足i % 10 == nums[i]的最小下标i；
        如果不存在这样的下标，返回-1。
        x % y 表示 x 除以 y 的 余数 。
    示例 1：
        输入：nums = [0, 1, 2]
        输出：0
        解释：i=0: 0 mod 10 = 0 == nums[0].
             i=1: 1 mod 10 = 1 == nums[1].
             i=2: 2 mod 10 = 2 == nums[2].
             所有下标都满足 i mod 10 == nums[i] ，所以返回最小下标 0
    示例 2：
        输入：nums = [4, 3, 2, 1]
        输出：2
        解释：i=0: 0 mod 10 = 0 != nums[0].
             i=1: 1 mod 10 = 1 != nums[1].
             i=2: 2 mod 10 = 2 == nums[2].
             i=3: 3 mod 10 = 3 != nums[3].
             2 唯一一个满足 i mod 10 == nums[i] 的下标
    示例 3：
        输入：nums = [1, 2, 3, 4, 5, 6, 7, 8, 9, 0]
        输出：-1
        解释：不存在满足 i mod 10 == nums[i] 的下标
    示例 4：
        输入：nums = {2, 1, 3, 5, 2}
        输出：1
        解释：1 是唯一一个满足 i mod 10 == nums[i] 的下标
*/
public class NO2057_E_SmallestEqual {

    @Test
    public void test() {
        assert  0 == smallestEqual(new int[]{0, 1, 2});
        assert  2 == smallestEqual(new int[]{4, 3, 2, 1});
        assert -1 == smallestEqual(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 0});
        assert  1 == smallestEqual(new int[]{2, 1, 3, 5, 2});
    }

    public int smallestEqual(int[] nums) {
        return -1;
    }

}























/*
// 方法1：
public int smallestEqual(int[] nums) {
    for (int i = 0; i < nums.length; i++)
        if (i % 10 == nums[i])
            return i;

    return -1;
}
*/