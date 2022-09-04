/**
 * copyright 2022/1/19
 */
package com.leetcode.normal;

import org.junit.Test;
import static com.leetcode.util.LogUtil.info;
import static java.lang.Math.abs;

/**
    （中等）
    50. Pow(x, n)
    实现 pow(x, n) ，即计算 x 的整数 n 次幂函数（即，xn ）。

    示例 1：
        输入：x = 2.00000, n = 10
        输出：1024.00000
    示例 2：
        输入：x = 2.10000, n = 3
        输出：9.26100
    示例 3：
        输入：x = 2.00000, n = -2
        输出：0.25000
        解释：2-2 = 1/22 = 1/4 = 0.25
*/
public class NO50_N_PowXN_x2 {

    @Test
    public void test() {
        info(myPow(2.0, -2));
    }

    public double myPow(double x, int n) {
        double res = 1.0;
        long N = abs((long) n);
        double c = x;
        while (N > 0) {
            if (N % 2 == 1)
                res *= c;
            c *= c;
            N /= 2;
        }
        if (n < 0)
            return 1 / res;
        return res;
    }
}


















/**
public double myPow(double x, int n) {
    long N = n;
    if (n >= 0)
        return quickMul(x, N);
    else
        return 1 / quickMul(x, -N);
}

public double quickMul(double x, long N) {
    double ans = 1.0;
    // 贡献的初始值为 x
    double c = x;
    // 在对 N 进行二进制拆分的同时计算答案
    while (N > 0) {
        if (N % 2 == 1) {
            // 如果 N 二进制表示的最低位为 1，那么需要计入贡献
            ans *= c;
        }
        // 将贡献不断地平方
        c *= c;
        // 舍弃 N 二进制表示的最低位，这样我们每次只要判断最低位即可
        N /= 2;
    }
    return ans;
}
*/