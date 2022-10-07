/**
 * copyright 2022/1/19
 */
package com.leetcode;

import org.junit.Test;

import static com.leetcode.util.LogUtil.info;
import static org.junit.Assert.assertEquals;

/**
    (中等)
    43. 字符串相乘
        给定两个以字符串形式表示的非负整数 num1 和 num2，返回 num1 和 num2 的乘积，它们的乘积也表示为字符串形式。
        注意：不能使用任何内置的 BigInteger 库或直接将输入转换为整数。
    示例 1:
        输入: num1 = "2", num2 = "3"
        输出: "6"
    示例 2:
        输入: num1 = "123", num2 = "456"
        输出: "56088"
*/
public class NO043_N_Multiply {

    @Test
    public void test() {
        assertEquals("6", multiply("2", "3"));
        assertEquals("56088", multiply("123", "456"));
    }

    public String multiply(String num1, String num2) {
        if (num1.equals("0") || num2.equals("0"))
            return "0";

        int m = num1.length();
        int n = num2.length();
        int[] ansArr = new int[m + n];
        for (int i = m - 1; i >= 0; i--) {
            int x = num1.charAt(i) - '0';
            for (int j = n - 1; j >= 0; j--) {
                int y = num2.charAt(j) - '0';
                ansArr[i + j + 1] += x * y;
            }
        }

        for (int i = m + n - 1; i > 0; i--) {
            ansArr[i - 1] += ansArr[i] / 10;
            ansArr[i] %= 10;
        }
        StringBuilder ans = new StringBuilder();
        for (int i = 0; i < ansArr.length; i++)
            if (ansArr[i] != 0 || i > 0)
                ans.append(ansArr[i]);
        return ans.toString();
    }

}