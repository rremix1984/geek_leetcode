package com.leetcode;

import org.junit.Test;

/**
    [ARRAY]
    (中等)
    NO.1020 飞地的数量
    给你一个大小为 m x n 的二进制矩阵 grid ，其中 0 表示一个海洋单元格、
    1 表示一个陆地单元格。一次 移动 是指从一个陆地单元格走到另一个相邻
    （上、下、左、右）的陆地单元格或跨过 grid 的边界。
    返回网格中 无法 在任意次数的移动中离开网格边界的陆地单元格的数量。
    示例 1：
        输入：grid = [[0, 0, 0, 0],
                     [1, 0, 1, 0],
                     [0, 1, 1, 0],
                     [0, 0, 0, 0]]
        输出：3
        解释：有三个 1 被 0 包围。一个 1 没有被包围，因为它在边界上。
    示例 2：
        输入：grid = [[0, 1, 1, 0],
                     [0, 0, 1, 0],
                     [0, 0, 1, 0],
                     [0, 0, 0, 0]]
        输出：0
        解释：所有 1 都在边界上或可以到达边界。
    提示：
        m == grid.length
        n == grid[i].length
        1 <= m, n <= 500
        grid[i][j] 的值为 0 或 1
    Related Topics:深度优先搜索,广度优先搜索.并查集,数组,矩阵
*/
public class NO1020_N_NumEnclaves {

    @Test
    public void test() {
        assert 3 == numEnclaves(
                new int[][]{{0, 0, 0, 0},
                            {1, 0, 1, 0},
                            {0, 1, 1, 0},
                            {0, 0, 0, 0}});
        assert 0 == numEnclaves(
                new int[][]{{0, 1, 1, 0},
                            {0, 0, 1, 0},
                            {0, 0, 1, 0},
                            {0, 0, 0, 0}});
    }

    public int numEnclaves(int[][] grid) {
        //统计中间结果，即边界出发能到达的陆地总面积
        int middle = 0;

        //陆地总数
        int landNumbers = 0;

        return landNumbers - middle;
    }

}























/*
// 方法1：
public int numEnclaves(int[][] grid) {
    //统计中间结果，即边界出发能到达的陆地总面积
    int middle = 0;

    //陆地总数
    int landNumbers = 0;

    for (int[] ints : grid)
        for (int j = 0; j < grid[0].length; j++)
            if (ints[j] == 1)
                landNumbers++;

    //统计边界出发能到达的陆地数
    for (int i = 0; i < grid.length; i++)
        for (int j = 0; j < grid[0].length; j++)
            if (grid[i][j] == 1 && (i == 0 || i == grid.length - 1 || j == 0 || j == grid[0].length - 1))
                middle = middle + countSize(grid, i, j);

    return landNumbers - middle;
}

private int countSize(int[][] grid, int i, int j) {
    if (i < 0 || i >= grid.length
            || j < 0 || j >= grid[0].length)
        return 0;

    if (grid[i][j] != 1)
        return 0;

    grid[i][j] = 2;
    return 1 + countSize(grid, i - 1, j)
            + countSize(grid, i + 1, j)
            + countSize(grid, i, j - 1)
            + countSize(grid, i, j + 1);
}
*/