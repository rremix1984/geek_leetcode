/**
 * copyright 2022/1/19
 */
package com.leetcode;

import org.junit.Test;
import static com.leetcode.util.LogUtil.info;

/**
    （中等）
    518. 零钱兑换 II
        给你一个整数数组 coins 表示不同面额的硬币，另给一个整数 amount 表示总金额。
        请你计算并返回可以凑成总金额的硬币组合数。如果任何硬币组合都无法凑出总金额，返回 0 。
        假设每一种面额的硬币有无限个。
        题目数据保证结果符合 32 位带符号整数。
    示例 1：
        输入：amount = 5, coins = [1, 2, 5]
        输出：4
        解释：有四种方式可以凑成总金额：
            5=5
            5=2+2+1
            5=2+1+1+1
            5=1+1+1+1+1
    示例 2：
        输入：amount = 3, coins = [2]
        输出：0
        解释：只用面额 2 的硬币不能凑成总金额 3 。
    示例 3：
        输入：amount = 10, coins = [10]
        输出：1

         这道题中，给定总金额 \textit{amount}amount 和数组 \textit{coins}coins，要求计算金额之和等于 \textit{amount}amount 的硬币组合数。其中，\textit{coins}coins 的每个元素可以选取多次，且不考虑选取元素的顺序，因此这道题需要计算的是选取硬币的组合数。
     可以通过动态规划的方法计算可能的组合数。用 \textit{dp}[x]dp[x] 表示金额之和等于 xx 的硬币组合数，目标是求 \textit{dp}[\textit{amount}]dp[amount]。
     动态规划的边界是 \textit{dp}[0]=1dp[0]=1。只有当不选取任何硬币时，金额之和才为 00，因此只有 11 种硬币组合。
     对于面额为 \textit{coin}coin 的硬币，当 \textit{coin} \le i \le \textit{amount}coin≤i≤amount 时，如果存在一种硬币组合的金额之和等于 i - \textit{coin}i−coin，则在该硬币组合中增加一个面额为 \textit{coin}coin 的硬币，即可得到一种金额之和等于 ii 的硬币组合。因此需要遍历 \textit{coins}coins，对于其中的每一种面额的硬币，更新数组 \textit{dp}dp 中的每个大于或等于该面额的元素的值。
     由此可以得到动态规划的做法：
     初始化 \textit{dp}[0]=1dp[0]=1；
     遍历 \textit{coins}coins，对于其中的每个元素 \textit{coin}coin，进行如下操作：
     遍历 ii 从 \textit{coin}coin 到 \textit{amount}amount，将 \textit{dp}[i - \textit{coin}]dp[i−coin] 的值加到 \textit{dp}[i]dp[i]。
     最终得到 \textit{dp}[\textit{amount}]dp[amount] 的值即为答案。
     上述做法不会重复计算不同的排列。因为外层循环是遍历数组 \textit{coins}coins 的值，内层循环是遍历不同的金额之和，在计算 \textit{dp}[i]dp[i] 的值时，可以确保金额之和等于 ii 的硬币面额的顺序，由于顺序确定，因此不会重复计算不同的排列。
     例如，\textit{coins}=[1,2]coins=[1,2]，对于 {dp}[3]dp[3] 的计算，一定是先遍历硬币面额 11 后遍历硬币面额 22，只会出现以下 22 种组合：
     3 = 1 + 1 + 1
     3 = 1 + 2
    硬币面额 2 不可能出现在硬币面额 1 之前，即不会重复计算 3 = 2 + 1 的情况。
*/
public class NO518_CoinChangeII_x2 {

    @Test
    public void test() {
        info(change(5, new int[]{1, 2, 5}));// 4
        info(change(3, new int[]{2}));// 0
        info(change(10, new int[]{10}));// 1
    }

    public int change(int amount, int[] coins) {
        return -1;
    }

}















/**
// 方法1：dp动态规划，dp[n] 表示凑齐 n 元钱，有多少种可能
public int change(int amount, int[] coins) {
    // 为了让面值等于坐标，dp长度为 amount + 1
    int[] dp = new int[amount + 1];
    // 0 元的策略有 1 种
    dp[0] = 1;
    for (int coin : coins)
        // 只用一个面值，有多少种可能性
        // 如果是大面值，就能从之前的小面值里面获取参考值 dp[i - coin]
        for (int i = coin; i <= amount; i++)
            dp[i] += dp[i - coin];
    return dp[amount];
}
*/