/**
 * copyright 2022/1/19
 */
package com.leetcode;

import org.junit.Test;
import static com.leetcode.util.BoardUtil.printBoard;
import static com.leetcode.util.LogUtil.info;

/**
    37. 解数独
        编写一个程序，通过填充空格来解决数独问题。
        数独的解法需 遵循如下规则：
        数字 1-9 在每一行只能出现一次。
        数字 1-9 在每一列只能出现一次。
        数字 1-9 在每一个以粗实线分隔的 3x3 宫内只能出现一次。（请参考示例图）
        数独部分空格内已填入了数字，空白格用 '.' 表示。
    示例 1：
        输入：board = [
                ['5','3','.', | '.','7','.', | '.','.','.'],
                ['6','.','.', | '1','9','5', | '.','.','.'],
                ['.','9','8', | '.','.','.', | '.','6','.'],
                -------------------------------------
                ['8','.','.', | '.','6','.', | '.','.','3'],
                ['4','.','.', | '8','.','3', | '.','.','1'],
                ['7','.','.', | '.','2','.', | '.','.','6'],
                -------------------------------------
                ['.','6','.', | '.','.','.', | '2','8','.'],
                ['.','.','.', | '4','1','9', | '.','.','5'],
                ['.','.','.', | '.','8','.', | '.','7','9']]
        输出：[
                ['5','3','4', | '6','7','8', | '9','1','2'],
                ['6','7','2', | '1','9','5', | '3','4','8'],
                ['1','9','8', | '3','4','2', | '5','6','7'],
                ----------------------------------------
                ['8','5','9', | '7','6','1', | '4','2','3'],
                ['4','2','6', | '8','5','3', | '7','9','1'],
                ['7','1','3', | '9','2','4', | '8','5','6'],
                ----------------------------------------
                ['9','6','1', | '5','3','7', | '2','8','4'],
                ['2','8','7', | '4','1','9', | '6','3','5'],
                ['3','4','5', | '2','8','6', | '1','7','9']]
        解释：输入的数独如上图所示，唯一有效的解决方案如下所示：
*/
public class NO37_SodukuSolver {

    @Test
    public void test() {
        char[][] board = new char[][]{
                // ------------------------------------------
                {'5', '3', '.',  '.', '7', '.',  '.', '.', '.'},
                {'6', '.', '.',  '1', '9', '5',  '.', '.', '.'},
                {'.', '9', '8',  '.', '.', '.',  '.', '6', '.'},
                // ------------------------------------------
                {'8', '.', '.',  '.', '6', '.',  '.', '.', '3'},
                {'4', '.', '.',  '8', '.', '3',  '.', '.', '1'},
                {'7', '.', '.',  '.', '2', '.',  '.', '.', '6'},
                // ------------------------------------------
                {'.', '6', '.',  '.', '.', '.',  '2', '8', '.'},
                {'.', '.', '.',  '4', '1', '9',  '.', '.', '5'},
                {'.', '.', '.',  '.', '8', '.',  '.', '7', '9'}
                // ------------------------------------------
        };
        solveSudoku(board);
        printBoard(board);
    }

    public void solveSudoku(char[][] board) {
        boolean[][] row = new boolean[9][10]//记录某行，某位数字是否已经被摆放
                   ,col = new boolean[9][10] //记录某列，某位数字是否已经被摆放
                 ,block = new boolean[9][10];//记录某3x3宫格内，某位数字是否已经被摆放
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++)
                if (board[i][j] != '.') {
                    int num = board[i][j] - '0';
                    row[i][num] = true;
                    col[j][num] = true;
                    // blockIndex = i / 3 * 3 + j / 3，取整
                    block[i / 3 * 3 + j / 3][num] = true;
                }
        dfs(board, row, col, block, 0, 0);
    }

    private boolean dfs(char[][] board,   // 数独
                        boolean[][] row,  // 第 i 行是否有元素 m
                        boolean[][] col,  // 第 j 行是否有元素 n
                        boolean[][] block,// 第 k 个九宫格是否有元素 o
                        int i,  // 行
                        int j) {// 列
        // 找寻空位置
        while (board[i][j] != '.') {
            if (++j >= 9) {
                i++;
                j = 0;
            }
            if (i >= 9)
                return true;
        }

        for (int num = 1; num <= 9; num++) {
            int idx = i / 3 * 3 + j / 3;
            // 如果数字 num 同时不在 "行、列、九宫格" 任何一个中
            if (!row[i][num]
                && !col[j][num]
                && !block[idx][num]) {

                // 递归
                board[i][j] = (char) ('0' + num);
                row[i][num] = true;
                col[j][num] = true;
                block[idx][num] = true;

                if (dfs(board, row, col, block, i, j))
                    return true;

                // 说明递归的过程中遇到了错误答案，开始回溯，把单元格内容还原回去 [false, false, false, .]
                row[i][num] = false;
                col[j][num] = false;
                block[idx][num] = false;
                board[i][j] = '.';
            }
        }

        // 当所有数字都根填好了，说明当前值不应该放到这个格子里，返回false，触发回溯。
        return false;
    }
}

















/**
// 方法1：回溯法
public void solveSudoku(char[][] board) {
    boolean[][] row = new boolean[9][10]//记录某行，某位数字是否已经被摆放
            ,col = new boolean[9][10] //记录某列，某位数字是否已经被摆放
            ,block = new boolean[9][10];//记录某3x3宫格内，某位数字是否已经被摆放
    for (int i = 0; i < 9; i++)
        for (int j = 0; j < 9; j++)
            if (board[i][j] != '.') {
                int num = board[i][j] - '0';
                row[i][num] = true;
                col[j][num] = true;
                // blockIndex = i / 3 * 3 + j / 3，取整
                block[i / 3 * 3 + j / 3][num] = true;
            }
    dfs(board, row, col, block, 0, 0);
}

private boolean dfs(char[][] board,   // 数独
                    boolean[][] row,  // 第 i 行是否有元素 m
                    boolean[][] col,  // 第 j 行是否有元素 n
                    boolean[][] block,// 第 k 个九宫格是否有元素 o
                    int i,  // 行
                    int j) {// 列
    // 找寻空位置
    while (board[i][j] != '.') {
        if (++j >= 9) {
            i++;
            j = 0;
        }
        if (i >= 9)
            return true;
    }
    for (int num = 1; num <= 9; num++) {
        int idx = i / 3 * 3 + j / 3;
        if (!row[i][num]
                && !col[j][num]
                && !block[idx][num]) {
            // 递归
            board[i][j] = (char) ('0' + num);
            row[i][num] = true;
            col[j][num] = true;
            block[idx][num] = true;
            if (dfs(board, row, col, block, i, j))
                return true;
            // 回溯
            row[i][num] = false;
            col[j][num] = false;
            block[idx][num] = false;
            board[i][j] = '.';
        }
    }
    return false;
}
*/