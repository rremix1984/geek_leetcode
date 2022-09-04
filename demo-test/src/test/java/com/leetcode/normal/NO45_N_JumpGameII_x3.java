/**
 * copyright 2022/1/19
 */
package com.leetcode.normal;

import org.junit.Test;
import static com.leetcode.util.LogUtil.info;
import static com.leetcode.util.MathUtils.max;

/**
    （中等）
    45. 跳跃游戏 II
        给你一个非负整数数组 nums ，你最初位于数组的第一个位置。
        数组中的每个元素代表你在该位置可以跳跃的最大长度。
        你的目标是使用最少的跳跃次数到达数组的最后一个位置。
        假设你总是可以到达数组的最后一个位置。（因此不需要考虑 length-1 这一位）
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
public class NO45_N_JumpGameII_x3 {

    @Test
    public void test() {
        info(jump(new int[]{2, 3, 0, 1, 4}));//2
        info(jump(new int[]{2, 3, 1, 1, 4}));//2
        info(jump(new int[]{2, 3, 1, 1, 1, 1, 1, 1, 4}));//6
    }

    public int jump(int[] nums) {
        int cur = 0;
        int max = 0;
        int jump = 0;
        for (int i = 0; i < nums.length; i++) {
            max = max(max, cur + nums[i]);
            if (cur == i) {
                cur = max;
                jump++;
            }
        }
        return jump;
    }

}














/**
// 方法1：
public int jump(int[] nums) {
    int end = 0, max = 0, jump = 0;
    // 因为题目说：总是可以到达数组的最后一个位置。
    // 因此不需要考虑 length - 1 这一位
    for (int i = 0; i < nums.length - 1; i++) {
        // 找到能调到的最远位置
        max = max(max, i + nums[i]);
        // 遇到边界，就更新边界，并且jump + 1，代表需要再跳一步
        if (i == end) {
            end = max;
            jump++;
        }
    }
    return jump;
}

// 方法2：
public int jump(int[] nums) {
    int position = nums.length - 1; //要找的位置
    int steps = 0;
    while (position != 0) { //是否到了第 0 个位置
        for (int i = 0; i < position; i++) {
            if (nums[i] >= position - i) {
                position = i; //更新要找的位置
                steps++;
                break;
            }
        }
    }
    return steps;
}
*/