/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

import static java.lang.Math.min;

/**
    [ARRAY] |
    (简单)
    1979. 找出数组的最大公约数
        给你一个整数数组nums，返回数组中【最大数】和【最小数】的
        【最大公约数】。两个数的最大公约数是能够被两个数整除的最大正整数。
    示例 1：
        输入：nums = {2, 5, 6, 9, 10}
        输出：2
        解释：nums 中最小的数是 2
             nums 中最大的数是 10
             2 和 10 的最大公约数是 2
    示例 2：
        输入：nums = {7, 5, 6, 8, 3}
        输出：1
        解释：nums 中最小的数是 3
             nums 中最大的数是 8
             3 和 8 的最大公约数是 1
    示例 3：
        输入：nums = {3, 3}
        输出：3
        解释：nums 中最小的数是 3
             nums 中最大的数是 3
             3 和 3 的最大公约数是 3
*/
public class NO1979_E_FindGCD {

    @Test
    public void test() {
        assert 2 == findGCD(new int[]{2, 5, 6, 9, 10});
        assert 1 == findGCD(new int[]{7, 5, 6, 8, 3});
        assert 3 == findGCD(new int[]{3, 3});
    }

    public int findGCD(int[] nums) {
        // 2024/2/27 NO.3 辗转相除法
        return -1;
    }

}















/*
// 方法1：
public int findGCD(int[] nums) {
    Arrays.sort(nums);
    int max = nums[nums.length - 1];
    int min = nums[0];
    for (int i = max; i >= 1; i--)
        if (max % i == 0 && min % i == 0)
            return i;

    return 0;
}

// 方法2：用辗转相除法找出数组的最大公约数
private static int gcd(int a, int b) {
    if (b == 0) return a;
    return gcd(b, a % b);
}

public static int findGCD(int[] nums) {
    int min = nums[0], max = nums[0];
    // 遍历数组，找到最大值和最小值
    for (int num : nums) {
        if (num > max) max = num;
        if (num < min) min = num;
    }
    // 计算并返回最大值和最小值的最大公约数
    return gcd(max, min);
}
*/