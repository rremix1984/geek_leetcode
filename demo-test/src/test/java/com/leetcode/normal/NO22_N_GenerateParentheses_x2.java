/**
 * copyright 2022/1/19
 */
package com.leetcode.normal;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
    (中等)
    22. 括号生成
        数字 n 代表生成括号的对数，请你设计一个函数，用于能够生成所有可能的并且 有效的 括号组合。
    示例 1：
        输入：n = 3
        输出：["((()))","(()())","(())()","()(())","()()()"]
    示例 2：
        输入：n = 1
        输出：["()"]
*/
public class NO22_N_GenerateParentheses_x2 {

    @Test
    public void test() {
        assert new ArrayList<String>(){{add("((()))");add("(()())");add("(())()");
                                        add("()(())");add("()()()");}}.equals(
                generateParenthesis(3));
        assert new ArrayList<String>(){{add("()");}}.equals(
                generateParenthesis(1));
    }

    public List<String> generateParenthesis(int n) {
        List<String> res = new ArrayList<>();
        return res;
    }

}




















/**
// 方法1：
public List<String> generateParenthesis(int n) {
    List<String> res = new ArrayList<>();
    call(res, new StringBuilder(), 0, 0, n);
    return res;
}

private void call(List<String> res, StringBuilder cur, int left, int right, int max) {
    if (cur.length() == max * 2) {
        res.add(cur.toString());
        return;
    }

    if (left < max) {
        cur.append("(");
        call(res, cur, left + 1, right, max);
        cur.deleteCharAt(cur.length() - 1);
    }

    if (right < left) {
        cur.append(")");
        call(res, cur, left, right + 1, max);
        cur.deleteCharAt(cur.length() - 1);
    }
}
*/