/**
 * copyright 2022/1/19
 */
package com.leetcode.normal;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
    （中等）
    5. 最长回文子串
        给你一个字符串 s，找到 s 中最长的回文子串。
    示例 1：
        输入：s = "babad"
        输出："bab"
        解释："aba" 同样是符合题意的答案。
    示例 2：
        输入：s = "cbbd"
        输出："bb"
*/
public class NO005_N_LongestPalindromicSubstring_x2 {

    @Test
    public void test() {
        assertEquals("aba", longestPalindrome("babad"));// bab 或者 aba 都行
        assertEquals("bb", longestPalindrome("cbbd"));// bb
    }

    public String longestPalindrome(String s) {
        String res = "";

        return res;
    }
}

















/**
// 方法1：选中中心，向两边扩散
public String longestPalindrome(String s) {
    if (s == null || s.length() < 1)
        return "";

    int start = 0, end = 0;
    for (int i = 0; i < s.length(); i++) {
        int len1 = expandAroundCenter(s, i, i);
        int len2 = expandAroundCenter(s, i, i + 1);
        int len = Math.max(len1, len2);
        if (len > end - start) {
            start = i - (len - 1) / 2;
            end = i + len / 2;
        }
    }
    return s.substring(start, end + 1);
}

public int expandAroundCenter(String s, int left, int right) {
    while (left >= 0 && right < s.length()
        && s.charAt(left) == s.charAt(right)) {
        --left;
        ++right;
    }
    return right - left - 1;
}

// 方法2： 动态规划
public String longestPalindrome(String s) {
    if (s == null || s.length() < 2) {
        return s;
    }
    int strLen = s.length();
    int maxStart = 0;  //最长回文串的起点
    int maxEnd = 0;    //最长回文串的终点
    int maxLen = 1;  //最长回文串的长度

    boolean[][] dp = new boolean[strLen][strLen];

    for (int r = 1; r < strLen; r++) {
        for (int l = 0; l < r; l++) {
            if (s.charAt(l) == s.charAt(r) && (r - l <= 2 || dp[l + 1][r - 1])) {
                dp[l][r] = true;
                if (r - l + 1 > maxLen) {
                    maxLen = r - l + 1;
                    maxStart = l;
                    maxEnd = r;

                }
            }

        }

    }
    return s.substring(maxStart, maxEnd + 1);
}

// 方法3：
public String longestPalindrome(String s) {
    int n = s.length();
    String res = "";

    // dp方程的含义：从 i 到 j 这个子串是一个回文字符串
    boolean[][] dp = new boolean[n][n];
    for (int i = n - 1; i >= 0; i--) {
        for (int j = i; j < n; j++) {
            // 一般状态转移方程长度为 0 或者 1 （即： j - i < 2 ）都是回文串
            dp[i][j] = s.charAt(i) == s.charAt(j)
                    &&
                    (j - i < 2 || dp[i + 1][j - 1]);

            // 如果找到了回文字符串，就记录到结果 res 中
            // 如果有更长的 回文字符串 j - i + 1 > res.length()
            // 就直接截取出结果 res
            if (dp[i][j] && j - i + 1 > res.length())
                res = s.substring(i, j + 1);
        }
    }
    return res;
}
*/