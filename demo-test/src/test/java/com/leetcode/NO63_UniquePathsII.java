/**
 * copyright 2022/1/19
 */
package com.leetcode;

import org.junit.Test;
import static com.leetcode.util.LogUtil.info;

/**
    （中等）
    63. 不同路径 II
        一个机器人位于一个 m x n 网格的左上角 （起始点在下图中标记为 “Start” ）。
        机器人每次只能向下或者向右移动一步。机器人试图达到网格的右下角（在下图中标记为 “Finish”）。
        现在考虑网格中有障碍物。那么从左上角到右下角将会有多少条不同的路径？
        网格中的障碍物和空位置分别用 1 和 0 来表示。
    示例 1：
        输入：obstacleGrid = [
                    [0, 0, 0],
                    [0, 1, 0],
                    [0, 0, 0]]
        输出：2
        解释：3x3 网格的正中间有一个障碍物。
            从左上角到右下角一共有 2 条不同的路径：
                1. 向右 -> 向右 -> 向下 -> 向下
                2. 向下 -> 向下 -> 向右 -> 向右
    示例 2：
        输入：obstacleGrid =
                    [[0, 1],
                     [0, 0]]
        输出：1
*/
public class NO63_UniquePathsII {

    @Test
    public void test() {
        info(uniquePathsWithObstacles(
                new int[][]{
                        {0, 0, 0},
                        {0, 1, 0},
                        {0, 0, 0}}));// 2
        info(uniquePathsWithObstacles(
            new int[][]{{0, 1},
                        {0, 0}}));// 1
    }

    public int uniquePathsWithObstacles(int[][] arr) {
        if (arr == null || arr.length == 0)
            return 0;

        // 定义 dp 数组并初始化第 1 行和第 1 列。
        int m = arr.length;
        int n = arr[0].length;
        int[][] dp = new int[m][n];
        for (int i = 0; i < m && arr[i][0] == 0; i++)
            dp[i][0] = 1;

        for (int j = 0; j < n && arr[0][j] == 0; j++)
            dp[0][j] = 1;

        // 根据状态转移方程 dp[i][j] = dp[i - 1][j] + dp[i][j - 1] 进行递推。
        for (int i = 1; i < m; i++)
            for (int j = 1; j < n; j++)
                if (arr[i][j] == 0)
                    dp[i][j] = dp[i - 1][j] + dp[i][j - 1];

        return dp[m - 1][n - 1];
    }

}
















/**
// 方法1：dp动态规划
public int uniquePathsWithObstacles(int[][] arr) {
    int n = arr.length;
    int m = arr[0].length;
    int[] dp = new int[m];
    dp[0] = 1;

    // 如果第一个元素就是 1 说明开始就有障碍物
    if (arr[0][0] == 1)
        return 0;

    for (int i = 0; i < n; ++i) {
        for (int j = 0; j < m; ++j) {
            if (arr[i][j] == 1) {
                dp[j] = 0;
                continue;
            }
            if (j - 1 >= 0 && arr[i][j - 1] == 0)
                dp[j] += dp[j - 1];
        }
    }
    return dp[m - 1];
}


// 方法2：
public int uniquePathsWithObstacles(int[][] arr) {
    if (arr == null || arr.length == 0)
        return 0;

    // 定义 dp 数组并初始化第 1 行和第 1 列。
    int m = arr.length;
    int n = arr[0].length;
    int[][] dp = new int[m][n];
    for (int i = 0; i < m && arr[i][0] == 0; i++)
        dp[i][0] = 1;

    for (int j = 0; j < n && arr[0][j] == 0; j++)
        dp[0][j] = 1;

    // 根据状态转移方程 dp[i][j] = dp[i - 1][j] + dp[i][j - 1] 进行递推。
    for (int i = 1; i < m; i++)
        for (int j = 1; j < n; j++)
            if (arr[i][j] == 0)
                dp[i][j] = dp[i - 1][j] + dp[i][j - 1];

    return dp[m - 1][n - 1];
}
*/