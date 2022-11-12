/**
 * copyright 2022/1/19
 */
package com.leetcode.undo;

import org.junit.Test;

/**
    (中等)
    97. 交错字符串
        给定三个字符串s1、s2、s3，请你帮忙验证s3是否是由s1和s2交错组成的。
        两个字符串s和t交错的定义与过程如下，其中每个字符串都会被分割成若干非空子字符串：
            s = s1 + s2 + ... + sn
            t = t1 + t2 + ... + tm
            |n - m| <= 1
        交错 是 s1 + t1 + s2 + t2 + s3 + t3 + ...
           或者 t1 + s1 + t2 + s2 + t3 + s3 + ...
        注意：a + b 意味着字符串 a 和 b 连接。
    示例 1：
        输入：s1 = "aabcc", s2 = "dbbca", s3 = "aadbbcbcac"
        输出：true
    示例 2：
        输入：s1 = "aabcc", s2 = "dbbca", s3 = "aadbbbaccc"
        输出：false
    示例 3：
        输入：s1 = "", s2 = "", s3 = ""
        输出：true
    提示：
        0 <= s1.length, s2.length <= 100
        0 <= s3.length <= 200
        s1、s2、和 s3 都由小写英文字母组成
*/
public class NO097_N_IsInterleave {

    @Test
    public void test() {
        assert isInterleave("aabcc", "dbbca", "aadbbcbcac");
        assert !isInterleave("aabcc", "dbbca", "aadbbbaccc");
        assert isInterleave("", "", "");
    }

    public boolean isInterleave(String s1, String s2, String s3) {
        int m = s1.length();
        int n = s2.length();
        if (s3.length() != m + n)
            return false;

        // 动态规划，dp[i,j]表示s1前i字符能与s2前j字符组成s3前i+j个字符；
        boolean[][] dp = new boolean[m + 1][n + 1];
        dp[0][0] = true;

        for (int i = 1; i <= m && s1.charAt(i-1) == s3.charAt(i-1); i++)
            dp[i][0] = true; // 不相符直接终止

        for (int j = 1; j <= n && s2.charAt(j-1) == s3.charAt(j-1); j++)
            dp[0][j] = true; // 不相符直接终止

        for (int i = 1; i <= m; i++)
            for (int j = 1; j <= n; j++) {
                dp[i][j] = (dp[i - 1][j] && s3.charAt(i + j - 1) == s1.charAt(i - 1))
                        || (dp[i][j - 1] && s3.charAt(i + j - 1) == s2.charAt(j - 1));
            }

        return dp[m][n];
    }

}
