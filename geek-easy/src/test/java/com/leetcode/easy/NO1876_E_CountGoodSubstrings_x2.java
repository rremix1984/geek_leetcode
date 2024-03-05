/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

/**
    [STRING]
    (简单)
    1876. 长度为三且各字符不同的子字符串
        如果一个字符串不含有任何重复字符，我们称这个字符串为 好 字符串。
        给你一个字符串 s ，请你返回 s 中长度为 3 的 好子字符串 的数量。
        注意，如果相同的好子字符串出现多次，每一次都应该被记入答案之中。
        子字符串 是一个字符串中连续的字符序列。
    示例 1：
        输入：s = "xyzzaz"
        输出：1
        解释：总共有 4 个长度为 3 的子字符串："xyz"，"yzz"，"zza" 和 "zaz" 。
             唯一的长度为 3 的好子字符串是 "xyz" 。
    示例 2：
        输入：s = "aababcabc"
        输出：4
        解释：总共有 7 个长度为 3 的子字符串："aab"，"aba"，"bab"，"abc"，"bca"，"cab" 和 "abc" 。
             好子字符串包括 "abc"，"bca"，"cab" 和 "abc" 。
*/
public class NO1876_E_CountGoodSubstrings_x2 {

    @Test
    public void test() {
        assert 1 == countGoodSubstrings("xyzzaz");
        assert 4 == countGoodSubstrings("aababcabc");
    }

    public int countGoodSubstrings(String s) {
        int res = 0;
        return res;
    }

}
















/**
public int countGoodSubstrings(String s) {
    int res = 0;
    for (int i = 0; i < s.length() - 2; i++)
        if (s.charAt(i) != s.charAt(i + 1)
            && s.charAt(i) != s.charAt(i + 2)
            && s.charAt(i + 1) != s.charAt(i + 2))
            res++;

    return res;
}
*/