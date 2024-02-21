/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;
import static java.lang.Math.max;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
    [STRING]
    (简单)
    67. 二进制求和
        给你两个二进制字符串 a 和 b ，以二进制字符串的形式返回它们的和。
    示例 1：
        输入:a = "11", b = "1"
        输出："100"
    示例 2：
        输入：a = "1010", b = "1011"
        输出："10101"
    提示：
        1 <= a.length, b.length <= 104
        a 和 b 仅由字符 '0' 或 '1' 组成
        字符串如果不是 "0" ，就不含前导零
*/
public class NO067_E_AddBinary_x3 {

    @Test
    public void test() {
        assertEquals("100",
                addBinary("11","1"));
        assertEquals("10101",
                addBinary("1010","1011"));
    }

    public String addBinary(String a, String b) {
        StringBuilder ans = new StringBuilder();
        return ans.reverse().toString();
    }

}



















/**
// 方法1：
public String addBinary(String a, String b) {
    StringBuilder ans = new StringBuilder();
    int carry = 0;
    for (int i = 0; i < max(a.length(), b.length()); i++) {
        if (i < a.length())
            carry += a.charAt(a.length() - 1 - i) - '0';

        if (i < b.length())
            carry += b.charAt(b.length() - 1 - i) - '0';

        ans.append((char) (carry % 2 + '0'));
        carry /= 2;
    }
    if (carry > 0)
        ans.append('1');

    return ans.reverse().toString();
}
*/