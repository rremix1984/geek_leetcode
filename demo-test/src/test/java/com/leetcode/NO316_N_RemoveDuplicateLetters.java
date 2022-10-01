/**
 * copyright 2022/1/19
 */
package com.leetcode;

import org.junit.Test;
import java.util.Stack;

/**
    (中等)
    316.去除重复字母
        给你一个字符串 s ，请你去除字符串中重复的字母，使得每个字母只出现一次。
        需保证 返回结果的字典序最小（要求不能打乱其他字符的相对位置）。
     示例 1：
         输入：s = "bcabc"
         输出："abc"
     示例 2：
         输入：s = "cbacdcbc"
         输出："acdb"
*/
public class NO316_N_RemoveDuplicateLetters {

    @Test
    public void test() {
        assert "abc".equals(removeDuplicateLetters("bcabc"));
        assert "acdb".equals(removeDuplicateLetters("cbacdcbc"));
    }

    // 方法1：单调栈
    public String removeDuplicateLetters(String s) {
        Stack<Character> stack = new Stack<>();

        // 维护一个计数器记录字符串中字符的数量
        // 因为输入为 ASCII 字符，大小 256 够用了
        int[] count = new int[256];
        for (int i = 0; i < s.length(); i++)
            count[s.charAt(i)]++;

        boolean[] vstd = new boolean[256];
        for (char ch : s.toCharArray()) {
            // 每遍历过一个字符，都将对应的计数减一
            count[ch]--;

            if (vstd[ch])
                continue;

            while (!stack.isEmpty() && stack.peek() > ch) {
                // 若之后不存在栈顶元素了，则停止 pop
                if (count[stack.peek()] == 0)
                    break;

                // 若之后还有，则可以 pop
                vstd[stack.pop()] = false;
            }
            stack.push(ch);
            vstd[ch] = true;
        }

        StringBuilder sb = new StringBuilder();
        while (!stack.empty())
            sb.append(stack.pop());

        return sb.reverse().toString();
    }

}





















/**
// 方法1：单调栈
public String removeDuplicateLetters(String s) {
    Stack<Character> stack = new Stack<>();

    // 维护一个计数器记录字符串中字符的数量
    // 因为输入为 ASCII 字符，大小 256 够用了
    int[] count = new int[256];
    for (int i = 0; i < s.length(); i++)
        count[s.charAt(i)]++;

    boolean[] inStack = new boolean[256];
    for (char ch : s.toCharArray()) {
        // 每遍历过一个字符，都将对应的计数减一
        count[ch]--;

        if (inStack[ch])
            continue;

        while (!stack.isEmpty() && stack.peek() > ch) {
            // 若之后不存在栈顶元素了，则停止 pop
            if (count[stack.peek()] == 0)
                break;

            // 若之后还有，则可以 pop
            inStack[stack.pop()] = false;
        }
        stack.push(ch);
        inStack[ch] = true;
    }

    StringBuilder sb = new StringBuilder();
    while (!stack.empty())
        sb.append(stack.pop());

    return sb.reverse().toString();
}
*/