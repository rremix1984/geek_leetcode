/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;
import static com.leetcode.util.LogUtil.info;
import static org.junit.Assert.assertEquals;

/**
    （简单）
    557. 反转字符串中的单词 III
        给定一个字符串 s ，你需要反转字符串中每个单词的字符顺序，同时仍保留空格和单词的初始顺序。
    示例 1：
        输入：s = "Let's take LeetCode contest"
        输出："s'teL ekat edoCteeL tsetnoc"
    示例 2:
        输入： s = "God Ding"
        输出："doG gniD"
*/
public class NO557_E_ReverseWordsInAStringIII_x2 {

    @Test
    public void test () {
        assertEquals("s'teL ekat edoCteeL tsetnoc", reverseWords("Let's take LeetCode contest"));// "s'teL ekat edoCteeL tsetnoc"
        assertEquals("doG gniD", reverseWords("God Ding"));// "doG gniD"
    }

    public String reverseWords(String s) {
        StringBuilder sb = new StringBuilder();
        return sb.toString();
    }
}


















/**
public String reverseWords(String s) {
    StringBuilder ret = new StringBuilder();
    int i = 0;
    while (i < s.length()) {
        int start = i;

        // 1.1 字符串不等于空的情况，反转字符串 (start + i) - (p + 1) --> [5, 4, 3, 2, 1]
        while (i < s.length() && s.charAt(i) != ' ')
            i++;

        for (int p = start; p < i; p++)
            ret.append(s.charAt((start + i) - (p + 1)));

        // 1.2 字符串等于空的情况，拼上 ' '
        while (i < s.length() && s.charAt(i) == ' ') {
            i++;
            ret.append(' ');
        }
    }
    return ret.toString();
}
*/