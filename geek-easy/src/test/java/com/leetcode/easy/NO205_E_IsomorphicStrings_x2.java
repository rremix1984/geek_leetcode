/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;
import java.util.HashMap;
import java.util.Map;
import static com.leetcode.util.LogUtil.info;

/**
    [STRING]
    (简单)
    205. 同构字符串
        给定两个字符串 s 和 t ，判断它们是否是同构的。
        如果 s 中的字符可以按某种映射关系替换得到 t，那么这两个字符串是同构的。
        每个出现的字符都应当映射到另一个字符，同时不改变字符的顺序。不同字符不能映射到同一个字符上，
        相同字符只能映射到同一个字符上，字符可以映射到自己本身。
    示例 1:
        输入：s = "egg", t = "add"
        输出：true
    示例 2：
        输入：s = "foo", t = "bar"
        输出：false
    示例 3：
        输入：s = "paper", t = "title"
        输出：true
*/
@SuppressWarnings("all")
public class NO205_E_IsomorphicStrings_x2 {

    @Test
    public void test() {
        assert isIsomorphic("egg", "add");// true
        assert isIsomorphic("paper", "title");// true
        assert !isIsomorphic("foo", "bar");// false
    }

    public boolean isIsomorphic(String s, String t) {
        return true;
    }

}


















/**
// 方法1：
public boolean isIsomorphic(String s, String t) {
    for (int i = 0; i < s.length(); i++)
        if (s.indexOf(s.charAt(i)) != t.indexOf(t.charAt(i)))
            return false;

    return true;
}

// 方法2：
public boolean isIsomorphic(String s, String t) {
    // 定义一个Map
    Map<Character,Character> map = new HashMap<>();

    // 遍历 s
    for (int i = 0; i < s.length(); i++) {
        // 截取当前字符
        char b = s.charAt(i);
        char c = t.charAt(i);
        // 判断map中是否包含b
        if (map.containsKey(b)) {
            // 判断包含的b的value是否等于c
            // 不等于 return false
            if (map.get(b) != c)
                return false;
        } else {
            // 相同
            map.put(b, c);
        }
    }
    return true;
}
*/