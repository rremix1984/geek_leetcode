/**
 * copyright 2022/1/19
 */
package com.dp;

import org.junit.Test;
import java.util.*;
import static com.leetcode.util.MathUtils.getArray;

/**
    [ARRAY]
    [STACK]
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
public class NO022_N_GenerateParentheses_x2 {

    @Test
    public void test() {
        assert getArray("((()))", "(()())", "(())()", "()(())", "()()()").containsAll(
                generateParenthesis(3));
        assert generateParenthesis(3).containsAll(
                getArray("((()))", "(()())", "(())()", "()(())", "()()()"));
        assert getArray("()").equals(
                generateParenthesis(1));
    }

    // 8ms
    public List<String> generateParenthesis(int n) {
        Map<Integer, List<String>> mem = new HashMap<>();
        return dp(n, mem);
    }

    private List<String> dp(int n, Map<Integer, List<String>> mem) {
        if (mem.containsKey(n))
            return mem.get(n);

        List<String> res = new LinkedList<>();
        if (n == 0) {
            res.add("");
            return res;
        }

        for (int i = 0; i < n; i++) {
            int j = n - i - 1;
            List<String> iRes = dp(i, mem);
            List<String> jRes = dp(j, mem);
            for (String s1 : iRes)
                for (String s2 : jRes)
                    res.add(s1 + "(" + s2 + ")");
        }
        mem.put(n, res);
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
List<String> res = new ArrayList<>();
public List<String> generateParenthesis(int n) {
    generate(n, n,"");
    return res;
}
private void generate(int left, int right, String cur) {
    if (left == 0 && right == 0) {
        res.add(cur);
        return;
    }

    if (left > 0) {
        generate(left - 1, right, cur + "(");
    }

    if (right > left) {
        generate(left, right - 1, cur + ")");
    }
}
*/