/**
 * copyright 2022/1/19
 */
package com.offer.normal;

import org.junit.Test;

/**
    (中等)
    剑指 Offer II 010. 和为 k 的子数组
        给定一个整数数组和一个整数 k ，请找到该数组中和为 k 的【连续】子数组的个数。
    示例 1：
        输入:nums = {1, 1, 1}, k = 2
        输出: 2
        解释: 此题 [1, 1] 与 [1, 1] 为两种不同的情况
    示例 2：
        输入:nums = {1, 2, 3}, k = 3
        输出: 2
*/
public class OfferII_010_N_SubarraySum_x2 {

    @Test
    public void test() {
        assert 2 == subarraySum(new int[]{1, 1, 1}, 2);
        assert 2 == subarraySum(new int[]{1, 2, 3}, 3);
    }

    public int subarraySum(int[] nums, int k) {
        int res = 0;
        return res;
    }

}

















/**
// 方法1：
public int subarraySum(int[] nums, int k) {
    int res = 0;
    for (int i = 0; i < nums.length; i++) {

        int sum = 0;
        for (int j = i; j >= 0; j--) {

            sum += nums[j];
            if (sum == k)
                res++;
        }
    }
    return res;
}
*/