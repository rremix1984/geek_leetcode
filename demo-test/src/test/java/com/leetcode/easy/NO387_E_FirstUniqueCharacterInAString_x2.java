/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

import static com.leetcode.util.LogUtil.info;

/**
    （简单）
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
public class NO387_E_FirstUniqueCharacterInAString_x2 {

    @Test
    public void test() {
        info(firstUniqChar("leetcode"));// 0
        info(firstUniqChar("loveleetcode"));// 2
        info(firstUniqChar("aabb"));// -1
    }

    public int firstUniqChar(String s) {
        return -1;
    }

}


















/**
// 方法1：
public int firstUniqChar(String s) {
    Map<Character, Integer> map = new HashMap<>();
    for (char ch : s.toCharArray())
        map.put(ch, map.getOrDefault(ch, 0) + 1);

    for (int i = 0; i < s.length(); i++)
        if (map.get(s.charAt(i)) == 1)
            return i;

    return -1;
}

// 方法2：
public int firstUniqChar(String s) {
    int[] freq = new int[26];
    char[] chars = s.toCharArray();
    for (char ch : chars) {
        freq[ch - 'a']++;
    }
    for (int i = 0; i < chars.length; i++) {
        if (freq[chars[i] - 'a'] == 1) {
            return i;
        }
    }
    return -1;
}

// 方法3：
public int firstUniqChar(String s) {
    for (int i = 0; i < s.length(); i++) {
        char ch = s.charAt(i);
        if (s.indexOf(ch) == s.lastIndexOf(ch))
            return i;
    }
    return -1;
}

// 方法4：
public int firstUniqChar(String s) {
    boolean[] notUniq = new boolean[26];
    for (int i = 0; i < s.length(); i++) {
        char ch = s.charAt(i);
        if (!notUniq[ch -'a']) {
            if (s.indexOf(ch) == s.lastIndexOf(ch)) {
                return i;
            } else {
                notUniq[ch - 'a'] = true;
            }
        }
    }
    return -1;
}

// 方法5：
public int firstUniqChar(String s) {
    int res = -1;
    for (char ch = 'a'; ch <= 'z'; ch++) {
        int index = s.indexOf(ch);
        if (index != -1 && index == s.lastIndexOf(ch))
            res = (res == -1 || res > index) ? index : res;
    }
    return res;
}
*/