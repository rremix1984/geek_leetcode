package com.leetcode;

import org.junit.Test;
import static java.lang.Math.max;

/**
    [ARRAY] ||
    (中等)
    NO.2461 长度为 K 子数组中的最大和
      给你一个整数数组 nums 和一个整数 k。请你从 nums 中满足下述条件的全部
    子数组中找出最大子数组和：
      1）子数组的长度是 k，且子数组中的所有元素各不相同。
      2）返回满足题面要求的最大子数组和。如果不存在子数组满足这些条件，返回 0。
    子数组：是数组中一段连续非空的元素序列。
    示例 1：
        输入：nums = [1, 5, 4, 2, 9, 9, 9], k = 3
        输出：15
        解释：nums 中长度为 3 的子数组是：
            - [1, 5, 4] 满足全部条件，和为 10。
            - [5, 4, 2] 满足全部条件，和为 11。
            - [4, 2, 9] 满足全部条件，和为 15。
            - [2, 9, 9] 不满足全部条件，因为元素 9 出现重复。
            - [9, 9, 9] 不满足全部条件，因为元素 9 出现重复。
            因为 15 是满足全部条件的所有子数组中的最大子数组和，所以返回 15。
    示例 2：
        输入：nums = [4, 4, 4], k = 3
        输出：0
        解释：nums 中长度为 3 的子数组是：
            - [4, 4, 4] 不满足全部条件，因为元素 4 出现重复。
            因为不存在满足全部条件的子数组，所以返回 0 。
    提示：
        1 <= k <= nums.length <= 10 ^ 5
        1 <= nums[i] <= 10 ^ 5
    Related Topics:数组,哈希表,滑动窗口
*/
@SuppressWarnings("ALL")
public class NO2461_N_MaximumSubarraySum {

    @Test
    public void test() {
        assert 15 == maximumSubarraySum(
                new int[]{1, 5, 4, 2, 9, 9, 9}, 3);
        assert  0 == maximumSubarraySum(
                new int[]{4, 4, 4}, 3);
    }

    public long maximumSubarraySum(int[] nums, int k) {
        // 2024/3/19 NO.1
        long ans = 0;

        return ans;
    }

}























/*
// 方法1：
public long maximumSubarraySum(int[] nums, int k) {
    // 2024/3/19 NO.1
    if (k > nums.length)
        return 0;

    long ans = 0;
    int sum = 0;
    int cat = 0;
    int[] cnt = new int[100005];
    for (int i = 0; i < k; i++) {
        sum += nums[i];
        cnt[nums[i]]++;

        if (cnt[nums[i]] == 2)
            cat++;
    }

    if (cat == 0)
        ans = sum;

    for (int i = k; i < nums.length; i++) {
        if (nums[i] == nums[i - k])
            continue;

        cnt[nums[i]]++;
        cnt[nums[i - k]]--;

        if (cnt[nums[i]] == 2)
            cat++;

        if (cnt[nums[i - k]] == 1)
            cat--;

        sum -= nums[i - k] - nums[i];
        if (cat == 0)
            ans = max(ans, sum);
    }
    return ans;
}
*/