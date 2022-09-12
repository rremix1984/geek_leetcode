/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;
import static com.leetcode.util.LogUtil.info;
import static java.lang.Math.max;

/**
    628. 三个数的最大乘积
        给你一个整型数组 nums ，在数组中找出由三个数组成的最大乘积，并输出这个乘积。
    示例 1：
        输入：nums = [1,2,3]
        输出：6
    示例 2：
        输入：nums = [1,2,3,4]
        输出：24
    示例 3：
        输入：nums = [-1,-2,-3]
        输出：-6
*/
public class NO628_E_MaximumProductOfThreeNumbers_x2 {

    @Test
    public void test() {
        assert  6 == maximumProduct(new int[]{1, 2, 3});
        assert 24 == maximumProduct(new int[]{1, 2, 3, 4});
        assert -6 == maximumProduct(new int[]{-1, -2, -3});
        assert  0 == maximumProduct(new int[]{-1, -2, 0});

    }

    public int maximumProduct(int[] nums) {
        return -1;
    }
}

















/**
// 方法1：快排
public int maximumProduct(int[] nums) {
    // 快排
    // quickSort(nums,0,nums.length - 1);
    java.util.Arrays.sort(nums);

    // 最后三个连乘，如果都是正数，这三个连乘就是最大的
    // 如果有一个是负数，还是最大的三个正数连乘
    int t1 = nums[nums.length - 1] * nums[nums.length - 2] * nums[nums.length - 3];

    // 前两个连乘，如果有两个以上的负数
    // 最大的正数与最小的两个负数连乘就是最大乘积
    int t2 = nums[nums.length - 1] * nums[0] * nums[1];

    return Math.max(t1, t2);
}
*/