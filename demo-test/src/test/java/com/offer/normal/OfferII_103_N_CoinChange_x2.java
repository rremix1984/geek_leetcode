/**
 * copyright 2022/1/19
 */
package com.offer.normal;

import org.junit.Test;
import static com.leetcode.util.LogUtil.info;
import static java.lang.Math.min;

/**
    (中等)
    剑指 Offer II 103. 最少的硬币数目
        给定不同面额的硬币 coins 和一个总金额 amount。编写一个函数来计算
        可以凑成总金额所需的最少的硬币个数。如果没有任何一种硬币组合能组成总金额，返回 -1。
        你可以认为每种硬币的数量是无限的。
    示例 1：
        输入：coins = {1, 2, 5}, amount = 11
        输出：3
        解释：11 = 5 + 5 + 1
    示例 2：
        输入：coins = {2}, amount = 3
        输出：-1
    示例 3：
        输入：coins = {1}, amount = 0
        输出：0
    示例 4：
        输入：coins = {1}, amount = 1
        输出：1
    示例 5：
        输入：coins = {1}, amount = 2
        输出：2
*/
public class OfferII_103_N_CoinChange_x2 {

    @Test
    public void test() {
        assert  3 == coinChange(new int[]{1, 2, 5}, 11);
        assert -1 == coinChange(new int[]{2}, 3);
        assert  0 == coinChange(new int[]{1}, 0);
        assert  1 == coinChange(new int[]{1}, 1);
        assert  2 == coinChange(new int[]{1}, 2);
    }

    public int coinChange(int[] coins, int amount) {
        return -1;
    }

}
















/**
// 方法1：动态规划 dp
public int coinChange(int[] coins, int amount) {
    int[] dp = new int[amount + 1];

    Arrays.fill(dp, MAX_VALUE);
    dp[0] = 0;

    for (int i = 1; i <= amount; i++)
        for (int num : coins) {
            if (num <= i)
                dp[i] = min(dp[i], dp[i - num] + 1);

            if (dp[i] > amount)
                return -1;
        }
    return dp[amount];
}


// 方法2：递归
public int coinChange(int[] coins, int amount) {
    if (amount < 1)
        return 0;
    return coinChange(coins, amount, new int[amount]);
}

private int coinChange(int[] coins, int rem, int[] count) {
    if (rem <= 0)
        return rem;

    if (count[rem - 1] != 0)
        return count[rem - 1];

    int min = MAX_VALUE;
    for (int coin : coins) {
        int res = coinChange(coins, rem - coin, count);
        if (res >= 0 && res < min)
            min = 1 + res;
    }

    count[rem - 1] = (min == Integer.MAX_VALUE) ? -1 : min;
    return count[rem - 1];
}
*/