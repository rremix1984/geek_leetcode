/**
 * copyright 2022/1/19
 */
package com.offer;

import org.junit.Test;

/**
    (简单)
    剑指 Offer II 072. 求平方根
        给定一个非负整数 x ，计算并返回 x 的平方根，即实现 int sqrt(int x) 函数。
        正数的平方根有两个，只输出其中的正数平方根。
        如果平方根不是整数，输出只保留整数的部分，小数部分将被舍去。
    示例 1:
        输入: x = 4
        输出: 2
    示例 2:
        输入: x = 8
        输出: 2
        解释: 8 的平方根是 2.82842...，由于小数部分将被舍去，所以返回 2
*/
public class OfferII_072_E_MySqrt {

    @Test
    public void test() {
        assert 2 == mySqrt(4);
        assert 2 == mySqrt(8);
    }

    public int mySqrt(int x) {
        int ans = -1;
        return ans;
    }

}


















/**
// 方法1：
public int mySqrt(int x) {
    int l = 0;
    int r = x;
    int ans = -1;
    while (l <= r) {
        int mid = l + (r - l) / 2;
        if ((long) mid * mid <= x) {
            ans = mid;
            l = mid + 1;
        } else {
            r = mid - 1;
        }
    }
    return ans;
}
*/