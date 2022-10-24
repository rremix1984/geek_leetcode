/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

/**
    (简单)
    1351. 统计有序矩阵中的负数
        给你一个 m * n 的矩阵 grid，矩阵中的元素无论是按行还是按列，
        都以非递增顺序排列。 请你统计并返回 grid 中 负数 的数目。
    示例 1：
        输入：grid = {{ 4,  3,  2, -1},
                     { 3,  2,  1, -1},
                     { 1,  1, -1, -2},
                     {-1, -1, -2, -3}}
        输出：8
        解释：矩阵中共有 8 个负数。
    示例 2：
        输入：grid = {{3, 2},
                     {1, 0}}
        输出：0
*/
public class NO1351_E_CountNegatives_x2 {

    @Test
    public void test() {
        assert 8 == countNegatives(
                new int[][]{{ 4,  3,  2, -1},
                            { 3,  2,  1, -1},
                            { 1,  1, -1, -2},
                            {-1, -1, -2, -3}});
        assert 0 == countNegatives(
                new int[][]{{3, 2}, {1, 0}});
    }

    public int countNegatives(int[][] grid) {
        int ans = 0;
        return ans;
    }
    
}


















/**
// 方法1：
public int countNegatives(int[][] grid) {
    int ans = 0;
    int row = grid.length;
    int col = grid[0].length;
    for (int i = 0; i < row; i++) {
        // 先看行 row
        if (grid[i][0] < 0)
            return ans + (row - i) * col;

        // 再看列 col
        for (int j = 0; j < col; j++)
            if (grid[i][j] < 0) {
                ans += (col - j);
                break;
            }
    }
    return ans;
}
*/