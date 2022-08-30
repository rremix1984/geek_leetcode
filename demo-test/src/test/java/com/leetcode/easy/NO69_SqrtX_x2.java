/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;
import static com.leetcode.util.LogUtil.info;

/**
    （简单）
    69. x 的平方根
        给你一个非负整数 x ，计算并返回 x 的 算术平方根 。
        由于返回类型是整数，结果只保留 整数部分 ，小数部分将被 舍去 。
        注意：不允许使用任何内置指数函数和算符，例如 pow(x, 0.5) 或者 x ** 0.5 。
    示例 1：
        输入：x = 4
        输出：2
    示例 2：
        输入：x = 8
        输出：2
        解释：8 的算术平方根是 2.82842...,
            由于返回类型是整数，小数部分将被舍去。
*/
@SuppressWarnings("all")
public class NO69_SqrtX_x2 {

    @Test
    public void test() {
        info(mySqrt(4));
        info(mySqrt(2147395599));
    }

    public int mySqrt(int x) {
        return 0;
    }
}














/*
// 方法1：二分查找法
public int mySqrt(int x) {
    int l = 0, r = x;
    while (l <= r) {
        int mid = l + (r - l) / 2;
        if ((long) mid * mid <= x) {
            l = mid + 1;
        } else {
            r = mid - 1;
        }
    }
    return r;
}

// 方法2：牛顿迭代法
public int mySqrt(int x) {
    long r = x;
    while (r * r > x) {
        r = (r + x / r) / 2;
    }
    return (int) r;
}
*/