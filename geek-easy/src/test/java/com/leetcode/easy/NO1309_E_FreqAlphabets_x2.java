/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;
import static java.lang.Integer.parseInt;
import static org.junit.Assert.assertEquals;

/**
    (简单)
    1309. 解码字母到整数映射
        给你一个字符串 s，它由数字（'0' - '9'）和 '#' 组成。
        我们希望按下述规则将 s 映射为一些小写英文字符：
         1）字符（'a' - 'i'）分别用（'1' - '9'）表示。
         2）字符（'j' - 'z'）分别用（'10#' - '26#'）表示。
        返回映射之后形成的新字符串。
        题目数据保证映射始终唯一。
    示例 1：
        输入：s = "10#11#12"
        输出："jkab"
        解释："j" -> "10#" , "k" -> "11#" , "a" -> "1" , "b" -> "2".
    示例 2：
        输入：s = "1326#"
        输出："acz"
*/
public class NO1309_E_FreqAlphabets_x2 {

    @Test
    public void test() {
        assertEquals("jkab", freqAlphabets("10#11#12"));
        assertEquals("acz", freqAlphabets("1326#"));
    }

    char[] map = "abcdefghijklmnopqrstuvwxyz".toCharArray();

    public String freqAlphabets(String s) {
        StringBuilder sb = new StringBuilder();
        return sb.toString();
    }

}



















/**
// 方法1：
public String freqAlphabets(String s) {
    // 1 - 9 只有一位数，10 - 26 有 10# - 26# 三位数
    StringBuilder sb = new StringBuilder();
    char[] map = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    for (int i = 0; i < s.length(); i++)
        // 结尾是 # 号，就要连看3个字符
        if (i < s.length() - 2 && s.charAt(i + 2) == '#') {
            String num = s.substring(i, i + 2);
            char c = map[parseInt(num) - 1];
            sb.append(c);
            // 跳2步，例如：当前在 "2" 后面的 "6" 和 "#" 都跳过去
            i += 2;
        } else {
            char c = (char) (map[s.charAt(i) - '0'] - 1);
            sb.append(c);
        }
    return sb.toString();
}
*/