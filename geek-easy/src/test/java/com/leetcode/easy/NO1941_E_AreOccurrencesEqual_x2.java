/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

/**
    [STRING]
    (简单)
    1941. 检查是否所有字符出现次数相同
        给你一个字符串 s ，如果 s 是一个 好 字符串，请你返回 true ，否则请返回 false 。
        如果 s 中出现过的 所有 字符的出现次数 相同 ，那么我们称字符串 s 是 好 字符串。
    示例 1：
        输入：s = "abacbc"
        输出：true
        解释：s 中出现过的字符为 'a'，'b' 和 'c' 。s 中所有字符均出现 2 次。
    示例 2：
        输入：s = "aaabb"
        输出：false
        解释：s 中出现过的字符为 'a' 和 'b' 。
             'a' 出现了 3 次，'b' 出现了 2 次，两者出现次数不同。
*/
public class NO1941_E_AreOccurrencesEqual_x2 {

    @Test
    public void test() {
        assert areOccurrencesEqual("abacbc");
        assert !areOccurrencesEqual("aaabb");
    }

    public boolean areOccurrencesEqual(String s) {
        return false;
    }

}

















/**
public boolean areOccurrencesEqual(String s) {
    int[] c1 = new int[26];
    for (char c : s.toCharArray())
        c1[c - 'a']++;

    int left = 0;
    int right = 25;
    while (left < right)
        if (c1[left] == 0)
            left++;
        else if (c1[right] == 0)
            right--;
        else
        if (c1[left] == c1[right])
            left++;
        else
            return false;
    return true;
}
*/