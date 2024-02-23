/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

/**
    [STRING]
    (简单)
    1961. 检查字符串是否为数组前缀
        给你一个字符串 s 和一个字符串数组 words ，请你判断 s 是否为 words 的 前缀字符串 。
        字符串 s 要成为 words 的 前缀字符串 ，需要满足：s 可以由 words 中的前 k（k为正数）个字符串按顺序相连得到，且 k 不超过 words.length 。
        如果 s 是 words 的 前缀字符串 ，返回 true ；否则，返回 false 。
    示例 1：
        输入：s = "iloveleetcode", words = {"i", "love", "leetcode", "apples"}
        输出：true
        解释：
        s 可以由 "i"、"love" 和 "leetcode" 相连得到。
    示例 2：
        输入：s = "iloveleetcode", words = {"apples", "i", "love", "leetcode"}
        输出：false
        解释：数组的前缀相连无法得到 s 。
*/
public class NO1961_E_IsPrefixString_x2 {

    @Test
    public void test() {
        assert isPrefixString("iloveleetcode",
                new String[]{"i", "love", "leetcode", "apples"});
        assert !isPrefixString("iloveleetcode",
                new String[]{"apples", "i", "love", "leetcode"});
    }

    public boolean isPrefixString(String s, String[] words) {
        return false;
    }

}














/**
public boolean isPrefixString(String s, String[] words) {
    StringBuilder builder = new StringBuilder();
    for (String str : words) {
        builder.append(str);
        if (s.equals(builder.toString()))
            return true;
    }
    return false;
}
*/