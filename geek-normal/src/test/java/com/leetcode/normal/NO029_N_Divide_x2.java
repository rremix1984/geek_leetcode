/**
 * copyright 2022/1/19
 */
package com.leetcode.normal;

import org.junit.Test;
import static com.leetcode.util.LogUtil.info;
import static java.lang.Integer.MAX_VALUE;
import static java.lang.Integer.MIN_VALUE;
import static java.lang.Math.abs;

/**
    (中等)
    29. 两数相除
        给定两个整数，被除数 dividend 和除数 divisor。
        将两数相除，要求不使用乘法、除法和 mod 运算符。
        返回被除数 dividend 除以除数 divisor 得到的商。
        整数除法的结果应当截去（truncate）其小数部分，
        例如：truncate(8.345) = 8 以及 truncate(-2.7335) = -2
    示例 1:
        输入: dividend = 10, divisor = 3
        输出: 3
        解释: 10/3 = truncate(3.33333..) = truncate(3) = 3
    示例 2:
        输入: dividend = 7, divisor = -3
        输出: -2
        解释: 7/-3 = truncate(-2.33333..) = -2
*/
public class NO029_N_Divide_x2 {

    @Test
    public void test() {
        assert 3 == divide(10,3);
        assert -2 == divide(7,-3);
        assert -333 == divide(1000,-3);
    }

    public int divide(int a, int b) {
        return -1;
    }

}

















/**
 * 解题思路
 * 用 2 ^ i 去作为乘法基数, x * 2^i = x << i 。
 * 从 2 ^ 31  试到 2 ^ 0
 * 直到被除数被减到比除数小，
 * 每个能满足除出来的最大的2的幂都加入答案
 * 也可以理解为每次计算出答案的32位中的某一位
 *
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

// 方法2：
public int divide(int a, int b) {
    if(a == 0)
        return 0;

    if(a == MIN_VALUE && b == -1)
        return MAX_VALUE;

    int sign = 1;
    if ((a > 0 && b < 0)
            || (a < 0 && b > 0))
        sign = -1;

    // 因为 MIN_VALUE 的范围比 MAX_VALUE 大
    // 全变成负数
    a = a > 0 ? -a : a;
    b  = b > 0  ? -b  : b;

    return sign * dvd(a, b);
}

// 递归除法
// 两个负数相除，如果 a > b 那么 |a| > |b|
public int dvd(int a, int b) {
    //a = -1,  b = -5
    if (a > b)
        return 0;

    if (a == b)
        return 1;

    int count = 1;
    int res = 0;
    int tb = b;
    // 当 a 小于等于 b 的时候，|a| > |b|
    while (a <= tb && tb < 0) {
        // 先把b剪掉
        a -= tb;

        // res 相除的结果
        res += count;

        // 1）除数翻倍
        tb += tb;

        // 2）计数器也需要翻倍
        count += count;
    }

    // 递归的原因是每次分母越除越多，到最后大于分子了
    // 要从头开始除了
    return res + dvd(a, b);
}
*/