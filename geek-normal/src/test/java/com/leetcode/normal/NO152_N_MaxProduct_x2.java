/**
 * copyright 2022/1/19
 */
package com.leetcode.normal;

import org.junit.Test;

import static java.lang.Integer.MIN_VALUE;

/**
    (中等)
    152. 乘积最大子数组
        给你一个整数数组 nums ，请你找出数组中乘积最大的非空连续子数组（该子数组中至少包含一个数字），并返回该子数组所对应的乘积。
        测试用例的答案是一个 32-位 整数。
        子数组 是数组的连续子序列。
    示例 1:
        输入: nums = [2, 3, -2, 4]
        输出: 6
        解释: 子数组 [2, 3] 有最大乘积 6。
    示例 2:
        输入: nums = [-2, 0, -1]
        输出: 0
        解释: 结果不能为 2, 因为 [-2, -1] 不是子数组。
*/
public class NO152_N_MaxProduct_x2 {

    @Test
    public void test() {
        assert 6 == maxProduct(new int[]{2, 3, -2, 4});
        assert 0 == maxProduct(new int[]{-2, 0, -1});
    }

    public int maxProduct(int[] nums) {
        int res = MIN_VALUE;
        return res;
    }

}



















/**
// 方法1：
public int maxProduct(int[] nums) {
    int res = Integer.MIN_VALUE;
    int imax = 1;
    int imin = 1;

    for (int num : nums) {
        // 当num小于0的时候
        // max反而变小了，min就变大了，所以需要互换一下
        if (num < 0) {
            int tmp = imax;
            imax = imin;
            imin = tmp;
        }

        // 每次记录两个值，最小的 和最大的（乘积）
        // 因为是连续子数组，所以是 num 和 num * imax 比较
        imax = max(imax * num, num);
        imin = min(imin * num, num);

        res = max(res, imax);
    }
    return res;
}
*/