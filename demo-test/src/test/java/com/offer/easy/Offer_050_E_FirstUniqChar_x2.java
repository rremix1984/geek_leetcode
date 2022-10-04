/**
 * copyright 2022/1/19
 */
package com.offer.easy;

import org.junit.Test;

import static com.leetcode.util.LogUtil.info;
import static org.junit.Assert.assertEquals;

/**
    (简单)
    剑指 Offer 50. 第一个只出现一次的字符
        在字符串 s 中找出第一个只出现一次的字符。如果没有，返回一个单空格。 s 只包含小写字母。
    示例 1:
        输入：s = "abaccdeff"
        输出：'b'
    示例 2:
        输入：s = ""
        输出：' '
*/
public class Offer_050_E_FirstUniqChar_x2 {

    @Test
    public void test() {
        assertEquals('b', firstUniqChar("abaccdeff"));
        assertEquals(' ', firstUniqChar(""));
        assertEquals('l', firstUniqChar("leetcode"));
    }

    public char firstUniqChar(String s) {
        return ' ';
    }

}

















/**
// 方法1：
public char firstUniqChar(String s) {
    Map<Character, Boolean> dic = new HashMap<>();

    // 第一次是true，以后每次都是false
    for (char c : s.toCharArray())
        // 你没有？现在你有了
        dic.put(c, !dic.containsKey(c));

    for (char c : s.toCharArray())
        if (dic.get(c))
            return c;

    // 如果没有，返回一个单空格
    return ' ';
}

// 方法2：用数组代替字典表
public char firstUniqChar(String s) {
    int[] arr = new int[26];
    for (int i = 0; i < s.length(); i++)
        if (arr[s.charAt(i) - 'a'] == 0)
            arr[s.charAt(i) - 'a'] = 1;
        else
            arr[s.charAt(i) - 'a'] = 2;

    for (int i = 0; i < s.length(); i++)
        if (arr[s.charAt(i) - 'a'] == 1)
            return s.charAt(i);

    return ' ';
}
*/