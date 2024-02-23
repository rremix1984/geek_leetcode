/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;

/**
    [ARRAY]
    (简单)
    1480. 一维数组的动态和
        给你一个数组 nums 。数组「动态和」的计算公式为：runningSum[i] = sum(nums[0]…nums[i]) 。
        请返回 nums 的动态和。
    示例 1：
        输入：nums = {1, 2, 3, 4}
        输出：{1, 3, 6, 10}
        解释：动态和计算过程为 {1,  1+2,  1+2+3,  1+2+3+4} 。
    示例 2：
        输入：nums = {1, 1, 1, 1, 1}
        输出：{1, 2, 3, 4, 5}
        解释：动态和计算过程为 {1,  1+1,  1+1+1,  1+1+1+1,  1+1+1+1+1} 。
    示例 3：
        输入：nums = {3, 1, 2, 10, 1}
        输出：{3, 4, 6, 16, 17}
*/
public class NO1480_E_RunningSum_x2 {

    @Test
    public void test() {
        assertArrayEquals(new int[]{1, 3, 6, 10}, runningSum(new int[]{1, 2, 3, 4}));
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, runningSum(new int[]{1, 1, 1, 1, 1}));
        assertArrayEquals(new int[]{3, 4, 6, 16, 17}, runningSum(new int[]{3, 1, 2, 10, 1}));
    }

    public int[] runningSum(int[] nums) {
        return nums;
    }

}












/**
public int[] runningSum(int[] nums) {
    for (int i = 1; i < nums.length; i++)
        nums[i] += nums[i - 1];

    return nums;
}
*/