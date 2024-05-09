/**
 * copyright 2020-2024
 */
package com.leetcode;

import org.junit.Test;
import static java.lang.Math.max;

/**
    [ARRAY] |||||
    (中等)
    NO.1027 最长等差数列
    给你一个整数数组 nums，返回 nums 中最长等差子序列的长度。
    回想一下，nums 的子序列是一个列表 nums[i1], nums[i2],
     ..., nums[ik] ，且 0 <= i1 < i2 < ... < ik <= nums.length - 1。
     并且如果 seq[i+1] - seq[i]( 0 <= i < seq.length - 1) 的值都相同，
     那么序列 seq 是等差的。
    示例 1：
        输入：nums = [3, 6, 9, 12]
        输出：4
        解释：
        整个数组是公差为 3 的等差数列。
    示例 2：
        输入：nums = [9, 4, 7, 2, 10]
        输出：3
        解释：
        最长的等差子序列是 [4, 7, 10]。
    示例 3：
        输入：nums = [20, 1, 15, 3, 10, 5, 8]
        输出：4
        解释：
        最长的等差子序列是 [20, 15, 10, 5]。
    提示：
        2 <= nums.length <= 1000
        0 <= nums[i] <= 500
    Related Topics:数组,哈希表,二分查找,动态规划
    动态规划：
        类似最长公共子序列的两层循环dp。
        dp[i][d] 表示以 nums[i] 为结尾，公差为d的子序列长度。
    在这道Java函数中，动态规划被用来解决一个经典问题：在数组nums中寻找最长的等差数列，
    并返回其长度。等差数列指的是数组中的一个子序列，其中任意相邻两项之间的差是一个常数。
    函数实现的关键在于理解动态规划的状态定义和状态转移方程：
    状态定义：
        状态变量是二维数组 dp[i][d]，其中 i 表示数组中的索引位置，d 是当前考虑的差值
    （即 nums[i] - nums[j] + 500）。
    对于每个位置 i 和差值 d，dp[i][d] 存储以 nums[i] 结尾且差值为 d 的最长等差数列
    的长度。
    状态转移方程：
        当遍历到数组中的第 i 个元素时，它会检查之前的所有元素（从 j = 0 到 j = i - 1），
    计算与当前元素的差值并调整至非负范围（通过加上 500 来确保不会出现负下标）。
    核心部分的代码 dp[i][d] = max(dp[i][d], dp[j][d] + 1); 就是状态转移的过程，
    如果找到一个更长的具有相同差值的等差数列，则更新 dp[i][d] 的值。
    同时，每次更新之后都会检查当前最长等差数列长度是否超过了全局最优解 ans，
    若超过则更新 ans。
    边界条件：
        初始化时，所有 dp[i][d] 可能都被初始化为 0 或未显式初始化，表示没有等差数列。
    最终答案是在遍历完所有可能的情况后，ans 中记录了整个数组中最长等差数列的长度，
    因此返回 ans + 1（通常不需要加1，这里可能是为了数组长度从1开始计数）。
    通过这种自底向上、逐步构造最优解的方式，该函数有效地利用了动态规划思想来避免重复计算，
    确保了对给定数组求解最长等差数列问题的效率。
*/
public class NO1027_N_LongestArithSeqLength {

    @Test
    public void test() {
        assert 4 == longestArithSeqLength(new int[]{3, 6, 9, 12});
        assert 3 == longestArithSeqLength(new int[]{9, 4, 7, 2, 10});
    }

    public int longestArithSeqLength(int[] nums) {
        // 2024/3/17 NO.1 动态规划
        // 2024/3/18 NO.2 没思路
        // 2024/3/20 NO.3 没思路
        // 2024/3/25 NO.4 没做出来..
        // 2024/3/29 NO.5
        int ans = 0;
        int n = nums.length;
        int[][] dp = new int[1001][n];

        return ans + 1;
    }

}















/*
// 方法1：动态规划（需要深入理解）
public int longestArithSeqLength(int[] nums) {
    int ans = 0;
    int n = nums.length;
    int[][] dp = new int[n][1001];
    for (int i = 0; i < n; i++)
        for (int j = 0; j < i; j++) {
            // +500防止负数下标
            int d = nums[i] - nums[j] + 500;
            // 这里才是核心
            dp[i][d] = max(dp[i][d], dp[j][d] + 1);
            ans = max(ans, dp[i][d]);
        }
    return ans + 1;
}
*/