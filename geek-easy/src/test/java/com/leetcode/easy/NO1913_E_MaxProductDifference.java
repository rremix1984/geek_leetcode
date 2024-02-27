/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;
import static java.lang.Integer.*;

/**
    [ARRAY] |
    (简单)
    1913. 两个数对之间的最大乘积差
        两个数对(a, b)和(c, d)之间的乘积差定义为(a * b)-(c * d)。
        例如：(5, 6)和(2, 7)之间的乘积差是(5 * 6)-(2 * 7)=16。
        给你一个整数数组 nums ，选出四个不同的下标w、x、y和z，
        使数对(nums[w], nums[x])和(nums[y], nums[z])之间的乘积差取到最大值。
        返回以这种方式取得的乘积差中的最大值。
    示例 1：
        输入：nums = {5, 6, 2, 7, 4}
        输出：34
        解释：可以选出下标为 1 和 3 的元素构成第一个数对 (6,  7) 以及下标 2 和 4 构成第二个数对 (2,  4)
             乘积差是 (6 * 7) - (2 * 4) = 34
    示例 2：
        输入：nums = {4, 2, 5, 9, 7, 4, 8}
        输出：64
        解释：可以选出下标为 3 和 6 的元素构成第一个数对 (9, 8) 以及下标 1 和 5 构成第二个数对 (2, 4)
             乘积差是 (9 * 8) - (2 * 4) = 64
*/
public class NO1913_E_MaxProductDifference {

    @Test
    public void test() {
        assert 34 == maxProductDifference(new int[]{5, 6, 2, 7, 4});
        assert 64 == maxProductDifference(new int[]{4, 2, 5, 9, 7, 4, 8});
    }

    public int maxProductDifference(int[] nums) {
        // 2024/2/27 NO.3 辣两头儿
        return 0;
    }

}













/*
// 方法1：
public int maxProductDifference(int[] nums) {
    Arrays.sort(nums);
    return nums[nums.length - 1] * nums[nums.length - 2] - nums[1] * nums[0];
}

// 方法2：推荐
public int maxProductDifference(int[] nums) {
    int max1 = Integer.MIN_VALUE, max2 = Integer.MIN_VALUE;
    int min1 = Integer.MAX_VALUE, min2 = Integer.MAX_VALUE;

    for (int x : nums) {
        if (x > max1) {
            max2 = max1;
            max1 = x;
        } else if (x > max2) {
            max2 = x;
        }

        if (x < min1) {
            min2 = min1;
            min1 = x;
        } else if (x < min2) {
            min2 = x;
        }
    }
    return (max1 * max2) - (min1 * min2);
}
*/