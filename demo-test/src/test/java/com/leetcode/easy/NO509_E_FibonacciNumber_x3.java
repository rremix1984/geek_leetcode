/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;
import static com.leetcode.util.LogUtil.info;
import static org.junit.Assert.assertEquals;

/**
    (简单)
    509. 斐波那契数
        斐波那契数 （通常用 F(n) 表示）形成的序列称为 斐波那契数列 。该数列由 0 和 1 开始，后面的每一项数字都是前面两项数字的和。也就是：
        F(0) = 0，F(1) = 1
        F(n) = F(n - 1) + F(n - 2)，其中 n > 1
        给定 n ，请计算 F(n) 。
    示例 1：
        输入：n = 2
        输出：1
        解释：F(2) = F(1) + F(0) = 1 + 0 = 1
    示例 2：
        输入：n = 3
        输出：2
        解释：F(3) = F(2) + F(1) = 1 + 1 = 2
    示例 3：
        输入：n = 4
        输出：3
        解释：F(4) = F(3) + F(2) = 2 + 1 = 3
*/
public class NO509_E_FibonacciNumber_x3 {

    @Test
    public void test() {
        assertEquals(610, fib(15));// 610
        assertEquals(1, fib(2));// 1
        assertEquals(2, fib(3));// 2
        assertEquals(3, fib(4));// 3
    }

    public int fib(int n) {
        return -1;
    }
}













/**
// 方法1：递归
public int fib(int n) {
    return call(n,new int[n + 1]);
}

private int call(int n, int[] mem) {
    if (n <= 1)
        return n;
    else if (mem[n] == 0)
        mem[n] = call(n-1, mem) + call(n-2, mem);
    return mem[n];
}

// 方法2：迭代法
public int fib(int n) {
    int n_1 = 1; // f(1)
    int n_2 = 1; // f(0)
    if (n == 0)
        return 0;

    if (n <= 2)
        return 1;

    for (int i = 3; i <= n; i++) {
        int tmp = n_1;
        n_1 = n_1 + n_2;
        n_2 = tmp;
    }
    return n_1;
}

// 方法3：动态规划
public int fib(int n) {
    if (n <= 1)
        return n;

    int[] dp = new int[n + 1];
    dp[0] = 0;
    dp[1] = 1;

    for (int i = 2; i <= n; i++)
        dp[i] = dp[i - 1] + dp[i - 2];

    return dp[n];
}
*/