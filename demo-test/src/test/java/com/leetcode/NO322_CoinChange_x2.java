/**
 * copyright 2022/1/19
 */
package com.leetcode;

import org.junit.Test;
import static com.leetcode.util.LogUtil.info;

/**
    （中等）
    322. 零钱兑换
        给你一个整数数组 coins ，表示不同面额的硬币；以及一个整数
        amount ，表示总金额。计算并返回可以凑成总金额所需的 最少的
        硬币个数 。如果没有任何一种硬币组合能组成总金额，返回 -1 。
        你可以认为每种硬币的数量是无限的。
    示例 1：
        输入：coins = [1, 2, 5], amount = 11
        输出：3
        解释：11 = 5 + 5 + 1
    示例 2：
        输入：coins = [2], amount = 3
        输出：-1
    示例 3：
        输入：coins = [1], amount = 0
        输出：0
*/
@SuppressWarnings("all")
public class NO322_CoinChange_x2 {

    @Test
    public void test() {
        // 3
        info(coinChange(
            new int[]{1, 2, 5}, 11));
        // -1
//        info(coinChange(
//                new int[]{2}, 3));
    }

    // 其实是一个爬楼梯问题的变种
    // 找到1元、2元面值的最少组合之后，就找到了3元的最少组合
    // 以此类推amount元面值就是 amount-[面值] 和amount-[面值] 的最少面值组合的和
    public int coinChange(int[] coins, int amount) {
        int[] ans = new int[amount + 1];

        return ans[amount];
    }
}












/*
// 方法1
public int coinChange(int[] coins, int amount) {
    if (coins.length == 0)
        return -1;
    // 缓存
    int[] memo = new int[amount + 1];
    // 面值 i
    for (int i = 1; i <= amount; i++) {
        int min = Integer.MAX_VALUE;
        // 硬币数组坐标 j
        for (int j = 0; j < coins.length; j++) {
            // 硬币面值小于目标面值，且比最小的步 min 骤还少
            if (i - coins[j] >= 0 && memo[i - coins[j]] < min) {
                // 最小硬币数 min
                min = memo[i - coins[j]] + 1;
                info("面值:" + i + "，硬币数：" + min);
            }
        }
        // 凑齐面值 i，需要的最小硬币数 min
        memo[i] = min;
    }
    if (memo[amount] == Integer.MAX_VALUE)
        return -1;
    return memo[amount];
}
*/