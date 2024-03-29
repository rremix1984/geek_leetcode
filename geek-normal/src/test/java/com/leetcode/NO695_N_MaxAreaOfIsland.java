package com.leetcode;

import org.junit.Test;
import static java.lang.Math.max;

/**
    [ARRAY]
    (中等)
    NO.695 岛屿的最大面积
    给你一个大小为 m x n 的二进制矩阵grid。
    岛屿 是由一些相邻的 1 (代表土地) 构成的组合，这里的「相邻」要求两个1必须在
    水平或者竖直的四个方向上 相邻。你可以假设 grid 的四个边缘都被 0（代表水）包围着。
    岛屿的面积是岛上值为 1 的单元格的数目。
    计算并返回 grid 中最大的岛屿面积。如果没有岛屿，则返回面积为 0 。
    示例 1：
        输入：grid = [[0,0,1,0,0,0,0,1,0,0,0,0,0],
                     [0,0,0,0,0,0,0,1,1,1,0,0,0],
                     [0,1,1,0,1,0,0,0,0,0,0,0,0],
                     [0,1,0,0,1,1,0,0,1,0,1,0,0],
                     [0,1,0,0,1,1,0,0,1,1,1,0,0],
                     [0,0,0,0,0,0,0,0,0,0,1,0,0],
                     [0,0,0,0,0,0,0,1,1,1,0,0,0],
                     [0,0,0,0,0,0,0,1,1,0,0,0,0]]
        输出：6
        解释：答案不应该是 11 ，因为岛屿只能包含水平或垂直这四个方向上的 1 。
    示例 2：
        输入：grid = [[0,0,0,0,0,0,0,0]]
        输出：0
        提示：
            m == grid.length
            n == grid[i].length
            1 <= m, n <= 50
            grid[i][j] 为 0 或 1
    Related Topics:深度优先搜索,广度优先搜索,并查集,数组,矩阵
*/
public class NO695_N_MaxAreaOfIsland {

    @Test
    public void test() {
        int[][] grid = new int[][]
                {{0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
                 {0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0},
                 {0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
                 {0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 1, 0, 0},
                 {0, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 0},
                 {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
                 {0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0},
                 {0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0}};
        assert maxAreaOfIsland(grid) == 6;
    }

    private static final int[][] DIRECTIONS =
            new int[][]{{ 0,  1},
                        { 0, -1},
                        { 1,  0},
                        {-1,  0}};

    public int maxAreaOfIsland(int[][] grid) {
        // 2024/3/16 NO.1
        int count = 0;
        // 遍历网格内所有的点
        for (int i = 0; i < grid.length; i++)
            for (int j = 0; j < grid[0].length; j++)
                // 如果这个位置是陆地，就计算它的面积，并将走过的陆地设置为海洋
                if (grid[i][j] == 1)
                    count = max(count, dfs(grid, i, j));

        return count;
    }

    private int dfs(int[][] grid, int i, int j) {
        int count = 1;
        grid[i][j] = 0;
        for (int[] dir : DIRECTIONS) {
            int x = dir[0] + i;
            int y = dir[1] + j;
            if (x < 0 || x > grid.length - 1
             || y < 0 || y > grid[0].length - 1
             || grid[x][y] == 0)
                continue;

            count += dfs(grid, x, y);
        }
        return count;
    }

}
















/*
// 方法1：
private static final int[][] DIRECTIONS =
            new int[][]{{ 0,  1},
                        { 0, -1},
                        { 1,  0},
                        {-1,  0}};
private static final int WATER = 0;
private static final int GRAND = 1;
public int maxAreaOfIsland(int[][] grid) {
    // 2024/3/16 NO.1
    int count = 0;
    // 遍历网格内所有的点
    for (int i = 0; i < grid.length; i++)
        for (int j = 0; j < grid[0].length; j++)
            // 如果这个位置是陆地，就计算它的面积，并将走过的陆地设置为海洋
            if (grid[i][j] == GRAND)
                count = max(count, dfs(grid, i, j));

    return count;
}

private int dfs(int[][] grid, int i, int j) {
    int count = 1;
    grid[i][j] = WATER;
    for (int[] dir : DIRECTIONS) {
        int x = dir[0] + i;
        int y = dir[1] + j;
        if (x < 0 || x > grid.length - 1
                || y < 0 || y > grid[0].length - 1
                || grid[x][y] == WATER)
            continue;

        count += dfs(grid, x, y);
    }
    return count;
}
*/