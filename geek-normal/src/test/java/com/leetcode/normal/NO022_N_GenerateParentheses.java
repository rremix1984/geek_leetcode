/**
 * copyright 2022/1/19
 */
package com.leetcode.normal;

import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static com.leetcode.util.MathUtils.getArray;

/**
    [ARRAY] ||||
    (中等)
    22. 括号生成
        数字 n 代表生成括号的对数，请你设计一个函数，用于能够生成所有可能的并且 有效的 括号组合。
    示例 1：
        输入：n = 3
        输出：["((()))", "(()())", "(())()", "()(())", "()()()"]
    示例 2：
        输入：n = 1
        输出：["()"]
*/
public class NO022_N_GenerateParentheses {

    @Test
    public void test() {
        assert getArray("((()))", "(()())", "(())()", "()(())","()()()")
                .equals(generateParenthesis(3));
        assert getArray("()").equals(
                generateParenthesis(1));
    }

    public List<String> generateParenthesis(int n) {
        // 2024/3/15 NO.2 没做出来...
        // 2024/3/17 NO.3 有点印象，但是还没做出来
        // 2024/3/18-19 NO.4-5 一遍过
        List<String> res = new ArrayList<>();
        return res;
    }

}




















/*
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


// 方法2：递归法
public List<String> generateParenthesis(int n) {
    List<String> res = new ArrayList<>();
    generate(res, n, n, "");
    return res;
}

private void generate(List<String> res, int left, int right, String cur) {
    if (left == 0 && right == 0) {
        res.add(cur);
        return;
    }

    if (left > 0) {
        generate(res, left - 1, right, cur + "(");
    }

    if (right > left) {
        generate(res, left, right - 1, cur + ")");
    }
}
*/