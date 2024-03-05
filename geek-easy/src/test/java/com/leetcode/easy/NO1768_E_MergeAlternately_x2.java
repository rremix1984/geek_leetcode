/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

import static com.leetcode.util.LogUtil.info;

/**
    [STRING]
    (简单)
    1768. 交替合并字符串
        给你两个字符串 word1 和 word2 。请你从 word1 开始，通过交替添加字母来合并字符串。
        如果一个字符串比另一个字符串长，就将多出来的字母追加到合并后字符串的末尾。
        返回 合并后的字符串 。
    示例 1：
        输入：word1 = "abc", word2 = "pqr"
        输出："apbqcr"
        解释：字符串合并情况如下所示：
        word1：  a   b   c
        word2：    p   q   r
        合并后：  a p b q c r
    示例 2：
        输入：word1 = "ab", word2 = "pqrs"
        输出："apbqrs"
        解释：注意，word2 比 word1 长，"rs" 需要追加到合并后字符串的末尾。
        word1：  a   b
        word2：    p   q   r   s
        合并后：  a p b q   r   s
    示例 3：
        输入：word1 = "abcd", word2 = "pq"
        输出："apbqcd"
        解释：注意，word1 比 word2 长，"cd" 需要追加到合并后字符串的末尾。
        word1：  a   b   c   d
        word2：    p   q
        合并后：  a p b q c   d
*/
public class NO1768_E_MergeAlternately_x2 {

    @Test
    public void test() {
        assert "apbqcr".equals(
                mergeAlternately("abc", "pqr"));
        assert "apbqrs".equals(
                mergeAlternately("ab", "pqrs"));
        assert "apbqcd".equals(
                mergeAlternately("abcd", "pq"));
    }

    public String mergeAlternately(String word1, String word2) {
        StringBuilder res = new StringBuilder();
        int i = 0;
        while (i < word1.length() && i < word2.length()) {
            res.append(word1.charAt(i));
            res.append(word2.charAt(i));
            i++;
        }

        if (i < word1.length())
            return res.append(word1.substring(i)).toString();

        return res.append(word2.substring(i)).toString();
    }

}
















/**
public String mergeAlternately(String word1, String word2) {
    StringBuilder res = new StringBuilder();
    int i = 0;
    while (i < word1.length() && i < word2.length()) {
        res.append(word1.charAt(i));
        res.append(word2.charAt(i));
        i++;
    }

    if (i < word1.length())
        res.append(word1.substring(i));
    else
        res.append(word2.substring(i));

    return res.toString();
}
*/