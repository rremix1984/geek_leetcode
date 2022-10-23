/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

/**
    (简单)
    1556. 千位分隔数
        给你一个整数 n，请你每隔三位添加点（即 "." 符号）作为千位分隔符，并将结果以字符串格式返回。
    示例 1：
        输入：n = 987
        输出："987"
    示例 2：
        输入：n = 1234
        输出："1.234"
    示例 3：
        输入：n = 123456789
        输出："123.456.789"
    示例 4：
        输入：n = 0
        输出："0"
    提示：
        0 <= n < 2^31
*/
public class NO1556_E_ThousandSeparator_x2 {

    @Test
    public void test() {
        assert "987".equals(thousandSeparator(987));
        assert "1.234".equals(thousandSeparator(1234));
        assert "123.456.789".equals(thousandSeparator(123456789));
        assert "0".equals(thousandSeparator(0));
    }

    public String thousandSeparator(int n) {
        StringBuilder sb = new StringBuilder();
        return sb.toString();
    }

}















/**
public String thousandSeparator(int n) {
    if (n == 0)
        return "0";

    StringBuilder sb = new StringBuilder();
    while (n > 0) {
        for (int i = 0; i < 3 && n > 0; i++) {
            sb.append(n % 10);
            n /= 10;
        }
        if (n > 0)
            sb.append('.');
    }
    return sb.reverse().toString();
}
*/