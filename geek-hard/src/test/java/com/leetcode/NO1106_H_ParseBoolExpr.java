/**
 * copyright 2022/1/19
 */
package com.leetcode;

import org.junit.Test;

import java.util.Deque;
import java.util.LinkedList;

/**
    (困难)
    1106. 解析布尔表达式
        给你一个以字符串形式表述的 布尔表达式（boolean） expression，返回该式的运算结果。
        有效的表达式需遵循以下约定：
        "t"，运算结果为 True
        "f"，运算结果为 False
        "!(expr)"，运算过程为对内部表达式 expr 进行逻辑 非的运算（NOT）
        "&(expr1,expr2,...)"，运算过程为对 2 个或以上内部表达式 expr1, expr2, ... 进行逻辑 与的运算（AND）
        "|(expr1,expr2,...)"，运算过程为对 2 个或以上内部表达式 expr1, expr2, ... 进行逻辑 或的运算（OR）
    示例 1：
        输入：expression = "!(f)"
        输出：true
    示例 2：
        输入：expression = "|(f,t)"
        输出：true
    示例 3：
        输入：expression = "&(t,f)"
        输出：false
    示例 4：
        输入：expression = "|(&(t,f,t),!(t))"
        输出：false
    提示：
        1 <= expression.length <= 20000
        expression[i] 由 {'(', ')', '&', '|', '!', 't', 'f', ','} 中的字符组成。
        expression 是以上述形式给出的有效表达式，表示一个布尔值。
*/
public class NO1106_H_ParseBoolExpr {

    @Test
    public void test() {
        assert parseBoolExpr("!(f)");
        assert parseBoolExpr("|(f,t)");
        assert !parseBoolExpr("&(t,f)");
        assert !parseBoolExpr("|(&(t,f,t),!(t))");
    }

    public boolean parseBoolExpr(String expression) {
        Deque<Character> stack = new LinkedList<>();
        for (int i = 0; i < expression.length();i++) {
            char exp = expression.charAt(i);
            if (exp != ',') {
                if (exp != ')') {
                    stack.push(exp);
                } else {
                    boolean flagTrue = false;
                    boolean flagFalse = false;
                    while (!stack.isEmpty()) {
                        Character pop = stack.pop();
                        if (pop == 't') {
                            flagTrue = true;
                        } else if (pop == 'f') {
                            flagFalse = true;
                        } else if (pop == '!' || pop == '|' || pop == '&') {
                            if (pop == '!') {
                                stack.push(flagFalse ? 't' : 'f');
                            } else if (pop == '|') {
                                stack.push(flagTrue ? 't' : 'f');
                            }else {
                                stack.push(flagFalse ? 'f' : 't');
                            }
                            break;
                        }
                    }
                }
            }

        }
        return stack.peek() == 't';
    }

}
