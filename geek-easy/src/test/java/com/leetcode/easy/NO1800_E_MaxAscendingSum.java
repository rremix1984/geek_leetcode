/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;
import static java.lang.Math.max;

/**
    [ARRAY] ||
    (简单)
    1800. 最大升序子数组和
        给你一个正整数组成的数组nums，返回nums中一个【升序】子数组的最大可能元素和。
        子数组是数组中的一个【连续】数字序列。
        已知子数组 [numsl, numsl + 1, ..., numsr - 1, numsr]，若对所有 i（l <= i < r），
        numsi<numsi+1都成立，则称这一子数组为【升序子数组】。
        注意，大小为1的子数组也视作升序子数组。
    示例 1：
        输入：nums = {10, 20, 30, 5, 10, 50}
        输出：65
        解释：{5, 10, 50} 是元素和最大的升序子数组，最大元素和为 65 。
    示例 2：
        输入：nums = {10, 20, 30, 40, 50}
        输出：150
        解释：{10, 20, 30, 40, 50} 是元素和最大的升序子数组，最大元素和为 150 。
    示例 3：
        输入：nums = {12, 17, 15, 13, 10, 11, 12}
        输出：33
        解释：{10, 11, 12} 是元素和最大的升序子数组，最大元素和为 33 。
    示例 4：
        输入：nums = {100, 10, 1}
        输出：100
*/
public class NO1800_E_MaxAscendingSum {

    @Test
    public void test() {
        assert 65 == maxAscendingSum(
            new int[]{10, 20, 30, 5, 10, 50});
        assert 150 == maxAscendingSum(
            new int[]{10, 20, 30, 40, 50});
        assert 33 == maxAscendingSum(
            new int[]{12, 17, 15, 13, 10, 11, 12});
        assert 100 == maxAscendingSum(
            new int[]{100, 10, 1});
    }

    public int maxAscendingSum(int[] nums) {
        // 2024/3/8 NO.1 不会做
        // 2024/3/10 NO.2 看懂了
        int max = 0;
        return max;
    }

}













/*
// 方法1：
public int maxAscendingSum(int[] nums) {
    int max = 0;
    int idx = 0;
    while (idx < nums.length) {
        int sum = nums[idx++];
        while (idx < nums.length && nums[idx] > nums[idx - 1])
            sum += nums[idx++];

        max = max(max, sum);
    }
    return max;
}
*/