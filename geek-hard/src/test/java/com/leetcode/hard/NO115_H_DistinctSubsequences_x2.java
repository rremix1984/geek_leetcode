/**
 * copyright 2022/1/19
 */
package com.leetcode.hard;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
    （困难）
    115. 不同的子序列
        给定一个字符串 s 和一个字符串 t ，计算在 s 的子序列中 t 出现的个数。
        字符串的一个 子序列 是指，通过删除一些（也可以不删除）字符且不干扰剩余字符相对位置所组成的新字符串。
        （例如，"ACE" 是 "ABCDE" 的一个子序列，而 "AEC" 不是）
        题目数据保证答案符合 32 位带符号整数范围。
    示例 1：
        输入：s = "rabbbit", t = "rabbit"
        输出：3
        解释：如下图所示, 有 3 种可以从 s 中得到 "rabbit" 的方案。
            ra[b]bbit -> rabbit
            rab[b]bit -> rabbit
            rabb[b]it -> rabbit
               r  a  b  b  i  t <null>
             ----------------------
          r  |[3] 3, 3, 3, 1, 1, 1
          a  | 0, 3, 3, 3, 1, 1, 1
          b  | 0, 0, 3, 3, 1, 1, 1
          b  | 0, 0, 1, 2, 1, 1, 1
          b  | 0, 0, 0, 1, 1, 1, 1
          i  | 0, 0, 0, 0, 1, 1, 1
          t  | 0, 0, 0, 0, 0, 1, 1
      <null> | 0, 0, 0, 0, 0, 0, 1

    示例 2：
        输入：s = "babgbag", t = "bag"
        输出：5
        解释：如下图所示, 有 5 种可以从 s 中得到 "bag" 的方案。
            ba[b]g[bag] -> bag
            ba[bgba]g   -> bag
            b[abgb]ag   -> bag
            [ba]b[gb]ag -> bag
            [babg]bag   -> bag

              b  a  g <null>
            -------------
          b |[5] 3  2  1
          a | 2  3  2  1
          b | 2  1  2  1
          g | 1  1  2  1
          b | 1  1  1  1
          a | 0  1  1  1
          g | 0  0  1  1
     <null> | 0  0  0  1
*/
public class NO115_H_DistinctSubsequences_x2 {

    @Test
    public void test() {
        assertEquals(3, numDistinct("rabbbit", "rabbit"));// 3
        assertEquals(5, numDistinct("babgbag", "bag"));// 5
    }

    public int numDistinct(String s, String t) {
        return 0;
    }
}



















/**
// 方法1：
public int numDistinct(String s, String t) {
    int m = s.length();
    int n = t.length();
    if (m < n)
        return 0;

    int[][] dp = new int[m + 1][n + 1];
    for (int i = 0; i <= m; i++)
        dp[i][n] = 1;

    for (int i = m - 1; i >= 0; i--) {
        for (int j = n - 1; j >= 0; j--) {
            if (s.charAt(i) == t.charAt(j))
                dp[i][j] = dp[i + 1][j + 1] + dp[i + 1][j];
            else
                dp[i][j] = dp[i + 1][j];
        }
    }
    return dp[0][0];
}
*/