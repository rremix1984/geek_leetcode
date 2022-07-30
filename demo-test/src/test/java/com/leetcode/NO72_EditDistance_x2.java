/**
 * copyright 2022/1/19
 */
package com.leetcode;

import org.junit.Test;
import static com.leetcode.util.LogUtil.info;
import static com.leetcode.util.MathUtils.mins;

/**
    （困难）
    72. 编辑距离
        给你两个单词 word1 和 word2， 请返回将 word1
        转换成 word2 所使用的最少操作数。你可以对一个单
        词进行如下三种操作：
            1. 插入一个字符
            2. 删除一个字符
            3. 替换一个字符
    示例 1：
        输入：word1 = "horse", word2 = "ros"
        输出：3
        解释：
            horse -> rorse (将 'h' 替换为 'r')
            rorse -> rose (删除 'r')
            rose -> ros (删除 'e')
    示例 2：
        输入：word1 = "intention", word2 = "execution"
        输出：5
        解释：
            intention -> inention (删除 't')
            inention -> enention (将 'i' 替换为 'e')
            enention -> exention (将 'n' 替换为 'x')
            exention -> exection (将 'n' 替换为 'c')
            exection -> execution (插入 'u')
*/
public class NO72_EditDistance_x2 {

    @Test
    public void test() {
        info(minDistance("horse", "ros")); // 3
        info(minDistance("intention", "execution")); // 5
        info(minDistance("oppa", "apple"));// 3
    }

    public int minDistance(String word1, String word2) {
        return -1;
    }
}











/**
// 方法1：dp动态规划
public int minDistance(String word1, String word2) {
    int n = word1.length();
    int m = word2.length();

    // 有一个字符串为空串
    if (n * m == 0)
        return n + m;

    // dp数组
    int[][] dp = new int[n + 1][m + 1];

    // 边界状态初始化
    for (int i = 0; i < n + 1; i++)
        dp[i][0] = i;

    for (int j = 0; j < m + 1; j++)
        dp[0][j] = j;

    // 计算所有 DP 值
    for (int i = 1; i < n + 1; i++)
        for (int j = 1; j < m + 1; j++) {
            int left = dp[i - 1][j] + 1;
            int down = dp[i][j - 1] + 1;
            int left_down = dp[i - 1][j - 1];
            if (word1.charAt(i - 1) != word2.charAt(j - 1))
                left_down += 1;
            dp[i][j] = mins(left, down, left_down);
        }
    return dp[n][m];
}
*/