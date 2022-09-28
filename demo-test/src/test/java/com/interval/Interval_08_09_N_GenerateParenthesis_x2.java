/**
 * copyright 2022/1/19
 */
package com.interval;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import static com.leetcode.util.MathUtils.getArray;

/**
    (中等)
    面试题 08.09. 括号
        设计一种算法，打印 n 对括号的所有合法的（例如，开闭一一对应）组合。
    说明：解集不能包含重复的子集。
    例如，给出 n = 3，生成结果为：
        ["((()))",
         "(()())",
         "(())()",
         "()(())",
         "()()()"]
*/
public class Interval_08_09_N_GenerateParenthesis_x2 {

    @Test
    public void test() {
        assert getArray("((()))","(()())","(())()","()(())","()()()").equals(generateParenthesis(3));
        assert getArray("((((()))))", "(((()())))", "(((())()))", "(((()))())", "(((())))()", "((()(())))",
                "((()()()))", "((()())())", "((()()))()", "((())(()))", "((())()())", "((())())()",
                "((()))(())", "((()))()()", "(()((())))", "(()(()()))", "(()(())())", "(()(()))()",
                "(()()(()))", "(()()()())", "(()()())()", "(()())(())", "(()())()()", "(())((()))",
                "(())(()())", "(())(())()", "(())()(())", "(())()()()", "()(((())))", "()((()()))",
                "()((())())", "()((()))()", "()(()(()))", "()(()()())", "()(()())()", "()(())(())",
                "()(())()()", "()()((()))", "()()(()())", "()()(())()", "()()()(())", "()()()()()").equals(generateParenthesis(5));
    }

    public List<String> generateParenthesis(int n) {
        List<String> res = new ArrayList<>();
        return res;
    }

}


















/**
// 方法1：
public List<String> generateParenthesis(int n) {
    List<String> arr = new ArrayList<>();
    call(arr, new char[2 * n], 0);
    return arr;
}

private void call(List<String> arr, char[] chars, int start) {
    if (start == chars.length) {
        if (!isValid(chars))
            return;
        arr.add(new String(chars));
        return;
    }

    chars[start] = '(';
    call(arr, chars, start + 1);

    chars[start] = ')';
    call(arr, chars, start + 1);
}

private boolean isValid(char[] chars) {
    int count = 0;
    for (char c : chars) {
        if (c == '(')
            count++;
        else
            count--;

        if (count == -1)
            return false;
    }
    return count == 0;
}
*/