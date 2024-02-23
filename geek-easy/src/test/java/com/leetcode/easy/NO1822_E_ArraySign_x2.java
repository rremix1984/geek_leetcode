/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

/**
    [ARRAY]
    (简单)
    1822. 数组元素积的符号
        已知函数 signFunc(x) 将会根据 x 的正负返回特定值：
            如果 x 是正数，返回 1 。
            如果 x 是负数，返回 -1 。
            如果 x 是等于 0 ，返回 0 。
        给你一个整数数组 nums 。令 product 为数组 nums 中所有元素值的乘积。
        返回 signFunc(product) 。
    示例 1：
        输入：nums = {-1,-2,-3,-4,3,2,1}
        输出：1
        解释：数组中所有值的乘积是 144 ，且 signFunc(144) = 1
    示例 2：
        输入：nums = {1,5,0,2,-3}
        输出：0
        解释：数组中所有值的乘积是 0 ，且 signFunc(0) = 0
    示例 3：
        输入：nums = {-1,1,-1,1,-1}
        输出：-1
        解释：数组中所有值的乘积是 -1 ，且 signFunc(-1) = -1
*/
public class NO1822_E_ArraySign_x2 {

    @Test
    public void test() {
        assert 1 == arraySign(new int[]{-1, -2, -3, -4, 3, 2, 1});
        assert 0 == arraySign(new int[]{1, 5, 0, 2, -3});
        assert -1 == arraySign(new int[]{-1, 1, -1, 1, -1});
    }

    public int arraySign(int[] nums) {
        int sign = 1;
        return sign;
    }

}
















/**
// 方法1：
public int arraySign(int[] nums) {
    int count = 0;
    for (int num : nums) {
        // 如果是偶数个 -1 就是正数
        if (num < 0)
            count++;

        // 如果有一个是0，直接返回0
        if (num == 0)
            return 0;
    }
    if (count % 2 == 0)
        return 1;

    return -1;
}

// 方法2：
public int arraySign(int[] nums) {
    int sign = 1;
    for (int num : nums) {
        if (num == 0) {
            return 0;
        }
        if (num < 0) {
            sign = -sign;
        }
    }
    return sign;
}
*/