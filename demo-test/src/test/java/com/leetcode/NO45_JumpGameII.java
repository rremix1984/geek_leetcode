/**
 * copyright 2022/1/19
 */
package com.leetcode;

import org.junit.Test;
import static com.leetcode.util.LogUtil.info;
import static com.leetcode.util.MathUtils.max;

/**
    （中等）
    45. 跳跃游戏 II
        给你一个非负整数数组 nums ，你最初位于数组的第一个位置。
        数组中的每个元素代表你在该位置可以跳跃的最大长度。
        你的目标是使用最少的跳跃次数到达数组的最后一个位置。
        假设你总是可以到达数组的最后一个位置。
    示例 1:
        输入: nums = [2,3,1,1,4]
        输出: 2
        解释: 跳到最后一个位置的最小跳跃数是 2。
        从下标为 0 跳到下标为 1 的位置，跳 1 步，然后跳 3 步到达数组的最后一个位置。
    示例 2:
        输入: nums = [2, 3, 0, 1, 4]
        输出: 2
*/
@SuppressWarnings("all")
public class NO45_JumpGameII {

    @Test
    public void test() {
        // 2
        info(jump(new int[]{2, 3, 0, 1, 4}));
    }

    public int jump(int[] nums) {
        int length = nums.length;
        int end = 0, pos = 0, res = 0;
        for (int i = 0; i < length - 1; i++) {
            pos = max(pos, i + nums[i]);
            if (i == end) {
                end = pos;
                res++;
            }
        }
        return res;
    }

}
