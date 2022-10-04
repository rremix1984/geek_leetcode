/**
 * copyright 2022/1/19
 */
package com.offer.easy;

import org.junit.Test;

/**
    (简单)
    剑指 Offer 10- II. 青蛙跳台阶问题
        一只青蛙一次可以跳上 1 级台阶，也可以跳上 2 级台阶。求该青蛙跳上一个 n 级的台阶总共有多少种跳法。
        答案需要取模 1e9+7（1000000007），如计算初始结果为：1000000008，请返回 1。
    示例 1：
        输入：n = 2
        输出：2
    示例 2：
        输入：n = 7
        输出：21
    示例 3：
        输入：n = 0
        输出：1
*/
public class Offer_010_II_E_NumWays_x2 {

    @Test
    public void test() {
        assert  1 == numWays(0);
        assert 21 == numWays(7);
        assert  2 == numWays(2);
        assert  1 == numWays(1);
    }

    public int numWays(int n) {
        int[] dp = new int[n + 1];
        return dp[n];
    }

}


















/**
// 方法1：
public int numWays(int n) {
    int[] dp = new int[n + 1];
    dp[0] = 1;
    if (n >= 1)
        dp[1] = 1;
    for (int i = 2; i <= n; i++)
        dp[i] = (dp[i - 1] + dp[i - 2]) % 1000000007;

    return dp[n];
}
*/