/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

/**
    (简单)
    1957. 删除字符使字符串变好
        一个字符串如果没有 三个连续 相同字符，那么它就是一个 好字符串 。
        给你一个字符串 s ，请你从 s 删除 最少 的字符，使它变成一个 好字符串 。
        请你返回删除后的字符串。题目数据保证答案总是 唯一的 。
    示例 1：
        输入：s = "leeetcode"
        输出："leetcode"
        解释：从第一组 'e' 里面删除一个 'e' ，得到 "leetcode" 。
             没有连续三个相同字符，所以返回 "leetcode" 。
    示例 2：
        输入：s = "aaabaaaa"
        输出："aabaa"
        解释：从第一组 'a' 里面删除一个 'a' ，得到 "aabaaaa" 。
             从第二组 'a' 里面删除两个 'a' ，得到 "aabaa" 。
             没有连续三个相同字符，所以返回 "aabaa" 。
    示例 3：
        输入：s = "aab"
        输出："aab"
        解释：没有连续三个相同字符，所以返回 "aab" 。
*/
public class NO1957_E_MakeFancyString_x2 {

    @Test
    public void test() {
        assert "leetcode".equals(makeFancyString("leeetcode"));
        assert "aabaa".equals(makeFancyString("aaabaaaa"));
        assert "aab".equals(makeFancyString("aab"));
    }

    public String makeFancyString(String s) {
        return s;
    }

}















/**
public String makeFancyString(String s) {
    if (s.length() <= 2)
        return s;

    char[] res = s.toCharArray();
    int tail = 2;
    for (int i = tail; i < res.length; i++)
        if (res[i] != res[tail - 1] || res[i] != res[tail - 2])
            res[tail++] = res[i];

    return new String(res).substring(0, tail);
}
*/