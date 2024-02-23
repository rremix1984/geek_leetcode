/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

/**
    [STRING]
    (简单)
    1455. 检查单词是否为句中其他单词的前缀
        给你一个字符串 sentence 作为句子并指定检索词为 searchWord ，其中句子由若干用 单个空格 分隔的单词组成。
        请你检查检索词 searchWord 是否为句子 sentence 中任意单词的前缀。
         1）如果 searchWord 是某一个单词的前缀，则返回句子 sentence 中该单词所对应的下标（下标从 1 开始）。
         2）如果 searchWord 是多个单词的前缀，则返回匹配的第一个单词的下标（最小下标）。
         3）如果 searchWord 不是任何单词的前缀，则返回 -1 。
        字符串 s 的 前缀 是 s 的任何前导连续子字符串。
    示例 1：
        输入：sentence = "i love eating burger", searchWord = "burg"
        输出：4
        解释："burg" 是 "burger" 的前缀，而 "burger" 是句子中第 4 个单词。
    示例 2：
        输入：sentence = "this problem is an easy problem", searchWord = "pro"
        输出：2
        解释："pro" 是 "problem" 的前缀，而 "problem" 是句子中第 2 个也是第 6 个单词，但是应该返回最小下标 2 。
    示例 3：
        输入：sentence = "i am tired", searchWord = "you"
        输出：-1
        解释："you" 不是句子中任何单词的前缀。
*/
public class NO1455_E_IsPrefixOfWord_x2 {

    @Test
    public void test() {
        assert 4 == isPrefixOfWord("i love eating burger","burg");
        assert 2 == isPrefixOfWord("this problem is an easy problem","pro");
        assert -1 == isPrefixOfWord("i am tired", "you");
    }

    public int isPrefixOfWord(String sentence, String searchWord) {
        return -1;
    }

}



















/**
// 方法1：
public int isPrefixOfWord(String sentence, String searchWord) {
    String[] words = sentence.split(" ");
    for (int i = 0; i < words.length; i++) {
        if (searchWord.length() > words[i].length())
            continue;

        for (int j = 0; j < searchWord.length(); j++) {
            if (searchWord.charAt(j) != words[i].charAt(j))
                break;

            if (j == searchWord.length() - 1)
                return i + 1;
        }
    }
    return -1;
}
*/