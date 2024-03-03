package com.leetcode.easy;

import org.junit.Test;
import static com.leetcode.util.MathUtils.getArrays;
import static org.junit.Assert.assertArrayEquals;

/**
    [ARRAY]
    （简单）
    NO.2574 左右元素和的差值
        给你一个下标从 0 开始的整数数组 nums ，请你找出一个下标从 0 开始的整数数组 answer ，其中：
        answer.length == nums.length
        answer[i] = |leftSum[i] - rightSum[i]|
        其中：
        leftSum[i] 是数组 nums 中下标 i 左侧元素之和。如果不存在对应的元素，leftSum[i] = 0 。
        rightSum[i] 是数组 nums 中下标 i 右侧元素之和。如果不存在对应的元素，rightSum[i] = 0 。
        返回数组 answer 。
    示例 1：
        输入：nums = [10,4,8,3]
        输出：[15,1,11,22]
        解释：数组 leftSum 为 [0,10,14,22] 且数组 rightSum 为 [15,11,3,0] 。
        数组 answer 为 [|0 - 15|,|10 - 11|,|14 - 3|,|22 - 0|] = [15,1,11,22] 。
    示例 2：
        输入：nums = [1]
        输出：[0]
        解释：数组 leftSum 为 [0] 且数组 rightSum 为 [0] 。
            数组 answer 为 [|0 - 0|] = [0] 。
    提示：
        1 <= nums.length <= 1000
        1 <= nums[i] <= 105
*/
public class NO2574_E_LeftRightDifference {

    @Test
    public void test() {
        assertArrayEquals(getArrays(15, 1, 11, 22),
            leftRightDifference(new int[]{10, 4, 8, 3}));
        assertArrayEquals(getArrays(0),
                leftRightDifference(new int[]{1}));
    }

    public int[] leftRightDifference(int[] nums) {
        // 2024/3/1 NO.1
        int[] res = new int[nums.length];
        int leftSum = 0;
        int rightSum = 0;
        for (int num : nums)
            rightSum += num;

        for (int i = 0; i < nums.length; i++) {
            rightSum -= nums[i];
            res[i] = Math.abs(leftSum-rightSum);
            leftSum += nums[i];
        }
        return res;
    }

}


















/*
// 方法1：
public int[] leftRightDifference(int[] nums) {
    int[] res = new int[nums.length];
    int leftSum = 0;
    int rightSum = 0;
    for (int num : nums)
        rightSum += num;

    for (int i = 0; i < nums.length; i++) {
        rightSum -= nums[i];
        res[i] = Math.abs(leftSum-rightSum);
        leftSum += nums[i];
    }
    return res;
}
*/