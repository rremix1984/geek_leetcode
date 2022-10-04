/**
 * copyright 2022/1/19
 */
package com.offer.easy;

import org.junit.Test;
import static com.leetcode.util.LogUtil.info;

/**
    (简单)
    剑指 Offer 10- I. 斐波那契数列
        写一个函数，输入 n ，求斐波那契（Fibonacci）数列的第 n 项（即 F(N)）。斐波那契数列的定义如下：
        F(0) = 0,   F(1) = 1
        F(N) = F(N - 1) + F(N - 2), 其中 N > 1.
        斐波那契数列由 0 和 1 开始，之后的斐波那契数就是由之前的两数相加而得出。
        答案需要取模 1e9+7（1000000007），如计算初始结果为：1000000008，请返回 1。
    示例 1：
        输入：n = 2
        输出：1
    示例 2：
        输入：n = 5
        输出：5
*/
public class Offer_010_I_E_Fib_x2 {

    @Test
    public void test() {
        assert 1 == fib(2);
        assert 5 == fib(5);
        assert 134903163 == fib(45);
        assert 807526948 == fib(48);
    }

    public int fib(int n) {
        return -1;
    }

}


















/**
// 方法1：
public int fib(int n) {
    if (n <= 1)
        return n;

    int n_1 = 1;
    int n_2 = 0;
    for (int i = 2; i <= n; i++) {
        int tmp = n_1;
        n_1 = (n_1 + n_2) % 1000000007;
        n_2 = tmp;
    }
    return n_1;
}
*/