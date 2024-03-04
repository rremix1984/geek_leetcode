/**
 * copyright 2022/1/19
 */
package com.offer.easy;

import org.junit.Test;
import static java.lang.Integer.MIN_VALUE;
import static java.lang.Math.max;

/**
    [ARRAY]
    (简单)
    (重要,面试题,美团)
    剑指 Offer 42. 连续子数组的最大和
        输入一个整型数组，数组中的一个或连续多个整数组成一个子数组。
        求所有子数组的和的最大值。
        要求时间复杂度为O(n)。
    例如：
    [1, 2, -1, -3, 5, 6, 7, -18, 1, 0]
    |___|
      |
      3

    示例1:
        输入: nums = [-2, 1, -3, 4, -1, 2, 1, -5, 4]
        输出: 6
        解释: 连续子数组 [4, -1, 2, 1] 的和最大，为 6。
    Related Topics:数组,
*/
public class Offer_042_E_MaxSubArray {

    @Test
    public void test() {
        assert 6 == maxSubArray(new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4});
        assert -1 == maxSubArray(new int[]{-1});
    }

    public int maxSubArray(int[] nums) {
        // 2024/3/4 NO.1 加了比不加还小，就断开了
        int res = nums[0];
        return res;
    }

}
















/*
// 方法1：
public int maxSubArray(int[] nums) {
    int max = nums[0];
    int tmp = 0;
    for (int num : nums) {
        // 如果加了TA这个元素，比不加TA还小，那就说明前面的元素和已经是最大的了
        // 即：到这里就断开了
        tmp = Math.max(num, num + tmp);
        // 把找到的每个段落，做一个比较留下最大的 max
        max = Math.max(max, tmp);
    }
    return max;
}
*/