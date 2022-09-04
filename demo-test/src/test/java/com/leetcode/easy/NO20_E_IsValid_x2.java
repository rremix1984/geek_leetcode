/**
copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

import static com.leetcode.util.LogUtil.info;

/**
    （简单）
    20. 有效的括号
        给定一个只包括 '('，')'，'{'，'}'，'['，']'的字符串 s ，判断字符串是否有效。
        有效字符串需满足：
        左括号必须用相同类型的右括号闭合。
        左括号必须以正确的顺序闭合。

    示例 1：
        输入：s = "()"
        输出：true
    示例2：
        输入：s = "()[]{}"
        输出：true
 */
public class NO20_E_IsValid_x2 {

    @Test
    public void test(){
        info(isValid("()"));// true
        info(isValid("()[]{}"));// true
        info(isValid("({ } )"));
    }

    public boolean isValid(String s) {
        return false;
    }
}
















/**
// 方法一
public boolean isValid(String s) {
    Stack<Character> stack = new Stack<>();
    char[] chars = s.toCharArray();
    for (int i = 0; i < chars.length; i++) {
        Character c = chars[i];
        if (c == '(' || c == '[' || c == '{') {
            stack.push(c);
        } else {
            if (c == ' ') continue;
            if (stack.empty()) return false;
            if (stack.peek() != '(' && c == ')') return false;
            if (stack.peek() != '[' && c == ']') return false;
            if (stack.peek() != '{' && c == '}') return false;
            stack.pop();
        }
    }
    return stack.empty();
}

// 方法二
public boolean isValid(String s) {
     Stack<Character> stack = new Stack<>();
     for (char c : s.toCharArray()) {
         if (c == '(')
             stack.push(')');
         else if (c == '[')
             stack.push(']');
         else if (c == '{')
             stack.push('}');
         else if (stack.isEmpty() || stack.pop() != c)
             return false;
     }
     return stack.empty();
}
*/