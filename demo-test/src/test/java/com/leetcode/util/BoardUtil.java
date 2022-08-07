/**
 * copyright 2022/1/19
 */
package com.leetcode.util;

public class BoardUtil {

    public static void printBoard(char[][] board) {
        System.out.println("-----------------------");
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (j % 3 == 0)
                    System.out.print("| ");
                System.out.print(board[i][j] + " ");
            }
            System.out.print("| ");
            System.out.println();
            if (i % 3 == 2)
                System.out.println("-------------------------");
        }
    }

}
