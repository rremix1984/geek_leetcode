/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;

/**
    [ARRAY] |
    (简单)
    2341. 数组能形成多少数对
        给你一个数组nums。在一步操作中，你可以执行以下步骤：
         1. 从nums选出两个相等的整数,
         2. 从nums中移除这两个整数，形成一个数对
        请你在nums上多次执行此操作直到无法继续执行。
        返回一个下标从 0 开始、长度为 2 的整数数组answer作为答案，
        其中answer[0]是形成的数对数目，answer[1]是对nums尽可能
        执行上述操作后【剩下】的【整数数目】。
    示例 1：
        输入：nums = {1, 3, 2, 1, 3, 2, 2}
        输出：{3, 1}
        解释：nums[0] 和 nums[3] 形成一个数对，并从 nums 中移除，nums = [3,2,3,2,2] 。
             nums[0] 和 nums[2] 形成一个数对，并从 nums 中移除，nums = [2,2,2] 。
             nums[0] 和 nums[1] 形成一个数对，并从 nums 中移除，nums = [2] 。
             无法形成更多数对。总共形成3个数对，nums中剩下1个数字。
    示例 2：
        输入：nums = {1, 1}
        输出：{1, 0}
        解释：nums[0] 和 nums[1] 形成一个数对，并从 nums 中移除，nums = [] 。
             无法形成更多数对。总共形成 1 个数对，nums 中剩下 0 个数字。
    示例 3：
        输入：nums = {0}
        输出：{0,1}
        解释：无法形成数对，nums 中剩下 1 个数字。
    提示：
        1 <= nums.length <= 100
        0 <= nums[i] <= 100
*/
public class NO2341_E_NumberOfPairs_x3 {

    @Test
    public void test() {
        assertArrayEquals(new int[]{3, 1},
                numberOfPairs(new int[]{1, 3, 2, 1, 3, 2, 2}));
        assertArrayEquals(new int[]{1, 0},
                numberOfPairs(new int[]{1, 1}));
        assertArrayEquals(new int[]{0, 1},
                numberOfPairs(new int[]{0}));
    }

    public int[] numberOfPairs(int[] nums) {
        // 2024/2/24 NO.3
        int pair = 0;
        int rest = 0;
        return new int[]{pair, rest};
    }

}
















/*
// 方法1
public int[] numberOfPairs(int[] nums) {
    int pair = 0;
    int rest = 0;

    int[] dict = new int[101];
    for (int num : nums)
        dict[num]++;

    for (int num : dict) {
        pair += num / 2;
        rest += num % 2;
    }

    return new int[]{pair, rest};
}
*/