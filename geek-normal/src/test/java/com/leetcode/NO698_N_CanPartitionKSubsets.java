package com.leetcode;

import org.junit.Test;

/**
    [ARRAY]
    (中等)
    NO.698 划分为K个相等的子集
    给定一个整数数组 nums 和一个正整数 k，找出是否有可能把这个数组分成 k 个非空子集，
    其总和都相等。
    示例 1：
        输入： nums = [4, 3, 2, 3, 5, 2, 1], k = 4
        输出： True
        说明： 有可能将其分成 4 个子集(5)、(1, 4)、(2, 3)、(2, 3)等于总和。
    示例 2:
        输入: nums = [1, 2, 3, 4], k = 3
        输出: false
        提示：
            1 <= k <= len(nums) <= 16
            0 < nums[i] < 10000
            每个元素的频率在 [1,4] 范围内
    Related Topics:位运算,记忆化搜索,数组,动态规划,回溯,状态压缩
*/
public class NO698_N_CanPartitionKSubsets {

    @Test
    public void test() {
        assert canPartitionKSubsets(
                new int[]{4, 3, 2, 3, 5, 2, 1}, 4);
        assert !canPartitionKSubsets(
                new int[]{1, 2, 3, 4}, 3);
    }

    public boolean canPartitionKSubsets(int[] nums, int k) {

        return false;
    }

}
















/*
// 方法1：
public boolean canPartitionKSubsets(int[] nums, int k) {
    // 边界条件
    if (k > nums.length)
        return false;

    int sum = 0;
    for (int x : nums)
        sum += x;

    if (sum % k != 0)
        return false;

    // 记录数组中元素的使用情况，对于每个子集（桶）我们都需要遍历全部的数组元素
    boolean[] used = new boolean[nums.length];

    // 理论上每个桶应该装的数字之和
    int target = sum / k;

    // DFS k号桶初始没有放任何元素，从 nums[0] 开始做选择
    return backtrack(k, 0, nums, 0, used, target);
}

// DFS 递归穷举 nums 中每个数字
public boolean backtrack(int k, int bucketSum, int[] nums, int start, boolean[] used, int target) {
    // base case
    if (k == 0)
        // 所有的桶都被装满了，且 nums 一定被使用完了
        return true;

    if (bucketSum == target)
        // 当前桶装满了，进行下一个子集的穷举，从 nums[0] 开始做选择
        return backtrack(k - 1, 0, nums, 0, used, target);

    // 从 start 开始向后遍历，将有效的 nums[i] 装入当前的子集（桶）
    for (int i = start; i < nums.length; i++) {
        // 剪枝，nums[i] 已经装入别的子集中
        if (used[i]) continue;
        // 剪枝，当前桶装不下 nums[i]
        if (bucketSum + nums[i] > target)
            continue;

        // 做选择，将 nums[i] 装入当前的桶中
        used[i] = true;
        bucketSum += nums[i];
        // 递归下一个数字是否放入该桶中
        if (backtrack(k, bucketSum, nums, i + 1, used, target))
            return true;

        // 撤销选择
        used[i] = false;
        bucketSum -= nums[i];
    }
    // nums[index] 装入哪个桶都不行
    return false;
}
*/