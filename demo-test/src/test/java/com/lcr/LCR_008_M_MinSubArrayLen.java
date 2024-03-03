package com.lcr;

import org.junit.Test;

import java.util.Arrays;


/**
    [ARRAY]
    （中等）
    LCR.008 长度最小的子数组
        给定一个含有 n 个正整数的数组和一个正整数 target 。
        找出该数组中满足其和 ≥ target 的长度最小的 连续子数组 [numsl, numsl+1, ..., numsr-1, numsr] ，并返回其长度。如果不存在符合条件的子数组，返回 0 。
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
    进阶:
        如果你已经实现O(n)时间复杂度的解法, 请尝试设计一个O(nlog(n))时间复杂度的解法。
 */
public class LCR_008_M_MinSubArrayLen {

    @Test
    public void test() {
        assert 2 == minSubArrayLen(7, new int[]{2, 3, 1, 2, 4, 3});
        assert 1 == minSubArrayLen(4, new int[]{1, 4, 4});
        assert 0 == minSubArrayLen(11, new int[]{1, 1, 1, 1, 1, 1, 1, 1});
    }

//    public int minSubArrayLen(int target, int[] nums) {
//        // 2024/3/1 NO.1
//
//    }

    public int minSubArrayLen(int target, int[] nums) {
        int n = nums.length;
        int[] sums = new int[n + 1];
        for (int i = 1; i <= n; ++i)
            sums[i] = sums[i - 1] + nums[i - 1];

        int ans = n + 1;
        for (int i = 1; i <= n; ++i) {
            int s = target + sums[i - 1];
            int bound = Arrays.binarySearch(sums, s);
            if (bound < 0) {
                bound = -bound - 1;
            }
            if (bound <= n) {
                ans = Math.min(ans, bound - (i - 1));
            }
        }
        return ans == n + 1 ? 0 : ans;
    }

}
