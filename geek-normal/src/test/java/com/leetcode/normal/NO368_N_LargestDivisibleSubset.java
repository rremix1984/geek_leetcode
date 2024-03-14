package com.leetcode.normal;

import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static com.leetcode.util.MathUtils.getArray;
import static com.leetcode.util.SystemUtil.arrayAllMatch;

/**
    [ARRAY]
    (中等)
    NO.368 最大整除子集
    给你一个由无重复正整数组成的集合nums，请你找出并返回其中最大的整除子集answer，
    子集中每一元素对 (answer[i], answer[j]) 都应当满足：
        answer[i] % answer[j] == 0 ，或
        answer[j] % answer[i] == 0
    如果存在多个有效解子集，返回其中任何一个均可。
    示例 1：
        输入：nums = [1,2,3]
        输出：[1,2]
        解释：[1,3] 也会被视为正确答案。
    示例 2：
        输入：nums = [1,2,4,8]
        输出：[1,2,4,8]
    提示：
        1 <= nums.length <= 1000
        1 <= nums[i] <= 2 * 109
        nums 中的所有整数 互不相同
    Related Topics:数组,数学,动态规划,排序
*/
public class NO368_N_LargestDivisibleSubset {

    @Test
    public void test() {
        assert arrayAllMatch(getArray(1, 2),
            largestDivisibleSubset(new int[]{1, 2, 3}));
        assert arrayAllMatch(getArray(1, 2, 4, 8),
            largestDivisibleSubset(new int[]{1, 2, 4, 8}));
    }

    public List<Integer> largestDivisibleSubset(int[] nums) {
        int len = nums.length;
        Arrays.sort(nums);

        // 第 1 步：动态规划找出最大子集的个数、最大子集中的最大整数
        int[] dp = new int[len];
        Arrays.fill(dp, 1);
        int maxSize = 1;
        int maxVal = dp[0];
        for (int i = 1; i < len; i++) {
            for (int j = 0; j < i; j++) {
                // 题目中说「没有重复元素」很重要
                if (nums[i] % nums[j] == 0)
                    dp[i] = Math.max(dp[i], dp[j] + 1);
            }

            if (dp[i] > maxSize) {
                maxSize = dp[i];
                maxVal = nums[i];
            }
        }

        // 第 2 步：倒推获得最大子集
        List<Integer> res = new ArrayList<>();
        if (maxSize == 1) {
            res.add(nums[0]);
            return res;
        }

        for (int i = len - 1; i >= 0 && maxSize > 0; i--) {
            if (dp[i] == maxSize && maxVal % nums[i] == 0) {
                res.add(nums[i]);
                maxVal = nums[i];
                maxSize--;
            }
        }
        return res;
    }

}
















/*
// 方法1：
public List<Integer> largestDivisibleSubset(int[] nums) {
    int len = nums.length;
    Arrays.sort(nums);

    // 第 1 步：动态规划找出最大子集的个数、最大子集中的最大整数
    int[] dp = new int[len];
    Arrays.fill(dp, 1);
    int maxSize = 1;
    int maxVal = dp[0];
    for (int i = 1; i < len; i++) {
        for (int j = 0; j < i; j++) {
            // 题目中说「没有重复元素」很重要
            if (nums[i] % nums[j] == 0)
                dp[i] = Math.max(dp[i], dp[j] + 1);
        }

        if (dp[i] > maxSize) {
            maxSize = dp[i];
            maxVal = nums[i];
        }
    }

    // 第 2 步：倒推获得最大子集
    List<Integer> res = new ArrayList<>();
    if (maxSize == 1) {
        res.add(nums[0]);
        return res;
    }

    for (int i = len - 1; i >= 0 && maxSize > 0; i--) {
        if (dp[i] == maxSize && maxVal % nums[i] == 0) {
            res.add(nums[i]);
            maxVal = nums[i];
            maxSize--;
        }
    }
    return res;
}
*/