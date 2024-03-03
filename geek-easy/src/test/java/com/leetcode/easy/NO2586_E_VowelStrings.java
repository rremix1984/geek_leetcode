package com.leetcode.easy;

import org.junit.Test;
import java.util.HashSet;
import java.util.Set;

/**
    [ARRAY]
    （简单）
    NO.2586 统计范围内的元音字符串数
    给你一个下标从 0 开始的字符串数组 words 和两个整数：left 和 right 。
    如果字符串以元音字母开头并以元音字母结尾，那么该字符串就是一个 元音字符串 ，其中元音字母是 'a'、'e'、'i'、'o'、'u' 。
    返回 words[i] 是元音字符串的数目，其中 i 在闭区间 [left, right] 内。

    示例 1：
        输入：words = ["are","amy","u"], left = 0, right = 2
        输出：2
        解释：
            - "are" 是一个元音字符串，因为它以 'a' 开头并以 'e' 结尾。
            - "amy" 不是元音字符串，因为它没有以元音字母结尾。
            - "u" 是一个元音字符串，因为它以 'u' 开头并以 'u' 结尾。
            在上述范围中的元音字符串数目为 2 。
    示例 2：
        输入：words = ["hey","aeo","mu","ooo","artro"], left = 1, right = 4
        输出：3
        解释：
            - "aeo" 是一个元音字符串，因为它以 'a' 开头并以 'o' 结尾。
            - "mu" 不是元音字符串，因为它没有以元音字母开头。
            - "ooo" 是一个元音字符串，因为它以 'o' 开头并以 'o' 结尾。
            - "artro" 是一个元音字符串，因为它以 'a' 开头并以 'o' 结尾。
            在上述范围中的元音字符串数目为 3 。
    提示：
        1 <= words.length <= 1000
        1 <= words[i].length <= 10
        words[i] 仅由小写英文字母组成
        0 <= left <= right < words.length
    Related Topics:
        数组,字符串
*/
public class NO2586_E_VowelStrings {

    @Test
    public void test() {
        assert 2 == vowelStrings(
            new String[]{"are", "amy", "u"}, 0, 2);
    }

    private final static Set<Character> yySet = new HashSet<>();

    static {
        yySet.add('a');
        yySet.add('e');
        yySet.add('i');
        yySet.add('o');
        yySet.add('u');
    }

    public int vowelStrings(String[] words, int left, int right) {
        int length = words.length;
        int res = 0;
        for (int i = 0; i < length; i++)
            if (valid(words[i]) && i >= left && i <= right)
                res++;

        return res;
    }

    private boolean valid(String word) {
        return yySet.contains(word.charAt(0))
            && yySet.contains(word.charAt(word.length() - 1));
    }

}
