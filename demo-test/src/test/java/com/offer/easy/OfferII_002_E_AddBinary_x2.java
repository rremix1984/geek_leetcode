/**
 * copyright 2022/1/19
 */
package com.offer.easy;

import org.junit.Test;
import static com.leetcode.util.LogUtil.info;
import static java.lang.Math.max;
import static org.junit.Assert.assertEquals;

/**
    (简单)
    剑指 Offer II 002. 二进制加法
        给定两个 01 字符串 a 和 b ，请计算它们的和，并以二进制字符串的形式输出。
        输入为 非空 字符串且只包含数字 1 和 0。
    示例 1:
        输入: a = "11", b = "10"
        输出: "101"
    示例 2:
        输入: a = "1010", b = "1011"
        输出: "10101"
*/
public class OfferII_002_E_AddBinary_x2 {

    @Test
    public void test() {
        assertEquals("101", addBinary("11", "10"));
        assertEquals("10101", addBinary("1010", "1011"));
        assertEquals("110101", addBinary("11010", "11011"));
    }

    public String addBinary(String a, String b) {
        StringBuilder ans = new StringBuilder();
        return ans.toString();
    }

}


















/**
// 方法1：
public String addBinary(String a, String b) {
    StringBuffer ans = new StringBuffer();
    int n = max(a.length(), b.length());

    int carry = 0;
    for (int i = 0; i < n; i++) {
        if (i < a.length())
            carry += a.charAt(a.length() - 1 - i) - '0';

        if (i < b.length())
            carry += b.charAt(b.length() - 1 - i) - '0';

        ans.append((char) (carry % 2 + '0'));
        carry /= 2;
    }

    if (carry > 0)
        ans.append('1');

    ans.reverse();

    return ans.toString();
}
*/