/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

import static com.leetcode.util.LogUtil.info;

/**
    （简单）
    242. 有效的字母异位词
    给定两个字符串 s 和 t ，编写一个函数来判断 t 是否是 s 的字母异位词。
    注意：若 s 和 t 中每个字符出现的次数都相同，则称 s 和 t 互为字母异位词。
    示例 1:
        输入: s = "anagram", t = "nagaram"
        输出: true
    示例 2:
        输入: s = "rat", t = "car"
        输出: false
*/
public class NO242_ValidAnagram_x3 {

    @Test
    public void test() {
        info(isAnagram("anagram", "nagaram"));
    }

    public boolean isAnagram(String s, String t) {
        return true;
    }
}













/**
public boolean isAnagram(String s, String t) {
    if (s.length() != t.length()) {
        return false;
    }

    int[] counter = new int[26];
    for (int i = 0; i < s.length(); i++) {
        counter[s.charAt(i) - 'a']++;
        counter[t.charAt(i) - 'a']--;
    }

    for (int c : counter) {
        if (c != 0) {
            return false;
        }
    }
    return true;
}
*/