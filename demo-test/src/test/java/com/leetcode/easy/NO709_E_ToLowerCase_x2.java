/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;
import static com.leetcode.util.LogUtil.info;
import static org.junit.Assert.assertEquals;

/**
    （简单）
    709. 转换成小写字母
        给你一个字符串 s ，将该字符串中的大写字母转换成相同的小写字母，返回新的字符串。
    示例 1：
        输入：s = "Hello"
        输出："hello"
    示例 2：
        输入：s = "here"
        输出："here"
    示例 3：
        输入：s = "LOVELY"
        输出："lovely"
*/
public class NO709_E_ToLowerCase_x2 {

    @Test
    public void test() {
        assertEquals("hello", toLowerCase("Hello"));// hello
        assertEquals("here", toLowerCase("here"));// here
        assertEquals("lovely", toLowerCase("LOVELY"));// lovely
    }

    public String toLowerCase(String s) {
        StringBuilder sb = new StringBuilder();
        return sb.toString();
    }

}

















/**
// 方法1：
public String toLowerCase(String s) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < s.length(); i++) {
        char c = s.charAt(i);
        // 如果是大写字母的话
        if (c >= 'A' && c <= 'Z')
            sb.append((char)('a' + c - 'A'));
        else
            sb.append(c);
    }
    return sb.toString();
}
*/