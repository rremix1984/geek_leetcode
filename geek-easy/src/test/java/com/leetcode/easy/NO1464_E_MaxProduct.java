/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;
import java.util.Arrays;
import static java.lang.Math.max;

/**
    [ARRAY] |
    (简单)
    1464. 数组中两元素的最大乘积
        给你一个【正整数数组】nums，请你选择数组的两个不同下标i和j，
        使(nums[i] - 1) * (nums[j] - 1)取得最大值。
        请你计算并返回该式的最大值。
    示例 1：
        输入：nums = {3, 4, 5, 2}
        输出：12
        解释：如果选择下标 i = 1 和 j = 2（下标从 0 开始），则可以获得最大值，
            (nums{1}-1)*(nums{2}-1) = (4-1)*(5-1) = 3*4 = 12 。
    示例 2：
        输入：nums = {1, 5, 4, 5}
        输出：16
        解释：选择下标 i=1 和 j=3（下标从 0 开始），
            则可以获得最大值 (5-1)*(5-1) = 16 。
    示例 3：
        输入：nums = {3, 7}
        输出：12
*/
public class NO1464_E_MaxProduct {

    @Test
    public void test() {
        assert 12 == maxProduct(new int[]{3, 4, 5, 2});
        assert 16 == maxProduct(new int[]{1, 5, 4, 5});
        System.out.println(maxProduct(new int[]{-4, -5, 3, 5}));
        assert 12 == maxProduct(new int[]{3, 7});
    }

    public int maxProduct(int[] nums) {
        // 2024/2/27 NO.3 不要用排序算法

        return -1;
    }

}














/*
// 方法1：
public int maxProduct(int[] nums) {
    Arrays.sort(nums);
    return (nums[nums.length - 1] - 1) * (nums[nums.length - 2] - 1);
}

// 方法2：
public int maxProduct(int[] nums) {
    int a = nums[0];
    int b = nums[1];
    if (a < b) {
        int temp = a;
        a = b;
        b = temp;
    }

    for (int i = 2; i < nums.length; i++)
        if (nums[i] > a) {
            b = a;
            a = nums[i];
        } else if (nums[i] > b)
            b = nums[i];

    return (a - 1) * (b - 1);
}
*/