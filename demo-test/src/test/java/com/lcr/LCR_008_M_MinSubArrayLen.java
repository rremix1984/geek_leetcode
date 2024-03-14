package com.lcr;

import org.junit.Test;

import java.util.Arrays;

import static java.lang.Integer.MAX_VALUE;
import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.util.Arrays.binarySearch;


/**
    [ARRAY] ||
    （中等）
    LCR.008 长度最小的子数组
        给定一个含有 n 个正整数的数组和一个正整数 target 。
        找出该数组中满足其和 ≥ target 的长度最小的 连续子数组
        [numsl, numsl+1, ..., numsr-1, numsr]，并返回其长度。
        如果不存在符合条件的子数组，返回 0 。
    示例 1：
        输入：target = 7, nums = [2, 3, 1, 2, 4, 3]
        输出：2
        解释：子数组 [4, 3] 是该条件下的长度最小的子数组。
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

    方法一：前缀和 + 二分查找
    为了使用二分查找，需要额外创建一个数组 sums 用于存储数组 nums 的前缀和，
    其中 sums[i] 表示从 nums[0] 到 nums[i−1] 的元素和。得到前缀和之后，对于每个开始下标 i，
    可通过二分查找得到大于或等于 i 的最小下标 bound，使得 sums[bound] − sums[i − 1] ≥ s，
    并更新子数组的最小长度（此时子数组的长度是 bound − (i − 1)）。

    因为这道题保证了数组中每个元素都为正，所以前缀和一定是递增的，这一点保证了二分的正确性。
    如果题目没有说明数组中每个元素都为正，这里就不能使用二分来查找这个位置了。
 */
public class LCR_008_M_MinSubArrayLen {

    @Test
    public void test() {
        assert 2 == minSubArrayLen(7,
                new int[]{2, 3, 1, 2, 4, 3});
        assert 1 == minSubArrayLen(4,
                new int[]{1, 4, 4});
        assert 0 == minSubArrayLen(11,
                new int[]{1, 1, 1, 1, 1, 1, 1, 1});
    }

    public int minSubArrayLen(int target, int[] nums) {
        // 2024/3/1 NO.1
        // 2024/3/13 NO.2
        int ans = MAX_VALUE;
        return ans == MAX_VALUE ? 0 : ans;
    }

}

















/*
// 方法1：
//
public int minSubArrayLen(int target, int[] nums) {
    int n = nums.length;
    int[] sums = new int[n + 1];
    for (int i = 1; i <= n; i++)
        sums[i] = sums[i - 1] + nums[i - 1];

    int ans = MAX_VALUE;
    for (int i = 1; i <= n; ++i) {
        int sum = target + sums[i - 1];
        // 找到元素直接返回坐标，找不到元素，就返回【需要添加的位置】的相反数 - 1
        int bound = binarySearch(sums, sum);
        // 小于0 说明没找到 target，就会返回：【应插入的位置的相反数】- 1
        if (bound < 0)
            bound = -bound - 1;

        // 大于0 说明找到了元素位置 bound
        if (bound <= n)
            ans = min(ans, bound - (i - 1));

    }
    return ans == MAX_VALUE ? 0 : ans;
}
*/
