/**
 * copyright 2022/1/19
 */
package com.leetcode;

import org.junit.Test;
import static com.leetcode.util.LogUtil.info;

/**
    55. 跳跃游戏
        给定一个非负整数数组 nums ，你最初位于数组的 第一个下标 。
        数组中的每个元素代表你在该位置可以跳跃的最大长度。
        判断你是否能够到达最后一个下标。
    示例 1：
        输入：nums = [2,3,1,1,4]
        输出：true
        解释：可以先跳 1 步，从下标 0 到达下标 1, 然后再从下标 1 跳 3 步到达最后一个下标。
    示例 2：
        输入：nums = [3,2,1,0,4]
        输出：false
        解释：无论怎样，总会到达下标为 3 的位置。但该下标的最大跳跃长度是 0 ， 所以永远不可能到达最后一个下标。
*/
@SuppressWarnings("all")
public class NO55_JumpGame_x2 {

    @Test
    public void test() {
        info(canJump(new int[]{2, 3, 1, 1, 4}));
    }

    public boolean canJump(int[] nums) {
        if (nums == null)
            return false;
        int end_idx = nums.length - 1;
        for (int i = nums.length - 1; i >= 0; i--) {
            if (nums[i] + i >= end_idx)
                end_idx = i;
        }
        return end_idx == 0;
    }

}















/**
// 方法1
public boolean canJump(int[] nums) {
    if (nums == null || nums.length == 0)
        return false;
    int end_index = nums.length - 1;
    for (int i = nums.length - 1; i >=0 ; i--) {
        if (nums[i] + i >= end_index)
            end_index = i;
    }
    return end_index == 0;
}
*/