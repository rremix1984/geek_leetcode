/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;
import static com.leetcode.util.LogUtil.info;
import static java.lang.Integer.MAX_VALUE;
import static java.lang.Integer.MIN_VALUE;
import static java.lang.Math.max;

/**
    [ARRAY]
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

// 最优解
// 方法2：不用排序算法，找到最大的3个值，和最小的2个值
public int maximumProduct(int[] nums) {
    int min1 = MAX_VALUE, min2 = MAX_VALUE;
    int max1 = MIN_VALUE, max2 = MIN_VALUE, max3 = MIN_VALUE;
    for (int x : nums) {
        // 找到最小的2个值
        if (x < min1) {// 如果比最小的还要小
            min2 = min1;// 原来最小的变成第二小的
            min1 = x;
        } else if (x < min2) {
            min2 = x; // 第二小的就是 x
        }

        // 找到最大的3个值
        if (x > max1) {
            max3 = max2;
            max2 = max1;
            max1 = x;
        } else if (x > max2) {
            max3 = max2;
            max2 = x;
        } else if (x > max3) {
            max3 = x;
        }
    }
    return max(max3*max2*max1, min1*min2*max1);
}
*/