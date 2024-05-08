package com.leetcode.todo;

import org.junit.Test;

/**
    [ARRAY]
    （简单）
    NO.3042 统计前后缀下标对 I
    给你一个下标从0开始的字符串数组words。
    定义一个布尔函数isPrefixAndSuffix，它接受两个字符串参数str1和str2：
    当str1同时是str2的前缀（prefix）和后缀（suffix）时，isPrefixAndSuffix(str1,str2) 返回true，否则返回false。
    例如，isPrefixAndSuffix("aba", "ababa") 返回 true，因为"aba"既是"ababa"的前缀，
    也是"ababa”的后缀，但是isPrefixAndSuffix("abc","abcd")返回false。
    以整数形式，返回满足 i<j 且 isPrefixAndSuffix(words[i], words[j])
    为true的下标对(i, j)的数量。
    示例 1：
        输入：words = ["a","aba","ababa","aa"]
        输出：4
        解释：在本示例中，计数的下标对包括：
        i = 0 且 j = 1 ，因为 isPrefixAndSuffix("a", "aba") 为 true 。
        i = 0 且 j = 2 ，因为 isPrefixAndSuffix("a", "ababa") 为 true 。
        i = 0 且 j = 3 ，因为 isPrefixAndSuffix("a", "aa") 为 true 。
        i = 1 且 j = 2 ，因为 isPrefixAndSuffix("aba", "ababa") 为 true 。
        因此，答案是 4 。
    示例 2：
        输入：words = ["pa","papa","ma","mama"]
        输出：2
        解释：在本示例中，计数的下标对包括：
        i = 0 且 j = 1 ，因为 isPrefixAndSuffix("pa", "papa") 为 true 。
        i = 2 且 j = 3 ，因为 isPrefixAndSuffix("ma", "mama") 为 true 。
        因此，答案是 2 。
    示例 3：
        输入：words = ["abab","ab"]
        输出：0
        解释：在本示例中，唯一有效的下标对是 i = 0 且 j = 1 ，但是 isPrefixAndSuffix("abab", "ab") 为 false 。
        因此，答案是 0 。
    提示：
        1 <= words.length <= 50
        1 <= words[i].length <= 10
        words[i] 仅由小写英文字母组成。
    Related Topics:字典树,数组,字符串,字符串匹配,哈希函数,滚动哈希
*/
@SuppressWarnings("all")
public class NO3042_E_CountPrefixSuffixPairs {

    @Test
    public void test() {
        assert 4 == countPrefixSuffixPairs(new String[]{"a","aba","ababa","aa"});
        assert 2 == countPrefixSuffixPairs(new String[]{"pa","papa","ma","mama"});
        assert 0 == countPrefixSuffixPairs(new String[]{"abab","ab"});
    }

    public int countPrefixSuffixPairs(String[] words) {
        int res = 0;

        return res;
    }

}




















/*
// 方法1：
public int countPrefixSuffixPairs(String[] words) {
    int res = 0;
    for (int i = 0; i < words.length; i++) {
        for (int j = i + 1; j < words.length; j++) {
            if (isPrefixAndSuffix(words[i], words[j])) res++;
        }
    }
    return res;
}

public boolean isPrefixAndSuffix(String str1, String str2) {
    // 这里判断即是前缀也是后缀
    // 最快就是数组
    if (str1.length() > str2.length())
        return false;

    if (!str2.endsWith(str1))
        return false;

    return str2.startsWith(str1);
}
*/