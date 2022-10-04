/**
 * copyright 2022/1/19
 */
package com.interval.easy;

import org.junit.Test;

/**
    (简单)
    面试题 16.17. 连续数列
        给定一个整数数组，找出总和最大的连续数列，并返回总和。
    示例：
        输入： [-2,1,-3,4,-1,2,1,-5,4]
        输出： 6
        解释： 连续子数组 [4,-1,2,1] 的和最大，为 6。
        进阶：
            如果你已经实现复杂度为 O(n) 的解法，尝试使用更为精妙的分治法求解。
*/
public class Interval_16_17_E_MaxSubArray_x2 {

    @Test
    public void test() {
        assert 6 == maxSubArray(new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4});
        assert -1 == maxSubArray(new int[]{-1});
    }

    public int maxSubArray(int[] nums) {
        int res = nums[0];
        return res;
    }

}
















/**
public int maxSubArray(int[] nums) {
    int res = nums[0];
    int tmp = 0;
    for (int i : nums) {
        tmp = max(i, tmp + i);
        res = max(res, tmp);
    }
    return res;
}
*/