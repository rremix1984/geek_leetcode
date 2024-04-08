package com.lonch;

import org.junit.Test;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

/**
 * 贪吃蛇游戏
 */
public class NO9_SnakeGame {

    @Test
    public void test() {
        SnakeGame snakeGame = new SnakeGame();
        System.out.println("Initial Score: " + snakeGame.score);

        // 向右移动一次
        int score1 = snakeGame.move("R");
        System.out.println("Score after moving right: " + score1);

        // 向下移动一次
        int score2 = snakeGame.move("D");
        System.out.println("Score after moving down: " + score2);

        // 向下移动一次
        int score3 = snakeGame.move("D");
        System.out.println("Score after moving down: " + score3);

        // 向左移动一次
        int score4 = snakeGame.move("L");
        System.out.println("Score after moving left: " + score4);

        // 向上移动一次
        int score5 = snakeGame.move("U");
        System.out.println("Score after moving up: " + score5);

        // 再向上移动一次，游戏结束
        int score6 = snakeGame.move("U");
        if (score6 == -1)
            System.out.println("Game Over! Final Score: " + snakeGame.score);
    }

    static class SnakeGame {
        private static final int ROWS = 10;
        private static final int COLS = 10;

        private LinkedList<int[]> snake; // 贪吃蛇身体
        private int[] food; // 食物位置
        private int score; // 得分

        public SnakeGame() {
            snake = new LinkedList<>();
            snake.add(new int[]{0, 0}); // 初始位置
            food = new int[]{3, 3}; // 初始食物位置
            score = 0;
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
            for (int[] part : snake)
                if (part[0] == newHead[0] && part[1] == newHead[1])
                    return -1; // 游戏结束

            snake.addFirst(newHead); // 更新蛇头位置
            return score; // 返回当前得分
        }

        // 生成食物
        private void generateFood() {
            Random rand = new Random();
            int row = rand.nextInt(ROWS);
            int col = rand.nextInt(COLS);
            food = new int[]{row, col};
        }

    }

}
