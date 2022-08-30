/**
 * copyright 2022/1/19
 */
package com.leetcode.normal;

import org.junit.Test;
import static com.leetcode.util.LogUtil.info;

/**
    （中等）
    1143. 最长公共子序列
          给定两个字符串 text1 和 text2，返回这两个字符串的最长
        公共子序列的长度。如果不存在公共子序列 ，返回 0 。
          一个字符串的 子序列 是指这样一个新的字符串：它是由原字符
        串在【不改变字符的相对顺序】的情况下删除某些字符（也可以不删
        除任何字符）后组成的新字符串。例如，"ace" 是 "abcde" 的子
        序列，但 "aec" 不是 "abcde" 的子序列。两个字符串的公共子
        序列是这两个字符串所共同拥有的子序列。
    示例 1：
        输入：text1 = "abcde", text2 = "ace"
        输出：3
        解释：最长公共子序列是 "ace" ，它的长度为 3 。
    示例 2：
        输入：text1 = "abc", text2 = "abc"
        输出：3
        解释：最长公共子序列是 "abc" ，它的长度为 3 。
    示例 3：
        输入：text1 = "abc", text2 = "def"
        输出：0
        解释：两个字符串没有公共子序列，返回 0 。
*/
@SuppressWarnings("all")
public class NO1143_LongestCommonSubsequence_x2 {

    @Test
    public void test() {
//        info(longestCommonSubsequence("abc","abc"));// 3
        info(longestCommonSubsequence("abcde", "ace"));// 3
//        info(longestCommonSubsequence("abc", "def"));// 0
    }

    public int longestCommonSubsequence(String s1, String s2) {
        int m = s1.length();
        int n = s2.length();
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 1; i <= m; i++)
            for (int j = 1; j <= n; j++)
                if (s1.charAt(i - 1) == s2.charAt(j - 1))
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                else
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
        return dp[m][n];
    }

}

















/**
// 方法1：动态规划（dp）
public int longestCommonSubsequence(String s1, String s2) {
    int m = s1.length();
    int n = s2.length();
    int[][] dp = new int[m + 1][n + 1];
    for (int i = 1; i <= m; i++)
        for (int j = 1; j <= n; j++)
            if (s1.charAt(i - 1) == s2.charAt(j - 1))
                dp[i][j] = dp[i - 1][j - 1] + 1;
            else
                dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
    return dp[m][n];
}
*/