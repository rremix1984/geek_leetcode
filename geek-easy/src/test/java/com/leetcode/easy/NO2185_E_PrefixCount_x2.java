/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

/**
    [STRING]
    [ARRAY]
    (简单)
    2185. 统计包含给定前缀的字符串
        给你一个字符串数组 words 和一个字符串 pref 。
        返回 words 中以 pref 作为 前缀 的字符串的数目。
        字符串 s 的 前缀 就是  s 的任一前导连续字符串。
    示例 1：
        输入：words = {"pay", "attention", "practice", "attend"},  pref = "at"
        输出：2
        解释：以 "at" 作为前缀的字符串有两个，分别是："attention" 和 "attend" 。
    示例 2：
        输入：words = {"leetcode", "win", "loops", "success"},  pref = "code"
        输出：0
        解释：不存在以 "code" 作为前缀的字符串。
*/
public class NO2185_E_PrefixCount_x2 {

    @Test
    public void test() {
        assert 2 == prefixCount(new String[]{"pay", "attention", "practice", "attend"},"at");
        assert 0 == prefixCount(new String[]{"leetcode", "win", "loops", "success"},"code");
    }

    public int prefixCount(String[] words, String pref) {
        int ans = 0;
        for (String word : words)
            if (word.startsWith(pref))
                ans++;

        return ans;
    }

}
