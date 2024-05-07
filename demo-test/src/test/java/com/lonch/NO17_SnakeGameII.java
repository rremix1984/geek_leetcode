/**
 * @copyright
 */
package com.lonch;

import com.lonch.util.MatrixNode;
import org.junit.Test;
import java.util.Random;
import java.util.Scanner;

import static com.leetcode.util.SystemUtil.printArr;
import static java.lang.System.in;

/**
 * @author rremix
 */
@SuppressWarnings("all")
public class NO17_SnakeGameII {

    @Test
    public void test() {

    }

}
































/*
static class Game{
    int score;
    int ROWS = 10, COLS = 10;
    char[][] board;
    // ******************************
//     区别点 0：蛇内部是一个 Character 对象
//     ********************************
    MatrixNode<Character> snake;
    int[] food;

    public Game() {
        score = 0;
        snake = new MatrixNode<>('*');
        board = new char[ROWS][COLS];
        food = new int[]{3, 3};
        init();
    }

    public void init() {
        for (int i = 0; i < ROWS; i++)
            for (int j = 0; j < COLS; j++)
                board[i][j] = '-';

        board[food[0]][food[1]] = 'F';

        // *******************
//         区别点 1：画蛇
//         ********************
        MatrixNode<Character> cur = snake;
        while (cur != null) {
            board[cur.row][cur.col] = cur.val;
            cur = cur.right;
        }

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                out.print(board[i][j] + " ");
            }
            out.println();
        }
    }

    public int move(char dir) {
        //******************
//         区别点 2：用 position替代原有的 int[] 数组
//         ********************
        int[] position = new int[]{snake.row, snake.col};
        switch (dir) {
            case 'W':
                position[0]--;
                break;
            case 'S':
                position[0]++;
                break;
            case 'A':
                position[1]--;
                break;
            case 'D':
                position[1]++;
                break;
        }

        if (position[0] < 0 || position[0] >= ROWS || position[1] < 0 || position[1] >= COLS
                || board[position[0]][position[1]] == '*') {
            return -1;
        }

        MatrixNode<Character> newHead =
                    new MatrixNode<>('*', position[0], position[1], null, snake);
        snake.left = newHead;
        snake = newHead;

        if (position[0] == food[0] && position[1] == food[1]) {
            score++;
            food = new int[]{new Random().nextInt(ROWS), new Random().nextInt(COLS)};
        } else {
            //***********************
//             区别点 3:
//             ************************
            // 去掉蛇尾巴：removeLast()
            MatrixNode<Character> tail = snake.right;
            while (tail.right != null) {
                tail = tail.right;
            }
            tail.left.right = null;
        }

        init();
        return score;
    }
}

static class MatrixNode<T> {
    public T val;
    public MatrixNode<T> left, right;
    //*****************************
//      区别点5：增加row, col字段，定义坐标
//     *******************************
    public int row, col;

    public MatrixNode(T val) {
        this.val = val;
        this.left = null;
        this.right = null;
    }

    public MatrixNode(T val, int row, int col,
                      MatrixNode<T> left,
                      MatrixNode<T> right) {
        this.val = val;
        this.left = left;
        this.right = right;
        this.row = row;
        this.col = col;
    }

    public void test() {
        Game g = new Game();
        while (g.move(read()) != -1)
            out.println("W/A/S/D");
        out.println("game over!  score:" + g.score);
    }

}
*/