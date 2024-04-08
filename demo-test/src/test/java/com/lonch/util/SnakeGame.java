package com.lonch.util;

import java.util.*;

public class SnakeGame {
    private static final int ROWS = 10;
    private static final int COLS = 10;

    private LinkedList<int[]> snake; // 贪吃蛇身体
    private int[] food; // 食物位置
    private int score; // 得分
    private char[][] board; // 游戏界面

    public SnakeGame() {
        snake = new LinkedList<>();
        snake.add(new int[]{0, 0}); // 初始位置
        food = new int[]{3, 3}; // 初始食物位置
        score = 0;
        board = new char[ROWS][COLS];
        initializeBoard();
    }

    // 初始化游戏界面
    private void initializeBoard() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                board[i][j] = '-';
            }
        }
        for (int[] part : snake) {
            board[part[0]][part[1]] = '*';
        }
        board[food[0]][food[1]] = 'F';
    }

    // 打印游戏界面
    private void printBoard() {
        System.out.println("Score: " + score);
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    // 移动
    public int move(String direction) {
        int[] head = snake.getFirst();
        int[] newHead = Arrays.copyOf(head, head.length);

        // 根据方向更新新头部的位置
        switch (direction) {
            case "U":
                newHead[0]--;
                break;
            case "D":
                newHead[0]++;
                break;
            case "L":
                newHead[1]--;
                break;
            case "R":
                newHead[1]++;
                break;
        }

        // 判断是否出界或者碰到自己
        if (newHead[0] < 0 || newHead[0] >= ROWS || newHead[1] < 0 || newHead[1] >= COLS) {
            return -1; // 游戏结束
        }

        // 判断是否吃到食物
        if (newHead[0] == food[0] && newHead[1] == food[1]) {
            score++;
            generateFood(); // 生成新的食物
        } else {
            snake.removeLast(); // 移动后删除尾部，相当于向前移动
        }

        // 判断是否碰到自己
        for (int[] part : snake) {
            if (part[0] == newHead[0] && part[1] == newHead[1]) {
                return -1; // 游戏结束
            }
        }

        snake.addFirst(newHead); // 更新蛇头位置
        updateBoard(); // 更新游戏界面
        return score; // 返回当前得分
    }

    // 更新游戏界面
    private void updateBoard() {
        initializeBoard();
    }

    // 生成食物
    private void generateFood() {
        Random rand = new Random();
        int row = rand.nextInt(ROWS);
        int col = rand.nextInt(COLS);
        food = new int[]{row, col};
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SnakeGame snakeGame = new SnakeGame();
        snakeGame.printBoard();

        while (true) {
            System.out.print("Enter direction (U/D/L/R): ");
            String direction = scanner.nextLine();
            int score = snakeGame.move(direction);
            if (score == -1) {
                System.out.println("Game Over! Final Score: " + snakeGame.score);
                break;
            }
            snakeGame.printBoard();
        }
    }
}
