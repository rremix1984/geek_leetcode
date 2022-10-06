/**
 * copyright 2022/1/19
 */
package com.leetcode;

import org.junit.Test;

/**
    (中等)
    29. 两数相除
        给定两个整数，被除数 dividend 和除数 divisor。将两数相除，要求不使用乘法、除法和 mod 运算符。
        返回被除数 dividend 除以除数 divisor 得到的商。
        整数除法的结果应当截去（truncate）其小数部分，例如：truncate(8.345) = 8 以及 truncate(-2.7335) = -2
    示例 1:
        输入: dividend = 10, divisor = 3
        输出: 3
        解释: 10/3 = truncate(3.33333..) = truncate(3) = 3
    示例 2:
        输入: dividend = 7, divisor = -3
        输出: -2
        解释: 7/-3 = truncate(-2.33333..) = -2
*/
public class NO029_N_Divide {

    @Test
    public void test() {
        assert 3 == divide(10, 3);
        assert -2 == divide(7, -3);
    }

    public int divide(int dividend, int divisor) {
        return -1;
    }

}


















/**
 * 解题思路
 * 用 2^i 去作为乘法基数, x * 2^i = x << i 。
 * 从 2^31  试到 2^0
 * 直到被除数被减到比除数小，
 * 每个能满足除出来的最大的2的幂都加入答案
 * 也可以理解为每次计算出答案的32位中的某一位
 */
/**
public int divide(int dividend, int divisor) {
    if (dividend == MIN_VALUE && divisor == -1)
        return MAX_VALUE;
    long a = abs((long) dividend);
    long b = abs((long) divisor);
    int res = 0;
    for (int i = 31; i >= 0; i--) {
        if (a >> i >= b) {
            res += 1 << i;
            a -= b << i;
        }
    }
    return (dividend > 0) == (divisor > 0) ? res : -res;
}
*/
