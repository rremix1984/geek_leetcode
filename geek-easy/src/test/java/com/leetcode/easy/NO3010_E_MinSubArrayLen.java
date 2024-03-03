package com.leetcode.easy;

import org.junit.Test;

import static java.lang.Integer.MAX_VALUE;

/**
    [ARRAY]
    （简单）
    NO3010. 长度最小子数组
        给定一个含有【n个正整数的数组】和一个【正整数】target。
        找出该数组中满足其和≥target的长度最小的连续子数组
        [numsl, numsl+1, ..., numsr-1, numsr] ，并返回其长度。
        如果不存在符合条件的子数组，返回 0 。
    示例 1：
        输入：target = 7, nums = [2, 3, 1, 2, 4, 3]
        输出：2
        解释：子数组 [4,3] 是该条件下的长度最小的子数组。
    示例 2：
        输入：target = 4, nums = [1, 4, 4]
        输出：1
    示例 3：
        输入：target = 11, nums = [1, 1, 1, 1, 1, 1, 1, 1]
        输出：0
        提示：
            1 <= target <= 109
            1 <= nums.length <= 105
            1 <= nums[i] <= 105
        进阶：
            如果你已经实现 O(n) 时间复杂度的解法, 请尝试设计一个O(n log(n))
            时间复杂度的解法。
*/
public class NO3010_E_MinSubArrayLen {

    @Test
    public void test() {
        assert 2 == minSubArrayLen( 7, new int[]{2, 3, 1, 2, 4, 3});
        assert 0 == minSubArrayLen(11, new int[]{1, 1, 1, 1, 1, 1, 1, 1});
        assert 1 == minSubArrayLen( 4, new int[]{1, 4, 4});
    }

    public int minSubArrayLen(int target, int[] nums) {
        // 2024/2/29 NO.1 练习单调队列
        int start = 0; // 用于标识滑动窗口的开始位置
        int end = -1; // 用于标识滑动窗口的结束位置，初始为-1，表示窗口为空
        int sum = 0; // 窗口中元素的总和
        int ans = Integer.MAX_VALUE; // 初始化最短长度为最大值

        for (int num : nums) {
            end++; // 扩大窗口的右边界
            sum += num; // 更新窗口内数字的和
            // 当窗口内的和大于等于目标值时，尝试缩小窗口以找到更小的满足条件的窗口
            while (start <= end && sum >= target) {
                ans = Math.min(ans, end - start + 1); // 更新最短子数组的长度
                sum -= nums[start++]; // 缩小窗口的左边界
            }
        }
        // 如果ans没有被更新过，说明没有找到符合条件的子数组，返回0；否则，返回ans
        return ans == Integer.MAX_VALUE ? 0 : ans;
    }

}
