/**
 * copyright 2022/1/19
 */
package com.leetcode;

import org.junit.Test;
import java.util.LinkedList;
import static com.leetcode.util.LogUtil.info;

/**
    (中等)
    394. 字符串解码
        给定一个经过编码的字符串，返回它解码后的字符串。
        编码规则为: k[encoded_string]，表示其中方括号内部的 encoded_string 正好重复 k 次。注意 k 保证为正整数。
        你可以认为输入字符串总是有效的；输入字符串中没有额外的空格，且输入的方括号总是符合格式要求的。
        此外，你可以认为原始数据不包含数字，所有的数字只表示重复的次数 k ，例如不会出现像 3a 或 2[4] 的输入。
    示例 1：
        输入：s = "3[a]2[bc]"
        输出："aaabcbc"
    示例 2：
        输入：s = "3[a2[c]]"
        输出："accaccacc"
    示例 3：
        输入：s = "2[abc]3[cd]ef"
        输出："abcabccdcdcdef"
    示例 4：
        输入：s = "abc3[cd]xyz"
        输出："abccdcdcdxyz"
*/
public class NO394_N_DecodeString {

    @Test
    public void test() {
        info(decodeString("3[a]2[bc]"));// "aaabcbc"
        info(decodeString("3[a2[c]]"));// "accaccacc"
        info(decodeString("2[abc]3[cd]ef"));// "abcabccdcdcdef"
        info(decodeString("abc3[cd]xyz"));// "abccdcdcdxyz"
    }

    public String decodeString(String s) {
        StringBuilder res = new StringBuilder();
        // 数字计数器，用于累计大于9的数字
        int multi = 0;
        LinkedList<Integer> stack_multi = new LinkedList<>();
        LinkedList<String> stack_res = new LinkedList<>();
        // 一共4种情况，[、]、1-9的数字、字母
        for(Character c : s.toCharArray()) {
            // 第 1 种场景，遇到左括号 "["
            if(c == '[') {
                stack_multi.addLast(multi);
                stack_res.addLast(res.toString());
                multi = 0;
                res.setLength(0);
            // 第 2 种场景，遇到右括号 "]"
            // 代表着 1-9[xxx] 这样的一个正则匹配满足了结算条件
            // 先把计数器 cur_multi 取出来，重复 [ 与 ] 中间的所有字符串 multi 次
            // 把结果放入 res 中
            } else if(c == ']') {
                StringBuilder tmp = new StringBuilder();
                int cur_multi = stack_multi.removeLast();
                for(int i = 0; i < cur_multi; i++)
                    tmp.append(res);
                res = new StringBuilder(stack_res.removeLast() + tmp);
            // 遇到数字,需要对multi变量做累加
            } else if(c >= '0' && c <= '9') {
                multi = multi * 10 + Integer.parseInt(c + "");
            // 遇到字母，直接放到结果集中
            } else {
                res.append(c);
            }
        }
        return res.toString();
    }

}