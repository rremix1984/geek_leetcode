/**
 * copyright 2022/1/19
 */
package com.leetcode.normal;

import org.junit.Test;

/**
    (中等)
    79. 单词搜索
        给定一个 m x n 二维字符网格 board 和一个字符串单词 word 。如果 word 存在于网格中，返回 true ；否则，返回 false 。
        单词必须按照字母顺序，通过相邻的单元格内的字母构成，其中“相邻”单元格是那些水平相邻或垂直相邻的单元格。同一个单元格内的字母不允许被重复使用。
    示例 1：
        输入：board = {{'A','B','C','E'},
                      {'S','F','C','S'},
                      {'A','D','E','E'}}, word = "ABCCED"
        输出：true
    示例 2：
        输入：board = {{'A','B','C','E'},
                      {'S','F','C','S'},
                      {'A','D','E','E'}}, word = "SEE"
        输出：true
    示例 3：
        输入：board = {{'A','B','C','E'},
                      {'S','F','C','S'},
                      {'A','D','E','E'}}, word = "ABCB"
        输出：false
*/
public class NO079_N_WordSearch_x2 {

    @Test
    public void test() {
        assert exist(new char[][]{{'A','B','C','E'},
                                  {'S','F','C','S'},
                                  {'A','D','E','E'}}, "ABCCED");
        assert exist(new char[][]{{'A','B','C','E'},
                                  {'S','F','C','S'},
                                  {'A','D','E','E'}}, "SEE");
        assert !exist(new char[][]{{'A','B','C','E'},
                                   {'S','F','C','S'},
                                   {'A','D','E','E'}}, "ABCB");
        assert  exist(new char[][]{{'C','A','A'},
                                   {'A','A','A'},
                                   {'B','C','D'}}, "AAB");
    }

    int[] dx = new int[]{-1, 0, 1, 0};
    int[] dy = new int[]{ 0, 1, 0,-1};

    public boolean exist(char[][] board, String word) {
        return false;
    }

}




















/**
int[] dx = new int[]{-1, 0, 1, 0};
int[] dy = new int[]{ 0, 1, 0,-1};

public boolean exist(char[][] board, String word) {
    for (int i = 0; i < board.length; i++)
        for (int j = 0; j < board[i].length; j++)
            if (dfs(board, word, 0, i, j))
                return true;

    return false;
}

// word 字符串
// step 第几个字符
// x 横坐标，y 纵坐标
boolean dfs(char[][] board, String word, int step, int x, int y) {
    if (board[x][y] != word.charAt(step))
        return false;

    if (step == word.length() - 1)
        return true;

    char t = board[x][y];
    board[x][y] = '.';

    for (int i = 0; i < 4; i++) {
        int a = x + dx[i];
        int b = y + dy[i];

        if (a < 0 || a >= board.length ||
            b < 0 || b >= board[0].length || board[a][b] == '.')
            continue;

        if (dfs(board, word, step + 1, a, b))
            return true;
    }
    board[x][y] = t;
    return false;
}
*/