/**
 * copyright 2022/1/19
 */
package com.leetcode;

import org.junit.Test;
import java.util.Stack;
import static com.leetcode.util.LogUtil.info;
import static java.lang.Math.max;

/**
    （困难）
    32. 最长有效括号
        给你一个只包含 '(' 和 ')' 的字符串，找出最长有效（格式正确且连续）括号子串的长度。
    示例 1：
        输入：s = "(()"
        输出：2
        解释：最长有效括号子串是 "()"
    示例 2：
        输入：s = ")()())"
        输出：4
        解释：最长有效括号子串是 "()()"
    示例 3：
        输入：s = ""
        输出：0
*/
public class NO32_LongestValidParentheses_x2 {

    @Test
    public void test() {
        info(longestValidParentheses("(()"));// 2
        info(longestValidParentheses(")()())"));// 4
        info(longestValidParentheses(""));// 0
        info(longestValidParentheses("()"));// 2
    }

    public int longestValidParentheses(String s) {
        int max = 0;
        Stack<Integer> stack = new Stack<>();
        stack.push(-1);

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                stack.push(i);
            } else if (stack.size() > 1 && s.charAt(stack.peek()) == '(' ) {
                stack.pop();
                max = Math.max(max, i - stack.peek());
            } else {
                stack.push(i);
            }
        }
        return max;
    }

}













/**
// 方法1：dp动态规划
public int longestValidParentheses(String s) {
    int maxLen = 0;
    int[] dp = new int[s.length()];
    for (int i = 1; i < s.length(); i++) {
        if (s.charAt(i) == ')') {
            if (s.charAt(i - 1) == '(')
                dp[i] = (i >= 2 ? dp[i - 2] : 0) + 2;
            else if (i - dp[i - 1] > 0 && s.charAt(i - dp[i - 1] - 1) == '(')
                dp[i] = dp[i - 1] + ((i - dp[i - 1]) >= 2 ? dp[i - dp[i - 1] - 2] : 0) + 2;
            maxLen = Math.max(maxLen, dp[i]);
        }
    }
    return maxLen;
}

// 方法2：层序遍历
public int longestValidParentheses(String s) {
    Stack<Integer> stack = new Stack<>();
    stack.push(-1);
    int max = 0;

    for (int i = 0; i < s.length(); i++)
        // 所有 '(' 一定先入
        if (s.charAt(i) == '(') {
            stack.push(i);
        // 排除上面的条件那就一定是 ')' 了，就直接带走第一个 '('
        } else if (stack.size() > 1 && s.charAt(stack.peek()) == '(') {
            stack.pop();
            max = Math.max(max, i - stack.peek());
        // 没有对应的 '(' 了
        } else {
            stack.push(i);
        }
    return max;
}
*/