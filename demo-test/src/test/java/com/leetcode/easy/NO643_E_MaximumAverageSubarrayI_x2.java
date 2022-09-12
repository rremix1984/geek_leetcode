/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;
import static java.lang.Math.max;

/**
    (简单)
    643. 子数组最大平均数 I
        给你一个由 n 个元素组成的整数数组 nums 和一个整数 k 。
        请你找出平均数最大且 长度为 k 的连续子数组，并输出该最大平均数。
        任何误差小于 10-5 的答案都将被视为正确答案。
    示例 1：
        输入：nums = {1, 12, -5, -6, 50, 3}, k = 4
        输出：12.75
        解释：最大平均数 (12-5-6+50)/4 = 51/4 = 12.75
    示例 2：
        输入：nums = {5}, k = 1
        输出：5.00000
*/
public class NO643_E_MaximumAverageSubarrayI_x2 {

    @Test
    public void test() {
        assert 12.75d == findMaxAverage(new int[]{1, 12, -5, -6, 50, 3}, 4);
        assert 5.0d == findMaxAverage(new int[]{5}, 1);
    }


    public double findMaxAverage(int[] nums, int k) {
        int sum = 0;

        // 窗口大小
        for (int i = 0; i < k; i++) {
            sum += nums[i];
        }

        int maxSum = sum;
        for (int i = k; i < nums.length; i++) {
            // 滑动一次
            sum -= nums[i - k] - nums[i];
            maxSum = max(maxSum, sum);
        }

        return (double) maxSum / k;
    }

}



















/**
// 方法1：滑动窗口法
public double findMaxAverage(int[] nums, int k) {
    int sum = 0;

    // 初始化第一次滑动窗口
    for (int i = 0; i < k; i++)
        sum += nums[i];

    // 初始化窗口和 (maxSum)
    int maxSum = sum;

    for (int i = k; i < nums.length; i++) {
        // 把第一个去掉，增加最后一个，相当于【滑动】了一次
        sum = sum - nums[i - k] + nums[i];
        maxSum = max(maxSum, sum);
    }

    // 窗口和 除以 K 就是结果【平均值】
    return (double) maxSum / k;
}
*/