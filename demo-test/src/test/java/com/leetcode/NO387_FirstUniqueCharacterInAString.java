/**
 * copyright 2022/1/19
 */
package com.leetcode;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static com.leetcode.util.LogUtil.info;

/**
    387. 字符串中的第一个唯一字符
        给定一个字符串 s ，找到 它的第一个不重复的字符，并返回它的索引 。如果不存在，则返回 -1 。
    示例 1：
        输入: s = "leetcode"
        输出: 0
    示例 2:
        输入: s = "loveleetcode"
        输出: 2
    示例 3:
        输入: s = "aabb"
        输出: -1
*/
public class NO387_FirstUniqueCharacterInAString {

    @Test
    public void test() {
        info(firstUniqChar("leetcode"));// 0
        info(firstUniqChar("loveleetcode"));// 2
        info(firstUniqChar("aabb"));// -1
    }

    public int firstUniqChar(String s) {
        Map<Character, Integer> frequency = new HashMap<>();
        for (int i = 0; i < s.length(); ++i) {
            char ch = s.charAt(i);
            frequency.put(ch, frequency.getOrDefault(ch, 0) + 1);
        }
        for (int i = 0; i < s.length(); ++i)
            if (frequency.get(s.charAt(i)) == 1)
                return i;

        return -1;
    }

}


















/**
public int firstUniqChar(String s) {
    Map<Character, Integer> frequency = new HashMap<>();
    for (int i = 0; i < s.length(); ++i) {
        char ch = s.charAt(i);
        frequency.put(ch, frequency.getOrDefault(ch, 0) + 1);
    }
    for (int i = 0; i < s.length(); ++i)
        if (frequency.get(s.charAt(i)) == 1)
            return i;

    return -1;
}
*/