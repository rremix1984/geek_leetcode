/**
 * copyright 2022/1/19
 */
package com.offer.normal;

import org.junit.Test;

/**
    (中等)
    剑指 Offer 16. 数值的整数次方
        实现 pow(x, n) ，即计算 x 的 n 次幂函数（即，xn）。不得使用库函数，同时不需要考虑大数问题。
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
public class Offer16_N_MyPow_x2 {

    @Test
    public void test() {
        assert 1024.00000 == myPow(2.00000, 10);
        assert 9.261000000000001 == myPow(2.1, 3);
        assert 0.25000 == myPow(2.00000, -2);
        assert 0.0 == myPow(2.00000, -2147483648);
    }

    public double myPow(double x, int n) {
        return 0.0;
    }

}

















/**
// 方法1：
public double myPow(double x, int n) {
    long N = n;
    if (N >= 0)
        return quickMul(x, N);

    return 1.0 / quickMul(x, -N);
}

public double quickMul(double x, long N) {
    double ans = 1.0;

    // 贡献的初始值为 x
    double C = x;

    // 在对 N 进行二进制拆分的同时计算答案
    while (N > 0) {
        if (N % 2 == 1)
            // 如果 N 二进制表示的最低位为 1，那么需要计入贡献
            ans *= C;

        // 将贡献不断地平方
        C *= C;

        // 舍弃 N 二进制表示的最低位，这样我们每次只要判断最低位即可
        N /= 2;
    }
    return ans;
}


// 方法2：
public double myPow(double x, int N) {
    long n = N;
    return n >= 0 ? quickMul(x, n) : 1.0 / quickMul(x, -n);
}

public double quickMul(double x, long N) {
    if (N == 0)
        return 1.0;

    double y = quickMul(x, N / 2);
    return N % 2 == 0 ? y * y : y * y * x;
}
*/