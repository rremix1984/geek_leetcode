/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

/**
    [STRING]
    (简单)
    1624. 两个相同字符之间的最长子字符串
        给你一个字符串 s，请你返回 两个相同字符之间的最长子字符串的长度 ，计算长度时不含这两个字符。如果不存在这样的子字符串，返回 -1 。
        子字符串 是字符串中的一个连续字符序列。
    示例 1：
        输入：s = "aa"
        输出：0
        解释：最优的子字符串是两个 'a' 之间的空子字符串。
    示例 2：
        输入：s = "abca"
        输出：2
        解释：最优的子字符串是 "bc" 。
    示例 3：
        输入：s = "cbzxy"
        输出：-1
        解释：s 中不存在出现出现两次的字符，所以返回 -1 。
    示例 4：
        输入：s = "cabbac"
        输出：4
        解释：最优的子字符串是 "abba" ，其他的非最优解包括 "bb" 和 "" 。
*/
public class NO1624_E_MaxLengthBetweenEqualCharacters_x2 {

    @Test
    public void test() {
        assert  0 == maxLengthBetweenEqualCharacters("aa");
        assert  2 == maxLengthBetweenEqualCharacters("abca");
        assert -1 == maxLengthBetweenEqualCharacters("cbzxy");
        assert  4 == maxLengthBetweenEqualCharacters("cabbac");
    }

    public int maxLengthBetweenEqualCharacters(String s) {
        int res = -1;
        return res;
    }

}

















/**
public int maxLengthBetweenEqualCharacters(String s) {
    int res = -1;
    int[] firstIndex = new int[26];
    Arrays.fill(firstIndex, MIN_VALUE);
    for (int i = 0; i < s.length(); i++) {
        int idx = s.charAt(i) - 'a';
        if (firstIndex[idx] < 0)
            firstIndex[idx] = i;
        else
            res = max(res, i - firstIndex[idx] - 1);
    }
    return res;
}
*/