/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

/**
    [NUMBER]
    (简单)
    1137. 第 N 个泰波那契数
        泰波那契序列 Tn 定义如下：T0 = 0, T1 = 1, T2 = 1, 且在 n >= 0 的条件下 Tn+3 = Tn + Tn+1 + Tn+2
        给你整数 n，请返回第 n 个泰波那契数 Tn 的值。
    示例 1：
        输入：n = 4
        输出：4
        解释：T_3 = 0 + 1 + 1 = 2
             T_4 = 1 + 1 + 2 = 4
    示例 2：
        输入：n = 25
        输出：1389537
*/
public class NO1137_E_Tribonacci_x2 {

    @Test
    public void test() {
        assert 4 == tribonacci(4);
        assert 1389537 == tribonacci(25);
    }

    public int tribonacci(int n) {
        int[] tb = new int[38];
        return tb[n];
    }

}
















/**
// 方法1：
public int tribonacci(int n) {
    int t_0 = 0;
    int t_1 = 1;
    int t_2 = 1;

    if (n == 0)
        return t_0;
    if (n == 1)
        return t_1;
    if (n == 2)
        return t_2;

    for (int i = 3; i <= n; i++) {
        int tmp = t_2 + t_1 + t_0;
        t_0 = t_1;
        t_1 = t_2;
        t_2 = tmp;
    }
    return t_2;
}

// 方法2：动态规划：dp
public int tribonacci(int n) {
    int[] tb = new int[38];

    tb[0] = 0;
    tb[1] = 1;
    tb[2] = 1;

    if (n == 0)
        return 0;
    else if(n == 1 || n == 2)
        return 1;

    for (int i = 3; i <= n; i++)
        tb[i] = tb[i - 3] + tb[i - 2] + tb[i - 1];

    return tb[n];
}
*/