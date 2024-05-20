/**
 * copyright 2022/1/19
 */
package com.leetcode.undo;

import org.junit.Test;

import static com.leetcode.util.LogUtil.info;

/**
    (中等)
    130. 被围绕的区域
        给你一个 m x n 的矩阵 board ，由若干字符 'X' 和 'O' ，
        找到所有被 'X' 围绕的区域，并将这些区域里所有的 'O' 用 'X' 填充。
    示例 1：
        输入：board = {{'X','X','X','X'},
                      {'X','O','O','X'},
                      {'X','X','O','X'},
                      {'X','O','X','X'}}
        输出：{{'X','X','X','X'},
              {'X','X','X','X'},
              {'X','X','X','X'},
              {'X','O','X','X'}}
        解释：被围绕的区间不会存在于边界上，换句话说，任何边界上的 'O' 都不会被填充为 'X'。
            任何不在边界上，或不与边界上的 'O' 相连的 'O' 最终都会被填充为 'X'。如果两个
            元素在水平或垂直方向相邻，则称它们是“相连”的。
    示例 2：
        输入：board = {{'X'}}
        输出：{{'X'}}
*/
@SuppressWarnings("all")
public class NO130_N_SurroundedRegions {

    @Test
    public void test() {
        char[][] tmp = new char[][]{
                    {'X','X','X','X'},
                    {'X','O','O','X'},
                    {'X','X','O','X'},
                    {'X','O','X','X'}};
        solve(tmp);
        info(tmp);
    }

    public void solve(char[][] board) {

    }
    
}



















/**
int[] dx = {-1, 0, 1, 0}, dy = {0, 1, 0, -1};
int m, n;

public void dfs(int x, int y, char[][] board) {
    board[x][y] = '1';

    for (int i = 0; i < 4; i++) {
        int xx = x + dx[i];
        int yy = y + dy[i];
        if (xx >= 0 && xx <= m && yy >=0 && yy <= n && board[xx][yy] == 'O') {
            dfs(xx, yy, board);
        }
    }
}

public void solve(char[][] board) {
    m = board.length - 1; // 行
    n = board[0].length - 1; // 列
    for (int j = 0; j <= n; j++)
        if (board[0][j] == 'O')
            dfs(0, j, board);

    for (int j = 0; j <= n; j++)
        if (board[m][j] == 'O')
            dfs(m, j, board);

    for (int i = 0; i <= m; i++)
        if (board[i][0] == 'O')
            dfs(i, 0, board);

    for (int i = 0; i <= m; i++)
        if (board[i][n] == 'O')
            dfs(i, n, board);

    for (int i = 0; i <= m; i++)
        for (int j = 0; j <= n; j++)
            if (board[i][j] == 'O')
                board[i][j] = 'X';
            else if (board[i][j] == '1')
                board[i][j] = 'O';
}
*/