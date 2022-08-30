/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;
import static com.leetcode.util.LogUtil.info;

/**
    （简单）
    58. 最后一个单词的长度
        给你一个字符串 s，由若干单词组成，单词前后用一些空格字符隔开。返回字符串中 最后一个 单词的长度。
        单词 是指仅由字母组成、不包含任何空格字符的最大子字符串。
    示例 1：
        输入：s = "Hello World"
        输出：5
        解释：最后一个单词是“World”，长度为5。
    示例 2：
        输入：s = "   fly me   to   the moon  "
        输出：4
        解释：最后一个单词是“moon”，长度为4。
    示例 3：
        输入：s = "luffy is still joyboy"
        输出：6
        解释：最后一个单词是长度为6的“joyboy”。
*/
public class NO58_LengthOfLastWord_x2 {

    @Test
    public void test() {
        info(lengthOfLastWord("Hello World"));// 5
        info(lengthOfLastWord("   fly me   to   the moon  "));// 4
        info(lengthOfLastWord("luffy is still joyboy"));// 6
        info(lengthOfLastWord("a"));// 1
    }

    public int lengthOfLastWord(String s) {
        return -1;
    }
}

















/**
public int lengthOfLastWord(String s) {
    int index = s.length() - 1;
    while (s.charAt(index) == ' ')
        index--;

    int wordLength = 0;
    while (index >= 0 && s.charAt(index) != ' ') {
        wordLength++;
        index--;
    }
    return wordLength;
}
*/