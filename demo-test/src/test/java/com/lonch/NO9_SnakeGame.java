package com.lonch;

import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;
import static java.util.Arrays.copyOf;

/**
 * 贪吃蛇游戏
 */
@SuppressWarnings("all")
public class NO9_SnakeGame {

    public static void main(String[] args) {
        SnakeGame snakeGame = new SnakeGame();
        while (true) {
            System.out.print("Enter direction (W/S/A/D): ");
            String cmd = new Scanner(System.in).nextLine().substring(0, 1);
            int score = snakeGame.move(cmd.toUpperCase());
            if (score == -1) {
                System.out.println("Game Over! Final Score: " + snakeGame.score);
                break;
            }
        }
    }

    static class SnakeGame {
        int ROWS = 10, COLS = 10;
        LinkedList<int[]> snake;
        int[] food;
        int score;
        char[][] board;

        public SnakeGame() {
            snake = new LinkedList<>();
            snake.add(new int[]{0, 0}); // 初始位置
            food = new int[]{3, 3}; // 初始食物位置
            score = 0;
            board = new char[ROWS][COLS];
            init();
        }

        // 初始化游戏界面
        private void init() {
            for (int i = 0; i < ROWS; i++)
                for (int j = 0; j < COLS; j++)
                    board[i][j] = '-';

            for (int[] part : snake)
                board[part[0]][part[1]] = '*';

            board[food[0]][food[1]] = 'F';

            for (int i = 0; i < ROWS; i++) {
                for (int j = 0; j < COLS; j++)
                    System.out.print(board[i][j] + " ");
                System.out.println();
            }
            System.out.println();
        }

        // 移动
        public int move(String dir) {
            // TODO
            int[] head = snake.getFirst();
            int[] newHead = new int[]{head[0], head[1]};

            switch (dir) {
                case "W":
                    newHead[0]--;
                    break;
                case "S":
                    newHead[0]++;
                    break;
                case "A":
                    newHead[1]--;
                    break;
                case "D":
                    newHead[1]++;
                    break;
            }

            if (newHead[0] < 0 || newHead[0] >= ROWS ||
                newHead[1] < 0 || newHead[1] >= COLS)
                return -1;

            if (newHead[0] == food[0] && newHead[1] == food[1]) {
                score++;
                food = new int[]{new Random().nextInt(ROWS),
                                 new Random().nextInt(COLS)};
            } else {
                snake.removeLast();
            }

            for (int[] part : snake)
                if (part[0] == newHead[0] && part[1] == newHead[1])
                    return -1;

            snake.addFirst(newHead);
            init();
            return score;
        }

    }

}




























/*
static class SnakeGame {
    int ROWS = 10, COLS = 10;
    LinkedList<int[]> snake;
    int[] food;
    int score;
    char[][] board;

    public SnakeGame() {
        snake = new LinkedList<>();
        snake.add(new int[]{0, 0}); // 初始位置
        food = new int[]{3, 3}; // 初始食物位置
        score = 0;
        board = new char[ROWS][COLS];
        init();
    }

    // 初始化游戏界面
    private void init() {
        for (int i = 0; i < ROWS; i++)
            for (int j = 0; j < COLS; j++)
                board[i][j] = '-';

        for (int[] part : snake)
            board[part[0]][part[1]] = '*';

        board[food[0]][food[1]] = 'F';

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++)
                System.out.print(board[i][j] + " ");
            System.out.println();
        }
        System.out.println();
    }

    // 移动
    public int move(String dir) {
        int[] head = snake.getFirst();
        int[] newHead = new int[]{head[0], head[1]};

        // 根据方向更新新头部的位置
        switch (dir) {
            case "W":
                newHead[0]--;
                break;
            case "S":
                newHead[0]++;
                break;
            case "A":
                newHead[1]--;
                break;
            case "D":
                newHead[1]++;
                break;
        }

        // 判断是否出界
        if (newHead[0] < 0 || newHead[0] >= ROWS ||
            newHead[1] < 0 || newHead[1] >= COLS)
            return -1; // 游戏结束

        // 判断是否吃到食物
        if (newHead[0] == food[0] && newHead[1] == food[1]) {
            score++;
            food = new int[]{new Random().nextInt(ROWS),
                             new Random().nextInt(COLS)};
        } else {
            snake.removeLast(); // 移动后删除尾部，相当于向前移动
        }

        // 判断是否碰到自己
        for (int[] part : snake)
            if (part[0] == newHead[0] && part[1] == newHead[1])
                return -1; // 游戏结束

        snake.addFirst(newHead); // 更新蛇头位置
        init(); // 更新游戏界面
        return score; // 返回当前得分
    }

}
*/