/**
 * @copyright wangxiaozhe
 */
package com.lonch;

import com.lonch.util.MatrixNode;
import lombok.val;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;
import static java.lang.System.in;
import static java.lang.System.out;

/**
    [SNAKE] |||||||||||
    (困难)
    NO.9 贪吃蛇游戏
 */
@SuppressWarnings("all")
public class NO9_SnakeGame {

    public static MatrixNode first;
    public static MatrixNode last;

    public static void main(String[] args) {
        // 2024/4/9  NO.1 能看懂，做不出来
        // 2024/4/10-11-12-13-14-15-16-18 NO.2-3-4-5-6-7-8-9 一遍过
        // 2024/4/30-5/2 NO.10-11 一遍过
        Game g = new Game();
        while (g.move(new Scanner(in).nextLine().charAt(0)) != -1) {
            out.println("W/S/A/D");
        }
        out.println("score:" + g.score);
    }

    static class Game {
        int score;
        int ROWS = 10, COLS = 10;
        LinkedList<int[]> snake = new LinkedList<>();
        char[][] board = new char[ROWS][COLS];
        int[] food = new int[]{3, 3};
        public Game() {
            score = 0;
            snake.addFirst(new int[]{0, 0});
            init();
        }

        public void init() {
            for (int i = 0; i < ROWS; i++) {
                for (int j = 0; j < COLS; j++) {
                    board[i][j] = '-';
                }
            }

            board[food[0]][food[1]] = 'F';
            for (int[] p : snake) {
                board[p[0]][p[1]] = '*';
            }

            for (int i = 0; i < COLS; i++) {
                for (int j = 0; j < COLS; j++) {
                    out.print(board[i][j] + " ");
                }
                out.println();
            }
        }

        public int move(char dir) {
            int[] head = snake.getFirst();
            int[] nHead = new int[]{head[0], head[1]};

            switch(dir) {
                case 'W':
                    nHead[0]--;
                    break;
                case 'S':
                    nHead[0]++;
                    break;
                case 'A':
                    nHead[1]--;
                    break;
                case 'D':
                    nHead[1]++;
                    break;
            }

            if (nHead[0] < 0 || nHead[0] >= ROWS || nHead[1] < 0 || nHead[1] >= COLS) {
                return -1;
            }

            if (nHead[0] == food[0] && nHead[1] == food[1]) {
                score++;
                food = new int[]{new Random().nextInt(ROWS), new Random().nextInt(COLS)};
            } else {
                snake.removeLast();
            }

            for (int[] p : snake) {
                if (p[0] == nHead[0] && p[1] == nHead[1]) {
                    return -1;
                }
            }

            snake.addFirst(nHead);
            init();
            return score;
        }
    }
}




























/*
public static void main(String[] args) {
    SnakeGame g = new SnakeGame();
    while (g.move(new Scanner(in).nextLine().charAt(0)) != -1)
        out.println("W/S/D/A");
    out.println("game over! score:" + g.score);
}

static class SnakeGame {
    // TODO 成员变量
    int ROWS = 10;
    int COLS = 10;
    LinkedList<int[]> snake;
    int[] food;
    int score;
    char[][] board;

    // TODO 构造函数
    public SnakeGame() {
        snake = new LinkedList<>();
        snake.add(new int[]{0, 0}); // 初始位置
        food = new int[]{3, 3}; // 初始食物位置
        score = 0;
        board = new char[ROWS][COLS];
        init();
    }

    // TODO 初始化游戏界面
    private void init() {
        for (int i = 0; i < ROWS; i++)
            for (int j = 0; j < COLS; j++)
                board[i][j] = '-';

        board[food[0]][food[1]] = 'F';
        for (int[] s : snake)
            board[s[0]][s[1]] = '*';

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++)
                System.out.print(board[i][j] + " ");
            out.println();
        }
    }

    // TODO 移动
    public int move(char dir) {
        int[] head = snake.getFirst();
        int[] newHead = new int[]{head[0], head[1]};

        // TODO 根据方向更新新头部的位置
        switch (dir) {
            case 'W':
                newHead[0]--;
                break;
            case 'S':
                newHead[0]++;
                break;
            case 'A':
                newHead[1]--;
                break;
            case 'D':
                newHead[1]++;
                break;
        }

        // TODO 判断是否出界
        if (newHead[0] < 0 || newHead[0] >= ROWS ||
                newHead[1] < 0 || newHead[1] >= COLS)
            return -1; // 游戏结束

        // TODO 判断是否吃到食物
        if (newHead[0] == food[0] && newHead[1] == food[1]) {
            score++;
            food = new int[]{new Random().nextInt(ROWS),
                             new Random().nextInt(COLS)};
        } else {
            snake.removeLast(); // 移动后删除尾部，相当于向前移动
        }

        // TODO 判断是否碰到自己
        for (int[] part : snake)
            if (part[0] == newHead[0] && part[1] == newHead[1])
                return -1; // 游戏结束

        // TODO 更新蛇头位置、更新游戏界面、返回当前得分
        snake.addFirst(newHead);
        init();
        return score;
    }
}
*/