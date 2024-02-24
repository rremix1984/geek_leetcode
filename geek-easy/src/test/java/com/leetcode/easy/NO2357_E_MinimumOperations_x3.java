/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

import java.util.Arrays;

/**
    [ARRAY] |
    (简单)
    2357. 使数组中所有元素都等于零
        给你一个非负整数数组nums。在一步操作中，你必须：
        选出一个正整数x，x需要小于或等于nums中最小的非零元素。
        nums中的每个正整数都减去x。
        返回使 nums 中所有元素都等于 0 需要的 最少 操作数。
    示例 1：
        输入：nums = {1, 5, 0, 3, 5}
        输出：3
        解释：
        第一步操作：选出 x = 1 ，之后 nums = {0, 4, 0, 2, 4} 。
        第二步操作：选出 x = 2 ，之后 nums = {0, 2, 0, 0, 2} 。
        第三步操作：选出 x = 2 ，之后 nums = {0, 0, 0, 0, 0} 。
    示例 2：
        输入：nums = {0}
        输出：0
        解释：nums 中的每个元素都已经是 0 ，所以不需要执行任何操作。
*/
public class NO2357_E_MinimumOperations_x3 {

    @Test
    public void test() {
        assert 3 == minimumOperations(new int[]{1, 5, 0, 3, 5});
        assert 0 == minimumOperations(new int[]{0});
    }

    public int minimumOperations(int[] nums) {
        // 2024/2/24 NO.3
        Arrays.sort(nums);
        int res = 0;
        return res;
    }

}
















/*
// 方法1：
public int minimumOperations(int[] nums) {
    Arrays.sort(nums);
    int res = 0;
    int sub = 0;
    for (int j : nums)
        if (sub < j) {
            res++;
            sub = j;
        }
    return res;
}
*/