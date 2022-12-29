package com.leetcode.undo;

import org.junit.Test;

import java.util.ArrayDeque;
import java.util.Queue;

/**
    (中等)
    792. 匹配子序列的单词数
        给定字符串 s 和字符串数组 words, 返回  words[i] 中是s的子序列的单词个数 。
        字符串的 子序列 是从原始字符串中生成的新字符串，可以从中删去一些字符(可以是none)，而不改变其余字符的相对顺序。
        例如， “ace” 是 “abcde” 的子序列。
    示例 1:
        输入: s = "abcde", words = ["a","bb","acd","ace"]
        输出: 3
        解释: 有三个是 s 的子序列的单词: "a", "acd", "ace"。
    Example 2:
        输入: s = "dsahjpjauf", words = ["ahjpjau","ja","ahbwzgqnuk","tnmlanowax"]
        输出: 2
    提示:
        1 <= s.length <= 5 * 104
        1 <= words.length <= 5000
        1 <= words[i].length <= 50
        words[i]和 s 都只由小写字母组成。
*/
@SuppressWarnings("all")
public class NO792_N_NumMatchingSubseq {

    @Test
    public void test() {
        assert 3 == numMatchingSubseq("abcde",
                new String[]{"a","bb","acd","ace"});
        assert 2 == numMatchingSubseq("dsahjpjauf",
                new String[]{"ahjpjau","ja","ahbwzgqnuk","tnmlanowax"});
    }

    public int numMatchingSubseq(String s, String[] words) {
        Queue<int[]>[] p = new Queue[26];
        for (int i = 0; i < 26; ++i)
            p[i] = new ArrayDeque<>();

        for (int i = 0; i < words.length; ++i)
            p[words[i].charAt(0) - 'a'].offer(new int[]{i, 0});

        int res = 0;
        for (int i = 0; i < s.length(); ++i) {
            char c = s.charAt(i);
            int len = p[c - 'a'].size();
            while (len > 0) {
                int[] t = p[c - 'a'].poll();
                if (t[1] == words[t[0]].length() - 1) {
                    res++;
                } else {
                    t[1]++;
                    p[words[t[0]].charAt(t[1]) - 'a'].offer(t);
                }
                len--;
            }
        }
        return res;
    }

}
