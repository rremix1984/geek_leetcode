/**
 * copyright 2022/1/19
 */
package com.leetcode.normal;

import org.junit.Test;
import static com.leetcode.util.LogUtil.info;

/**
    （中等）
    62. 不同路径
        一个机器人位于一个 m x n 网格的左上角（起始点在下图中
        标记为 “Start” ）。机器人每次只能向下或者向右移动一步。
        机器人试图达到网格的右下角（在下图中标记为 “Finish” ）。
        问总共有多少条不同的路径？
    示例 1：
        输入：m = 3, n = 7
        输出：28
    示例 2：
        输入：m = 3, n = 2
        输出：3
        解释：
        从左上角开始，总共有 3 条路径可以到达右下角。
        1. 向右 -> 向下 -> 向下
        2. 向下 -> 向下 -> 向右
        3. 向下 -> 向右 -> 向下
    示例 3：
        输入：m = 7, n = 3
        输出：28
    示例 4：
        输入：m = 3, n = 3
        输出：6
*/
public class NO62_UniquePaths_x2 {

    @Test
    public void test() {
        info(uniquePaths(3, 7));// 28
//        info(uniquePaths(7, 3));// 28
//        info(uniquePaths(3, 3));// 6
//        info(uniquePaths(3, 2));// 3
    }

    public int uniquePaths(int m, int n) {
        return -1;
    }

}









/**
// 方法1：数学归纳法
public int uniquePaths(int m, int n) {
    int[][] paths = new int[m][n];
    for (int i = 0; i < n; i++)
        paths[0][i] = 1;

    for (int i = 0; i < m; i++)
        paths[i][0] = 1;

    for (int i = 1; i < m; i++)
        for (int j = 1; j < n; j++)
            paths[i][j] = paths[i - 1][j] + paths[i][j - 1];

    return paths[m-1][n-1];
}

// 方法2：简化方法
public int uniquePaths(int m, int n) {
    int[] cur = new int[n];
    Arrays.fill(cur, 1);
    for (int i = 1; i < m; i++)
        for (int j = 1; j < n; j++)
            cur[j] += cur[j - 1];
    return cur[n - 1];
}
*/