/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

import static com.leetcode.util.LogUtil.info;
import static java.lang.Math.abs;

/**
    (简单)
    504. 七进制数
        给定一个整数 num，将其转化为 7 进制，并以字符串形式输出。
    示例 1:
        输入: num = 100
        输出: "202"
    示例 2:
        输入: num = -7
        输出: "-10"
    提示：
        -107 <= num <= 107
*/
public class NO504_E_ConvertToBase7_x2 {

    @Test
    public void test() {
        assert "202".equals(convertToBase7(100));
        assert "-10".equals(convertToBase7(-7));
    }

    public String convertToBase7(int num) {
        StringBuilder sb = new StringBuilder();
        return sb.reverse().toString();
    }

}




















/**
// 方法1：除法 + 模除
public String convertToBase7(int num) {
    if (num == 0)
        return "0";

    boolean negative = num < 0;
    num = abs(num);
    StringBuilder sb = new StringBuilder();
    while (num > 0) {
        sb.append(num % 7);
        num /= 7;
    }

    if (negative)
        sb.append('-');

    return sb.reverse().toString();
}*/
