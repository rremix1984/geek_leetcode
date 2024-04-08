/**
 * copyright 2022/1/19
 */
package com.leetcode.normal;

import org.junit.Test;

/**
    [ARRAY] ||||
    （中等）
    200. 岛屿数量
    给你一个由 '1'（陆地）和 '0'（水）组成的的二维网格，请你计算网格中岛屿的数量。
    岛屿总是被水包围，并且每座岛屿只能由水平方向和/或竖直方向上相邻的陆地连接形成。
    此外，你可以假设该网格的四条边均被水包围。
    示例 1：
        输入：grid = {{'1', '1', '1', '1', '0'},
                     {'1', '1', '0', '1', '0'},
                     {'1', '1', '0', '0', '0'},
                     {'0', '0', '0', '0', '0'}}
        输出：1
    示例 2：
        输入：grid = {{'1', '1', '0', '0', '0'},
                     {'1', '1', '0', '0', '0'},
                     {'0', '0', '1', '0', '0'},
                     {'0', '0', '0', '1', '1'}}
        输出：3
*/
public class NO200_N_NumberOfIslands {

    @Test
    public void test() {
        assert 1 == numIslands(new char[][]{
                {'1', '1', '1', '1', '0'},
                {'1', '1', '0', '1', '0'},
                {'1', '1', '0', '0', '0'},
                {'0', '0', '0', '0', '0'}});
        assert 3 == numIslands(new char[][]{
                {'1', '1', '0', '0', '0'},
                {'1', '1', '0', '0', '0'},
                {'0', '0', '1', '0', '0'},
                {'0', '0', '0', '1', '1'}});
        assert 0 == numIslands(new char[][]{});
    }

    public int numIslands(char[][] grid) {
        // 2024/3/23 NO.1 没做出来，Flood Fill法
        // 2024/3/27 NO.2 有思路，没做出来，再试试看
        // 2024/4/1  NO.3 思路对，但是没写出来
        // 2024/4/7  NO.4 思路对，没做出来
        int ret = 0;
        // TODO

        return ret;
    }

}











/*
// 方法1：dfs 深度遍历
private int row, col;

public int numIslands(char[][] grid) {
    // 岛屿数量
    int cnt = 0;

    // 初始化行、列数
    row = grid.length;
    if (row == 0)
        return 0;
    col = grid[0].length;

    // 迭代地图上每一个元素
    for (int i = 0; i < row; i++) {
        for (int j = 0; j < col; j++)
            // 发现陆地，就遍历（dfs）每一个周边元素
            if (grid[i][j] == '1') {
                dfs(grid, i, j);
                // 当把所有周边元素遍历完成之后
                // 岛屿数 +1
                cnt++;
            }
    }
    return cnt;
}

// 已知一个节点，遍历他的上、下、左、右所有节点
void dfs(char[][] grid, int i, int j) {
    // 处理边界条件
    // 超出（左、右、上、下）边界，或者不是陆地
    if (i < 0  || j < 0 ||
            i >= row || j >= col ||
            grid[i][j] != '1')
        return;

    // 用炸沉陆地的方法 -> floodfill法
    grid[i][j] = '0';

    // 递归上、下、左、右各个位置
    dfs(grid,i + 1, j);
    dfs(grid,i - 1, j);
    dfs(grid, i, j + 1);
    dfs(grid, i, j - 1);
}
*/