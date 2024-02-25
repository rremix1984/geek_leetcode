/**
 * copyright 2022/1/19
 */
package com.leetcode.normal;

import org.junit.Test;
import static java.lang.Math.max;

/**
    [ARRAY] |
    （中等）
    45. 跳跃游戏 II
        给你一个非负整数数组nums，你最初位于数组的第一个位置。
        数组中的每个元素代表你在该位置可以跳跃的最大长度。
        你的目标是使用【最少的跳跃次数】到达数组的最后一个位置。
        假设你总是可以到达数组的最后一个位置。
        （因此不需要考虑 length - 1 这一位）
    示例 1:
        输入: nums = [2, 3, 1, 1, 4]
        输出: 2
        解释: 跳到最后一个位置的最小跳跃数是 2。
        从下标为 0 跳到下标为 1 的位置，跳 1 步，然后跳 3 步到达数组的最后一个位置。
    示例 2:
        输入: nums = [2, 3, 0, 1, 4]
        输出: 2
*/
@SuppressWarnings("all")
public class NO045_N_JumpGameII {

    @Test
    public void test() {
        assert 2 == jump(new int[]{2, 3, 0, 1, 4});// 2
        assert 2 == jump(new int[]{2, 3, 1, 1, 4});// 2
        assert 6 == jump(new int[]{2, 3, 1, 1, 1, 1, 1, 1, 4});// 6
    }

    public int jump(int[] nums) {
        // 2024/2/25 NO.3
        int jump = 0;
        return jump;
    }

}














/*
// 方法1：
public int jump(int[] nums) {
    int jump = 0;// 跳的次数
    int max = 0;   // 当前能跳到最远的位置
    int reach = 0; // 能到达（reach）的边界
    // 因为题目说：总是可以到达数组的最后一个位置。
    // 因此不需要考虑 length - 1 这一位
    for (int i = 0; i < nums.length - 1; i++) {
        // 找到能跳到的最远位置
        max = max(max, i + nums[i]);
        // 遇到边界，就更新边界 reach = max
        // 而且需要再跳一步 jump++
        if (i == reach) {
            reach = max;
            jump++;
        }
    }
    return jump;
}

// 方法2：
public int jump(int[] nums) {
    int position = nums.length - 1; //要找的位置
    int steps = 0;
    while (position != 0) //是否到了第 0 个位置
        for (int i = 0; i < position; i++)
            if (nums[i] >= position - i) {
                position = i; //更新要找的位置
                steps++;
                break;
            }

    return steps;
}
*/