/**
 * copyright 2022/1/19
 */
package com.leetcode.normal;

import org.junit.Test;
import static java.lang.Math.max;

/**
    [ARRAY] ||||
    （中等）
    (重要,面试）
    55. 跳跃游戏 I
        给定一个非负整数数组 nums ，你最初位于数组的 第一个下标 。
        数组中的每个元素代表你在该位置可以跳跃的最大长度。
        判断你是否能够到达最后一个下标。
    示例 1：
        输入：nums = [2, 3, 1, 1, 4]
        输出：true
        解释：可以先跳1步，从下标0到达下标1,
            然后再从下标1跳3步到达最后一个下标。
    示例 2：
        输入：nums = [3, 2, 1, 0, 4]
        输出：false
        解释：无论怎样，总会到达下标为3的位置。但该下标的最大跳跃
            长度是0， 所以永远不可能到达最后一个下标。
*/
@SuppressWarnings("all")
public class NO055_N_JumpGame {

    @Test
    public void test() {
        assert  canJump(new int[]{2,  3, 1,  1, 4});// true
        assert !canJump(new int[]{3,  2, 1,  0, 4});// false
        assert !canJump(new int[]{1, -1, 1, -1, 1});// false
    }

    public boolean canJump(int[] nums) {
        // 2024/2/24    NO.4
        // 2024/2/25    NO.5
        // 2024/3/4     NO.6
        // 2024/3/14    NO.7 还是不会做，想不出来
        return true;
    }

}















/*
// 方法1
public boolean canJump(int[] nums) {
    if (nums == null || nums.length == 0)
        return false;

    int end_index = nums.length - 1;
    for (int i = nums.length - 1; i >=0 ; i--)
        // 可以跳到数组最后的位置
        if (nums[i] + i >= end_index)
            end_index = i;
    return end_index == 0;
}

// 方法2：
public boolean canJump(int[] nums) {
    int reach = 0;
    for (int i = 0; i < nums.length; i++) {
        if (i > reach)
            return false;
        reach = Math.max(reach, i + nums[i]);
    }
    return true;
}
*/