/**
 * copyright 2022/1/19
 */
package com.leetcode.normal;

import org.junit.Test;

/**
    (中等)
    518. 零钱兑换 II
        给你一个整数数组 coins 表示不同面额的硬币，另给一个整数 amount 表示总金额。
        请你计算并返回可以凑成总金额的硬币组合数。如果任何硬币组合都无法凑出总金额，返回 0 。
        假设每一种面额的硬币有无限个。
        题目数据保证结果符合 32 位带符号整数。
    示例 1：
        输入：amount = 5, coins = [1, 2, 5]
        输出：4
        解释：有四种方式可以凑成总金额：
            5 = 5
            5 = 2 + 2 + 1
            5 = 2 + 1 + 1 + 1
            5 = 1 + 1 + 1 + 1 + 1
    示例 2：
        输入：amount = 3, coins = [2]
        输出：0
        解释：只用面额 2 的硬币不能凑成总金额 3 。
    示例 3：
        输入：amount = 10, coins = [10]
        输出：1

    这道题中，给定总金额 amount 和数组 coins，要求计算金额之和等于 amount 的硬币组合数。其中 coins 的每个元素可以选取多次，
    且不考虑选取元素的顺序，因此这道题需要计算的是选取硬币的组合数。
    可以通过动态规划的方法计算可能的组合数。用 dp[x] 表示金额之和等于 x 的硬币组合数，目标是求 dp[amount]。
    动态规划的边界是 dp[0] = 1。只有当不选取任何硬币时，金额之和才为 0，因此只有 1 种硬币组合。
    对于面额为 coin 的硬币，当 coin ≤ i ≤ amount 时，如果存在一种硬币组合的金额之和等于 i − coin，
    则在该硬币组合中增加一个面额为 coin 的硬币，即可得到一种金额之和等于 i 的硬币组合。
    因此需要遍历 coins，对于其中的每一种面额的硬币，更新数组 dp 中的每个大于或等于该面额的元素的值。
    由此可以得到动态规划的做法：
        初始化 dp[0] = 1；
        遍历 coins，对于其中的每个元素 coin，进行如下操作：
        遍历 i 从 coin 到 amount，将 dp[i − coin] 的值加到 dp[i]。
    最终得到 dp[amount] 的值即为答案。
*/
public class NO518_N_Change_x2 {

    @Test
    public void test() {
        assert 12701 == change(500, new int[]{1, 2, 5});
        assert 4 == change(5, new int[]{1, 2, 5});
        assert 0 == change(3, new int[]{2});
        assert 1 == change(10, new int[]{10});
    }

    public int change(int amount, int[] coins) {
        return -1;
    }

}
















/**
// 方法1：剪枝法（不推荐）明显有性能问题，amount = 500 时超时
public int change(int amount, int[] coins) {
    List<List<Integer>> res = new ArrayList<>();
    Set set = new HashSet();
    call(res, new ArrayList<>(), coins, amount, 1, set);
    return res.size();
}

public void call(List<List<Integer>> res, ArrayList<Integer> list, int[] coins, int amount, int mul, Set set) {
    if (amount < 0)
        return;

    if (amount == 0) {
        if (set.add(mul)) {
            res.add(new ArrayList<>(list));
            return;
        }
    }

    for (int coin : coins) {
        list.add(coin);

        call(res, list, coins, amount - coin, mul * coin, set);

        list.remove(list.size() - 1);
    }
}


// 方法2：动态规划 dp
public int change(int amount, int[] coins) {
    // dp[x] 表示金额之和等于 x 的硬币组合数，
    int[] dp = new int[amount + 1];
    dp[0] = 1;

    for (int coin : coins)
        for (int i = coin; i <= amount; i++)
            dp[i] += dp[i - coin];

    return dp[amount];
}
*/