/**
 * copyright 2022/1/19
 */
package com.leetcode.undo;

import org.junit.Test;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.abs;
import static org.junit.Assert.assertEquals;

/**
    (中等)
    166. 分数到小数
        给定两个整数，分别表示分数的分子 numerator 和分母 denominator，以 字符串形式返回小数 。
        如果小数部分为循环小数，则将循环的部分括在括号内。
        如果存在多个答案，只需返回 任意一个 。
        对于所有给定的输入，保证 答案字符串的长度小于 104 。
    示例 1：
        输入：numerator = 1, denominator = 2
        输出："0.5"
    示例 2：
        输入：numerator = 2, denominator = 1
        输出："2"
    示例 3：
        输入：numerator = 4, denominator = 333
        输出："0.(012)"
    提示：
        -231 <= numerator, denominator <= 231 - 1
        denominator != 0
*/
public class NO166_N_FractionToDecimal {

    @Test
    public void test() {
        assertEquals("0.5", fractionToDecimal(1,2));
        assertEquals("2", fractionToDecimal(2, 1));
        assertEquals("0.(012)", fractionToDecimal(4, 333));
    }

    public String fractionToDecimal(int numerator, int denominator) {
        // 转 long 计算，防止溢出
        long a = numerator;
        long b = denominator;

        // 如果本身能够整除，直接返回计算结果
        if (a % b == 0)
            return "" + a / b;

        StringBuilder sb = new StringBuilder();
        // 如果其一为负数，先追加负号
        if (a * b < 0)
            sb.append('-');

        a = abs(a);
        b = abs(b);

        // 计算小数点前的部分，并将余数赋值给 a
        sb.append(a / b + ".");

        a %= b;

        Map<Long, Integer> map = new HashMap<>();
        while (a != 0) {
            // 记录当前余数所在答案的位置，并继续模拟除法运算
            map.put(a, sb.length());

            a = a * 10;

            sb.append(a / b);

            a = a % b;

            // 如果当前余数之前出现过，则将 [出现位置 到 当前位置] 的部分抠出来（循环小数部分）
            if (map.containsKey(a))
                return sb.substring(0, map.get(a)) + "(" + sb.substring(map.get(a)) + ")";

        }
        return sb.toString();
    }

}

















/**
// 方法1：
public String fractionToDecimal(int numerator, int denominator) {
    // 转 long 计算，防止溢出
    long a = numerator;
    long b = denominator;

    // 如果本身能够整除，直接返回计算结果
    if (a % b == 0)
        return String.valueOf(a / b);

    StringBuilder sb = new StringBuilder();
    // 如果其一为负数，先追加负号
    if (a * b < 0)
        sb.append('-');

    a = Math.abs(a);
    b = Math.abs(b);

    // 计算小数点前的部分，并将余数赋值给 a
    sb.append(a / b + ".");
    a %= b;

    Map<Long, Integer> map = new HashMap<>();
    while (a != 0) {
        // 记录当前余数所在答案的位置，并继续模拟除法运算
        map.put(a, sb.length());

        a *= 10;
        sb.append(a / b);
        a %= b;

        // 如果当前余数之前出现过，则将 [出现位置 到 当前位置] 的部分抠出来（循环小数部分）
        if (map.containsKey(a))
            return sb.substring(0, map.get(a)) + "(" + sb.substring(map.get(a)) + ")";
    }
    return sb.toString();
}
*/