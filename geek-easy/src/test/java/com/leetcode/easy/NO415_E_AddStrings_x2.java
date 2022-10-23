/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

import static com.leetcode.util.MathUtils.reverse;
import static org.junit.Assert.assertEquals;

/**
    (简单)
    415. 字符串相加
        给定两个字符串形式的非负整数 num1 和num2 ，计算它们的和并同样以字符串形式返回。
        你不能使用任何內建的用于处理大整数的库（比如 BigInteger）， 也不能直接将输入的字符串转换为整数形式。
    示例 1：
        输入：num1 = "11", num2 = "123"
        输出："134"
    示例 2：
        输入：num1 = "456", num2 = "77"
        输出："533"
    示例 3：
        输入：num1 = "0", num2 = "0"
        输出："0"
*/
public class NO415_E_AddStrings_x2 {

    @Test
    public void test() {
        assertEquals("134", addStrings("11", "123"));
        assertEquals("533",addStrings("456", "77"));
        assertEquals("0", addStrings("0", "0"));
        assertEquals("1998", addStrings("999", "999"));
    }

    public String addStrings(String num1, String num2) {
        StringBuilder sb = new StringBuilder();
        return reverse(sb);
    }

}






















/**
public String addStrings(String num1, String num2) {
    StringBuilder sb = new StringBuilder();
    // 进位变量
    int carry = 0;
    for (int i = num1.length() - 1,
         j = num2.length() - 1;
         i >= 0 || j >= 0 || carry == 1;
         i--, j--) {
        int n1 = i < 0 ? 0 : num1.charAt(i) - '0';
        int n2 = j < 0 ? 0 : num2.charAt(j) - '0';
        sb.append((n1 + n2 + carry) % 10);
        carry = (n1 + n2 + carry) / 10;
    }
    return sb.reverse().toString();
}

// 方法2：
public String addStrings(String num1, String num2) {
    int i = num1.length() - 1, j = num2.length() - 1, add = 0;
    StringBuffer ans = new StringBuffer();
    while (i >= 0 || j >= 0 || add != 0) {
        int x = i >= 0 ? num1.charAt(i) - '0' : 0;
        int y = j >= 0 ? num2.charAt(j) - '0' : 0;
        int result = x + y + add;
        ans.append(result % 10);
        add = result / 10;
        i--;
        j--;
    }
    // 计算完以后的答案需要翻转过来
    ans.reverse();
    return ans.toString();
}
*/