/**
 * copyright 2022/1/19
 */
package com.leetcode.undo;

import org.junit.Test;
import static com.leetcode.util.LogUtil.info;

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
public class NO557_ReverseWordsInAStringIII {

    @Test
    public void test () {
        info(reverseWords("Let's take LeetCode contest"));// "s'teL ekat edoCteeL tsetnoc"
        info(reverseWords("God Ding"));// "doG gniD"
    }

    public String reverseWords(String s) {
        StringBuilder ret = new StringBuilder();
        int length = s.length();
        int i = 0;
        while (i < length) {
            int start = i;
            while (i < length && s.charAt(i) != ' ')
                i++;

            for (int p = start; p < i; p++)
                ret.append(s.charAt(start + i - 1 - p));

            while (i < length && s.charAt(i) == ' ') {
                i++;
                ret.append(' ');
            }
        }
        return ret.toString();
    }
}


















/**
public String reverseWords(String s) {
    StringBuilder ret = new StringBuilder();
    int length = s.length();
    int i = 0;
    while (i < length) {
        int start = i;
        while (i < length && s.charAt(i) != ' ')
            i++;

        for (int p = start; p < i; p++)
            ret.append(s.charAt(start + i - 1 - p));

        while (i < length && s.charAt(i) == ' ') {
            i++;
            ret.append(' ');
        }
    }
    return ret.toString();
}
*/