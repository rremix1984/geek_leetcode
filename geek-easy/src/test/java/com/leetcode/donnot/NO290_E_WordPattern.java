/**
 * copyright 2022/1/19
 */
package com.leetcode.donnot;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
    (简单)
    290. 单词规律
        给定一种规律 pattern 和一个字符串 s ，判断 s 是否遵循相同的规律。
        这里的 遵循 指完全匹配，例如， pattern 里的每个字母和字符串 s 中的
        每个非空单词之间存在着双向连接的对应规律。
    示例1:
        输入: pattern = "abba", s = "dog cat cat dog"
        输出: true
    示例 2:
        输入:pattern = "abba", s = "dog cat cat fish"
        输出: false
    示例 3:
        输入: pattern = "aaaa", s = "dog cat cat dog"
        输出: false
    提示:
        1 <= pattern.length <= 300
        pattern 只包含小写英文字母
        1 <= s.length <= 3000
        s 只包含小写英文字母和 ' '
        s 不包含 任何前导或尾随对空格
        s 中每个单词都被 单个空格 分隔
*/
public class NO290_E_WordPattern {

    @Test
    public void test() {
        assert wordPattern(
                "abba","dog cat cat dog");
        assert !wordPattern(
                "abba","dog cat cat fish");
        assert !wordPattern(
                "aaaa","dog cat cat dog");
    }

    public boolean wordPattern(String pattern, String str) {
        Map<String, Character> s2c = new HashMap<>();
        Map<Character, String> c2s = new HashMap<>();
        int i = 0;
        for (char ch : pattern.toCharArray()) {
            if (i >= str.length())
                return false;

            int j = i;
            while (j < str.length() && str.charAt(j) != ' ')
                j++;

            String tmp = str.substring(i, j);
            if (s2c.containsKey(tmp) && s2c.get(tmp) != ch)
                return false;

            if (c2s.containsKey(ch) && !tmp.equals(c2s.get(ch)))
                return false;

            s2c.put(tmp, ch);
            c2s.put(ch, tmp);
            i = j + 1;
        }
        return i >= str.length();
    }

}
