/**
 * copyright 2022/1/19
 */
package com.dp;

import org.junit.Test;

/**
    [DP] |
    NO.70 爬楼梯
        假设你正在爬楼梯。需要 n 阶你才能到达楼顶。
        每次你可以爬 1 或 2 个台阶。你有多少种不同的方法可以爬到楼顶呢？
    示例 1：
        输入：n = 2
        输出：2
        解释：有两种方法可以爬到楼顶。
            1. 1 阶 + 1 阶
            2. 2 阶
    示例 2：
        输入：n = 3
        输出：3
        解释：有三种方法可以爬到楼顶。
            1. 1 阶 + 1 阶 + 1 阶
            2. 1 阶 + 2 阶
            3. 2 阶 + 1 阶
*/
public class NO070_E_ClimbStairs {

    @Test
    public void test() {
        assert 2  == climbStairs(2);// 2
        assert 3  == climbStairs(3);// 3
        assert 5  == climbStairs(4);// 5
        assert 89 == climbStairs(10);// 89
    }

    public int climbStairs(int n) {
        // 2024/5/15 NO.1 动态规划，不能一遍过但是思路对

        return -1;
    }

}








/*
// 方法1：
public int climbStairs(int n) {
    if (n == 1)
        return 1;
    int s2 = 1;
    int s1 = 2;
    for (int i = 3; i <= n; i++) {
        int tmp = s1;
        s1 = s1 + s2;
        s2 = tmp;
    }
    return s1;
}

// 方法2：
public int climbStairs(int n) {
    if (n < 3)
        return n;

    int[] dp = new int[n + 1];
    dp[1] = 1;
    dp[2] = 2;
    for (int i = 3; i <= n; i++)
        dp[i] = dp[i - 1] + dp[i - 2];

    return dp[n];
}
 */