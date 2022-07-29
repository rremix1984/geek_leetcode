/**
 * copyright 2022/1/19
 */
package com.leetcode;

import org.junit.Test;
import static com.leetcode.util.LogUtil.info;

/**
    （中等）
    647. 回文子串
        给你一个字符串 s ，请你统计并返回这个字符串中 回文子串
        的数目。回文字符串 是正着读和倒过来读一样的字符串。
        子字符串 是字符串中的由连续字符组成的一个序列。
        具有不同开始位置或结束位置的子串，即使是由相同的字符组成，
        也会被视作不同的子串。
    示例 1：
        输入：s = "abc"
        输出：3
        解释：三个回文子串: "a", "b", "c"
    示例 2：
        输入：s = "aaa"
        输出：6
        解释：6个回文子串: "a", "a", "a", "aa", "aa", "aaa"
*/
public class NO647_PalindromicSubstrings_x2 {

    @Test
    public void test() {
        info(countSubstrings("abc"));// 3
        info(countSubstrings("aaa"));// 6
    }

    public int countSubstrings(String s) {
        int ans = 0;
        return ans;
    }
}









/**
// 方法1：
public int countSubstrings(String s) {
    int n = s.length();
    int ans = 0;
    for (int i = 0; i < 2 * n - 1; i++) {
        int l = i / 2;
        int r = i / 2 + i % 2;
        while ( l >= 0 && r < n
            && s.charAt(l) == s.charAt(r) ) {
            --l;
            ++r;
            ++ans;
        }
    }
    return ans;
}
*/