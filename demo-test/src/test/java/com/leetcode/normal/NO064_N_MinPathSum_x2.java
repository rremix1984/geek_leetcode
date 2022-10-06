/**
 * copyright 2022/1/19
 */
package com.leetcode.normal;

import org.junit.Test;
import static java.lang.Math.min;

/**
    (中等)
    64. 最小路径和
        给定一个包含非负整数的 m x n 网格 grid ，请找出一条从左上角到右下角的路径，使得路径上的数字总和为最小。
        说明：每次只能向下或者向右移动一步。
    示例 1：
        输入：grid = {{1, 3, 1}, {1, 5, 1}, {4, 2, 1}}
        输出：7
        解释：因为路径 1→3→1→1→1 的总和最小。
    示例 2：
        输入：grid = {{1, 2, 3}, {4, 5, 6}}
        输出：12
*/
public class NO064_N_MinPathSum_x2 {

    @Test
    public void test() {
        assert 7 == minPathSum(new int[][]{{1, 3, 1}, {1, 5, 1}, {4, 2, 1}});
        assert 12 == minPathSum(new int[][]{{1, 2, 3}, {4, 5, 6}});
        assert 7 == minPathSum(new int[][]{{1, 2, 1}, {1, 2, 3}, {3, 2, 1}});
    }

    public int minPathSum(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[][] dp = new int[m][n];
        dp[0][0] = grid[0][0];

        for (int i = 1; i < m; i++)
            dp[i][0] = dp[i-1][0] + grid[i][0];

        for (int i = 1; i < n; i++)
            dp[0][i] = dp[0][i-1] + grid[0][i];

        for (int i = 1; i < m; i++)
            for (int j = 1; j < n; j++)
                dp[i][j] = min(dp[i-1][j], dp[i][j-1]) + grid[i][j];

        return dp[m-1][n-1];
    }

}

















/**
// 方法1：动态规划
public int minPathSum(int[][] grid) {
    // 边界值判断
    if (grid == null || grid.length == 0 || grid[0].length == 0)
        return 0;

    int rows = grid.length;
    int columns = grid[0].length;

    // 动态规划数组
    int[][] dp = new int[rows][columns];
    dp[0][0] = grid[0][0];

    for (int i = 1; i < rows; i++)
        dp[i][0] = dp[i - 1][0] + grid[i][0];

    for (int j = 1; j < columns; j++)
        dp[0][j] = dp[0][j - 1] + grid[0][j];

    // 动态规划 dp[i][j] = grid[i][j] 加上 dp[i-1][j] 和 dp[i][j-1] 的最小值
    for (int i = 1; i < rows; i++)
        for (int j = 1; j < columns; j++)
            dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + grid[i][j];

    return dp[rows - 1][columns - 1];
}
*/