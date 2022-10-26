/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
    (简单)
    389. 找不同
        给定两个字符串 s 和 t ，它们只包含小写字母。
        字符串 t 由字符串 s 随机重排，然后在随机位置添加一个字母。
        请找出在 t 中被添加的字母。
    示例 1：
        输入：s = "abcd", t = "abcde"
        输出："e"
        解释：'e' 是那个被添加的字母。
    示例 2：
        输入：s = "", t = "y"
        输出："y"
    提示：
        0 <= s.length <= 1000
        t.length == s.length + 1
        s 和 t 只包含小写字母
*/
public class NO389_E_FindTheDifference_x2 {

    @Test
    public void test() {
        assertEquals('e', findTheDifference( "abcd","abcde"));
        assertEquals('y', findTheDifference("","y"));
    }

    public char findTheDifference(String s, String t) {
        return ' ';
    }

}
















/**
// 方法1：
public char findTheDifference(String s, String t) {
    int[] cnt = new int[26];
    for (int i = 0; i < s.length(); i++)
        cnt[s.charAt(i) - 'a']++;

    for (int i = 0; i < t.length(); i++)
        if (--cnt[t.charAt(i) - 'a'] < 0)
            return t.charAt(i);

    return ' ';
}
*/