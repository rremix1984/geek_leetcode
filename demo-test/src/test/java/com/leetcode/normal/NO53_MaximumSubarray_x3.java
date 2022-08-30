/**
 * copyright 2022/1/19
 */
package com.leetcode.normal;

import org.junit.Test;
import static com.leetcode.util.LogUtil.info;
import static java.lang.Integer.MIN_VALUE;

/**
    （中等）
    53. 最大子数组和
        给你一个整数数组 nums ，请你找出一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
        子数组 是数组中的一个连续部分。
    示例 1：
        输入：nums = [-2, 1, -3, 4, -1, 2, 1, -5, 4]
        输出：6
        解释：连续子数组 [4, -1, 2, 1] 的和最大，为 6 。
    示例 2：
        输入：nums = [1]
        输出：1
    示例 3：
        输入：nums = [5, 4, -1, 7, 8]
        输出：23
*/
public class NO53_MaximumSubarray_x3 {

    @Test
    public void test() {
        info(maxSubArray(new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4}));// 6
        info(maxSubArray(new int[]{-1}));// -1
    }

    public int maxSubArray(int[] nums) {
        int pre = 0, max = MIN_VALUE;
        for (int n : nums) {
            pre = Math.max(n, n + pre);
            if (max < pre)
                max = pre;
        }
        return max;
    }

}













/*
// 方法1：dp动态规划
public int maxSubArray(int[] nums) {
    int cur = nums[0];
    int max = nums[0];
    for (int i = 1; i < nums.length; i++) {
        cur = max(nums[i], nums[i] + cur);
        max = max(cur, max);
    }
    return max;
}
*/

