/**
 * copyright 2022/1/19
 */
package com.leetcode;

import org.junit.Test;

/**
    (简单)
    856. 括号的分数
        给定一个平衡括号字符串 S，按下述规则计算该字符串的分数：
        () 得 1 分。
        AB 得 A + B 分，其中 A 和 B 是平衡括号字符串。
        (A) 得 2 * A 分，其中 A 是平衡括号字符串。
    示例 1：
        输入： "()"
        输出： 1
    示例 2：
        输入： "(())"
        输出： 2
    示例 3：
        输入： "()()"
        输出： 2
    示例 4：
        输入： "(()(()))"
        输出： 6
*/
public class NO856_N_ScoreOfParentheses {

    @Test
    public void test() {
        assert 1 == scoreOfParentheses("()");
        assert 2 == scoreOfParentheses("(())");
        assert 2 == scoreOfParentheses("()()");
        assert 6 == scoreOfParentheses("(()(()))");
    }

    public int scoreOfParentheses(String s) {
        if (s.length() == 2)
            return 1;

        int bal = 0;
        int len = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(')
                bal++;
            else
                bal--;

            if (bal == 0) {
                len = i + 1;
                break;
            }
        }

        // 如果已经到头了，就返回 2 倍的结果
        if (len == s.length())
            return 2 * scoreOfParentheses(s.substring(1, s.length() - 1));

        // 分成两部分计算，[0, len) 和 [len, 正无穷)
        return scoreOfParentheses(s.substring(0, len)) + scoreOfParentheses(s.substring(len));
    }

}












/**
// 方法1：递归法
public int scoreOfParentheses(String s) {
    if (s.length() == 2)
        return 1;

    int bal = 0;
    int len = 0;
    for (int i = 0; i < s.length(); i++) {
        if (s.charAt(i) == '(')
            bal++;
        else
            bal--;

        if (bal == 0) {
            len = i + 1;
            break;
        }
    }
    if (len == s.length())
        return 2 * scoreOfParentheses(s.substring(1, s.length() - 1));

    return scoreOfParentheses(s.substring(0, len)) + scoreOfParentheses(s.substring(len));
}
*/