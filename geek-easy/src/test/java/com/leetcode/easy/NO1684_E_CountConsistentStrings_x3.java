/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

/**
    (简单)
    1684. 统计一致字符串的数目
        给你一个由不同字符组成的字符串 allowed 和一个字符串数组 words 。
        如果一个字符串的每一个字符都在 allowed 中，就称这个字符串是 一致字符串 。
        请你返回 words 数组中 一致字符串 的数目。
    示例 1：
        输入：allowed = "ab",  words = {"ad", "bd", "aaab", "baa", "badab"}
        输出：2
        解释：字符串 "aaab" 和 "baa" 都是一致字符串，因为它们只包含字符 'a' 和 'b' 。
    示例 2：
        输入：allowed = "abc",  words = {"a", "b", "c", "ab", "ac", "bc", "abc"}
        输出：7
        解释：所有字符串都是一致的。
    示例 3：
        输入：allowed = "cad",  words = {"cc", "acd", "b", "ba", "bac", "bad", "ac", "d"}
        输出：4
        解释：字符串 "cc"，"acd"，"ac" 和 "d" 是一致字符串。
*/
public class NO1684_E_CountConsistentStrings_x3 {

    @Test
    public void test() {
        assert 2 == countConsistentStrings("ab", new String[]{"ad", "bd", "aaab", "baa", "badab"});
        assert 7 == countConsistentStrings("abc", new String[]{"a", "b", "c", "ab", "ac", "bc", "abc"});
        assert 4 == countConsistentStrings("cad", new String[]{"cc","acd","b","ba","bac","bad","ac","d"});
    }

    public int countConsistentStrings(String allowed, String[] words) {
        int ans = 0;
        return ans;
    }

}














/**
public int countConsistentStrings(String allowed, String[] words) {
    int ans = 0;
    for (String word : words) {
        ans++;
        for (char c : word.toCharArray())
            if (allowed.indexOf(c) == -1) {
                ans--;
                break;
            }
    }
    return ans;
}
*/