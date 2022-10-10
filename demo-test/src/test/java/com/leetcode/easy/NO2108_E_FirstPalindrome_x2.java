/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

/**
    (简单)
    2108. 找出数组中的第一个回文字符串
        给你一个字符串数组 words ，找出并返回数组中的 第一个回文字符串 。如果不存在满足要求的字符串，返回一个 空字符串 "" 。
        回文字符串 的定义为：如果一个字符串正着读和反着读一样，那么该字符串就是一个 回文字符串 。
    示例 1：
        输入：words = {"abc", "car", "ada", "racecar", "cool"}
        输出："ada"
        解释：第一个回文字符串是 "ada" 。
             注意，"racecar" 也是回文字符串，但它不是第一个。
    示例 2：
        输入：words = {"notapalindrome", "racecar"}
        输出："racecar"
        解释：第一个也是唯一一个回文字符串是 "racecar" 。
    示例 3：
        输入：words = {"def", "ghi"}
        输出：""
        解释：不存在回文字符串，所以返回一个空字符串。
*/
public class NO2108_E_FirstPalindrome_x2 {

    @Test
    public void test() {
        assert "ada".equals(firstPalindrome(new String[]{"abc", "car", "ada", "racecar", "cool"}));
        assert "racecar".equals(firstPalindrome(new String[]{"notapalindrome", "racecar"}));
        assert "".equals(firstPalindrome(new String[]{"def", "ghi"}));
    }

    public String firstPalindrome(String[] words) {
        for (String word : words)
            if (isRevert(word))
                return word;

        return "";
    }

    private boolean isRevert(String word) {
        int i = 0;
        int j = word.length() - 1;
        while (i < j) {
            if (word.charAt(i) == word.charAt(j)) {
                i++;
                j--;
            } else {
                return false;
            }
        }
        return true;
    }

}
