/**
 * copyright 2022/1/19
 */
package com.leetcode;

import org.junit.Test;
import static com.leetcode.util.LogUtil.info;

/**
    （中等）
    79. 单词搜索
        给定一个 m x n 二维字符网格 board 和一个字符串单词 word 。
        如果 word 存在于网格中，返回 true ；否则，返回 false 。单词
        必须按照字母顺序，通过相邻的单元格内的字母构成，其中“相邻”单元格
        是那些水平相邻或垂直相邻的单元格。同一个单元格内的字母不允许被重复
        使用。
    示例 1：
        输入：board = {{'A','B','C','E'},
                      {'S','F','C','S'},
                      {'A','D','E','E'}},
              word = 'ABCCED'
        输出：true
    示例 2：
        输入：board = {{'A','B','C','E'},
                      {'S','F','C','S'},
                      {'A','D','E','E'}},
              word = 'SEE'
        输出：true
    示例 3：
        输入：board = {{'A','B','C','E'},
                      {'S','F','C','S'},
                      {'A','D','E','E'}},
              word = 'ABCB'
        输出：false
*/
public class NO79_WordSearch {

    @Test
    public void test() {
        info(exist(new char[][]
                {{'A','B','C','E'},
                {'S','F','C','S'},
                {'A','D','E','E'}}, "ABCCED"));// true
        info(exist(new char[][]{
                {'A','B','C','E'},
                {'S','F','C','S'},
                {'A','D','E','E'}},"SEE"));// true
        info(exist(new char[][]{
                {'A','B','C','E'},
                {'S','F','C','S'},
                {'A','D','E','E'}}, "ABCB"));// false
    }

    private static final int[][] DIRECTIONS = {{-1, 0}, {0, -1}, {0, 1}, {1, 0}};
    private int rows;
    private int cols;
    private int len;
    private boolean[][] visited;
    private char[] charArray;
    private char[][] board;

    public boolean exist(char[][] board, String word) {
        rows = board.length;
        if (rows == 0) {
            return false;
        }
        cols = board[0].length;
        visited = new boolean[rows][cols];

        this.len = word.length();
        this.charArray = word.toCharArray();
        this.board = board;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (dfs(i, j, 0)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean dfs(int x, int y, int begin) {
        if (begin == len - 1) {
            return board[x][y] == charArray[begin];
        }
        if (board[x][y] == charArray[begin]) {
            visited[x][y] = true;
            for (int[] direction : DIRECTIONS) {
                int newX = x + direction[0];
                int newY = y + direction[1];
                if (inArea(newX, newY) && !visited[newX][newY]) {
                    if (dfs(newX, newY, begin + 1)) {
                        return true;
                    }
                }
            }
            visited[x][y] = false;
        }
        return false;
    }

    private boolean inArea(int x, int y) {
        return x >= 0 && x < rows && y >= 0 && y < cols;
    }
    
}
