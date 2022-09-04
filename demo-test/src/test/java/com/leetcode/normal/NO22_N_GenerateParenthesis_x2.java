/**
 * copyright 2022/1/19
 */
package com.leetcode.normal;

import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static com.leetcode.util.LogUtil.info;

/**
    （中等）
    22. 括号生成
    数字 n 代表生成括号的对数，请你设计一个函数，用于能够生成所有可能的并且 有效的 括号组合。
    示例 1：
        输入：n = 3
        输出：["((()))", "(()())", "(())()", "()(())", "()()()"]
    示例 2：
        输入：n = 1
        输出：["()"]
*/
public class NO22_N_GenerateParenthesis_x2 {

    @Test
    public void test() {
        info(generateParenthesis(3));
    }

    List<String> res = new ArrayList<>();

    public List<String> generateParenthesis(int n) {
        return res;
    }

}












/**
// 方法1 递归法
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
