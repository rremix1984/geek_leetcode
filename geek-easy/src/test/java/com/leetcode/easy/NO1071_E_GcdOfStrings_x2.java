/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

import static com.leetcode.util.LogUtil.info;
import static com.leetcode.util.MathUtils.GCD;
import static org.junit.Assert.assertEquals;

/**
    [STRING]
    (简单)
    1071. 字符串的最大公因子
        对于字符串 s 和 t，只有在 s = t + ... + t（t 自身连接 1 次或多次）时，我们才认定 “t 能除尽 s”。
        给定两个字符串 str1 和 str2 。返回 最长字符串 x，要求满足 x 能除尽 str1 且 X 能除尽 str2 。
    示例 1：
        输入：str1 = "ABCABC", str2 = "ABC"
        输出："ABC"
    示例 2：
        输入：str1 = "ABABAB", str2 = "ABAB"
        输出："AB"
    示例 3：
        输入：str1 = "LEET", str2 = "CODE"
        输出：""
*/
public class NO1071_E_GcdOfStrings_x2 {

    @Test
    public void test() {
        info(GCD(4, 6));
        assertEquals("ABC", gcdOfStrings("ABCABC", "ABC"));
        assertEquals("AB", gcdOfStrings("ABABAB", "ABAB"));
        assertEquals("", gcdOfStrings("LEET", "CODE"));
    }

    public String gcdOfStrings(String str1, String str2) {
        if (!(str1 + str2).equals(str2 + str1))
            return "";
        return str1.substring(0,
                    GCD(str1.length(), str2.length()));
    }

    // 欧几里得法：计算最大公因数
    // 也叫辗转相除法
    public int GCD(int d1, int d2) {
        int tmp = d1 % d2;
        while (tmp != 0) {
            d1 = d2;
            d2 = tmp;
            tmp = d1 % d2;
        }
        return d2;
    }

}























/**
// 方法1：
public String gcdOfStrings(String str1, String str2) {
    if (!(str1 + str2).equals(str2 + str1))
        return "";

    return str1.substring(0,
            GCD(str1.length(), str2.length()));
}
*/