/**
 * copyright 2022/1/19
 */
package com.offer.easy;

import org.junit.Test;
import java.util.Deque;
import java.util.LinkedList;
import static com.leetcode.util.LogUtil.info;
import static java.lang.String.join;
import static org.junit.Assert.assertEquals;

/**
    (简单)
    剑指 Offer 58 - I. 翻转单词顺序
        输入一个英文句子，翻转句子中单词的顺序，但单词内字符的顺序不变。为简单起见，标点符号和普通字母一样处理。例如输入字符串"I am a student. "，则输出"student. a am I"。
    示例 1：
        输入: "the sky is blue"
        输出: "blue is sky the"
    示例 2：
        输入: "  hello world!  "
        输出: "world! hello"
        解释: 输入字符串可以在前面或者后面包含多余的空格，但是反转后的字符不能包括。
    示例 3：
        输入: "a good   example"
        输出: "example good a"
        解释: 如果两个单词间有多余的空格，将反转后单词间的空格减少到只含一个。
*/
public class Offer_058_I_E_ReverseWords_x2 {

    @Test
    public void test() {
        assertEquals("blue is sky the", reverseWords("the sky is blue"));
        assertEquals("world! hello", reverseWords("  hello world!  "));
        assertEquals("example good a", reverseWords("a good   example"));
    }

    public String reverseWords(String s) {
        Deque<String> queue = new LinkedList<>();
        return String.join(" ", queue);
    }

}

















/**
// 方法1：双指针法
public String reverseWords(String s) {
    int l = 0;
    int r = s.length() - 1;

    // 去掉字符串开头的空白字符
    while (l <= r && s.charAt(l) == ' ')
        l++;

    // 去掉字符串末尾的空白字符
    while (l <= r && s.charAt(r) == ' ')
        r--;

    Deque<String> queue = new LinkedList<>();
    StringBuilder word = new StringBuilder();
    while (l <= r) {
        if (s.charAt(l) != ' ') {
            word.append(s.charAt(l));
        } else if (word.length() != 0) {
            queue.push(word.toString());
            word.setLength(0);
        }
        l++;
    }
    queue.push(word.toString());
    return join(" ", queue);
}
*/