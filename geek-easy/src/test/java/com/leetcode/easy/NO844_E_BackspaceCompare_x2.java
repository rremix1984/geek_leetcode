/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

/**
    (简单)
    844. 比较含退格的字符串
        给定 s 和 t 两个字符串，当它们分别被输入到空白的文本编辑器后，
        如果两者相等，返回 true 。# 代表退格字符。
        注意：如果对空文本输入退格字符，文本继续为空。
    示例 1：
        输入：s = "ab#c", t = "ad#c"
        输出：true
        解释：s 和 t 都会变成 "ac"。
    示例 2：
        输入：s = "ab##", t = "c#d#"
        输出：true
        解释：s 和 t 都会变成 ""。
    示例 3：
        输入：s = "a#c", t = "b"
        输出：false
        解释：s 会变成 "c"，但 t 仍然是 "b"。
    提示：
        1 <= s.length, t.length <= 200
        s 和 t 只含有小写字母以及字符 '#'
*/
public class NO844_E_BackspaceCompare_x2 {

    @Test
    public void test() {
        assert backspaceCompare("ab#c","ad#c");
        assert backspaceCompare("ab##","c#d#");
        assert !backspaceCompare("a#c","b");
    }

    public boolean backspaceCompare(String s, String t) {
        return call(s).equals(call(t));
    }

    private String call(String t) {
        StringBuilder sb = new StringBuilder();
        return sb.toString();
    }

}

















/**
// 方法1：
public boolean backspaceCompare(String s, String t) {
    return build(s).equals(build(t));
}

public String build(String str) {
    StringBuilder ret = new StringBuilder();
    for (char ch : str.toCharArray())
        if (ch != '#')
            ret.append(ch);
        else if (ret.length() > 0)
            ret.deleteCharAt(ret.length() - 1);

    return ret.toString();
}
*/
