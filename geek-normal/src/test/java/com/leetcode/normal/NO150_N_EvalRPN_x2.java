/**
 * copyright 2022/1/19
 */
package com.leetcode.normal;

import org.junit.Test;
import java.util.Deque;
import java.util.LinkedList;
import static java.lang.Integer.parseInt;

/**
    (中等)
    150. 逆波兰表达式求值
        根据 逆波兰表示法，求表达式的值。
        有效的算符包括 +、-、*、/ 。每个运算对象可以是整数，也可以是另一个逆波兰表达式。
        注意 两个整数之间的除法只保留整数部分。
        可以保证给定的逆波兰表达式总是有效的。换句话说，
        表达式总会得出有效数值且不存在除数为 0 的情况。
    示例 1：
        输入：tokens = ["2","1","+","3","*"]
        输出：9
        解释：该算式转化为常见的中缀算术表达式为：((2 + 1) * 3) = 9
    示例 2：
        输入：tokens = ["4","13","5","/","+"]
        输出：6
        解释：该算式转化为常见的中缀算术表达式为：(4 + (13 / 5)) = 6
    示例 3：
        输入：tokens = ["10","6","9","3","+","-11","*","/","*","17","+","5","+"]
        输出：22
        解释：该算式转化为常见的中缀算术表达式为：
        ((10 * (6 / ((9 + 3) * -11))) + 17) + 5
        = ((10 * (6 / (12 * -11))) + 17) + 5
        = ((10 * (6 / -132)) + 17) + 5
        = ((10 * 0) + 17) + 5
        = (0 + 17) + 5
        = 17 + 5
        = 22
    提示：
        1 <= tokens.length <= 104
        tokens[i] 是一个算符（"+"、"-"、"*" 或 "/"），或是在范围 [-200, 200] 内的一个整数
*/
public class NO150_N_EvalRPN_x2 {

    @Test
    public void test() {
        assert 9 == evalRPN(
                new String[]{"2","1","+","3","*"});
        assert 6 == evalRPN(
                new String[]{"4","13","5","/","+"});
        assert 22 == evalRPN(
                new String[]{"10","6","9","3","+","-11","*","/","*","17","+","5","+"});
    }

    public int evalRPN(String[] tokens) {
        Deque<Integer> stack = new LinkedList<>();
        for (String token : tokens) {
            if (isNumber(token)) {
                stack.push(parseInt(token));
                continue;
            }
            int num2 = stack.pop();
            int num1 = stack.pop();
            switch (token) {
                case "+":
                    stack.push(num1 + num2);
                    break;
                case "-":
                    stack.push(num1 - num2);
                    break;
                case "*":
                    stack.push(num1 * num2);
                    break;
                case "/":
                    stack.push(num1 / num2);
                    break;
                default:
            }
        }
        return stack.pop();
    }

    public boolean isNumber(String token) {
        return !("+".equals(token) || "-".equals(token) || "*".equals(token) || "/".equals(token));
    }

}




















/**
// 方法1：
public int evalRPN(String[] tokens) {
    Deque<Integer> stack = new LinkedList<>();
    for (String token : tokens) {
        if (isNumber(token)) {
            stack.push(parseInt(token));
            continue;
        }
        int num2 = stack.pop();
        int num1 = stack.pop();
        switch (token) {
            case "+":
                stack.push(num1 + num2);
                break;
            case "-":
                stack.push(num1 - num2);
                break;
            case "*":
                stack.push(num1 * num2);
                break;
            case "/":
                stack.push(num1 / num2);
                break;
            default:
        }
    }
    return stack.pop();
}

public boolean isNumber(String token) {
    return !("+".equals(token) || "-".equals(token)
          || "*".equals(token) || "/".equals(token));
}
*/