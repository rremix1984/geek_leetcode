/**
 * @copyright
 */
package com.lonch;

import com.lonch.util.MatrixNode;
import lombok.Getter;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import static java.lang.System.*;
import static java.util.Arrays.fill;

/**
    |
    [简单]
    NO.17 贪吃蛇

    问双向链表、三向链表、四向链表，然后用四向链表结构构建一个双向链表，
    然后再把双向链表改为蛇形链表，最后是用蛇形链表写一个贪吃蛇的弹出和吃入方法。
    @author wangxiaozhe
 */
@SuppressWarnings("all")
public class NO17_SnakeGameII {

    public static void main(String[] args) {
        // 2024/5/10 NO.1 一遍过
        // 2024/5/11 NO.2 没做对，要重复的练习才行
        Game g = new Game();
        while (g.move(new Scanner(in)
                .nextLine().toUpperCase().charAt(0)))
            out.println("W/A/S/D");
        out.println("game over");
    }

    static class Game{
        int ROWS = 10, COLS = 10;
        DBLink<MatrixNode> snake;
        char[][] board;
        int[] food;
        public Game() {
            food = new int[]{3, 3};
            snake = new DBLink<>();
            snake.addFirst(new MatrixNode<>(0, 0));
            board = new char[ROWS][COLS];
            init();
        }
        public void init() {
            for (int i = 0; i < ROWS; i++)
                Arrays.fill(board[i], '-');
            board[food[0]][food[1]] = 'F';
            MatrixNode cur = snake.getFirst();
            while (cur != null) {
                board[cur.row][cur.col] = '*';
                cur = cur.right;
            }
            for (int i = 0; i < ROWS; i++) {
                for (int j = 0; j < COLS; j++)
                    out.print(board[i][j] + " ");
                out.println();
            }
        }
        public boolean move(char dir) {
            MatrixNode head = snake.getFirst();
            int row = head.row;
            int col = head.col;
            switch (dir) {
                case 'W': row--; break;
                case 'S': row++; break;
                case 'A': col--; break;
                case 'D': col++; break;
            }
            if (row < 0 || col < 0 || row >= ROWS || col >= COLS
                || board[row][col] == '*') {
                return false;
            }
            MatrixNode nHead = new MatrixNode(row, col);
            snake.addFirst(nHead);
            if (food[0] == row && food[1] == col) {
                do {
                    food = new int[]{new Random().nextInt(ROWS),
                            new Random().nextInt(COLS)};
                } while (contains(snake, food));
            } else {
                snake.removeLast();
            }
            init();
            return true;
        }

        private boolean contains(DBLink<MatrixNode> snake, int[] food) {
            MatrixNode cur = snake.head;
            while (cur != null) {
                if (cur.row == food[0] && cur.col == food[1])
                    return true;
                cur = cur.right;
            }
            return false;
        }
    }

    static class DBLink<E> {
        MatrixNode<E> head;
        MatrixNode<E> tail;

        public void removeLast() {
            if (tail == null)
                return;

            if (tail.left == null) {
                head = tail = null;
            } else {
                tail.left.right = null;
                tail = tail.left;
            }
        }

        public void addFirst(MatrixNode node) {
            if (head == null) {
                head = tail = node;
                return;
            }
            head.left = node;
            node.right = head;
            head = node;
        }

        public MatrixNode<E> getFirst() {
            return head;
        }
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
        // 区别点 2：用 position替代原有的 int[] 数组
        //********************
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

// 方法3：用四向链表结构构建一个双向链表，
// 然后再把双向链表改为蛇形链表，最后是用蛇形链表写一个贪吃蛇的
// (弹出) addFirst
// (吃入) removeLast 方法。
static class SnakeGame {

    private int ROWS = 10;
    private int COLS = 10;
    private char[][] board = new char[ROWS][COLS];
    private DoubleLinkedList snake = new DoubleLinkedList();
    private int[] food;

    public SnakeGame() {
        snake.addFirst(new MatrixNode(0, 0)); // 初始蛇头位置
        board[0][0] = '*';
        food = new int[]{3, 3};
        init();
    }

    public void init() {
        for (int i = 0; i < ROWS; i++)
            fill(board[i], '-');

        board[food[0]][food[1]] = 'F';

        MatrixNode tail = snake.getHead();
        while (tail != null) {
            board[tail.row][tail.col] = '*';
            tail = tail.right;
        }

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++)
                System.out.print(board[i][j] + " ");
            System.out.println();
        }
    }

    public boolean move(char dir) {
        MatrixNode head = snake.getHead();
        int newRow = head.row;
        int newCol = head.col;

        switch (dir) {
            case 'W': newRow--; break;
            case 'S': newRow++; break;
            case 'A': newCol--; break;
            case 'D': newCol++; break;
        }

        if (newRow < 0 || newRow >= ROWS || newCol < 0 || newCol >= COLS
                || board[newRow][newCol] == '*')
            return false;

        MatrixNode newNode= new MatrixNode(newRow, newCol);
        snake.addFirst(newNode);
        if (newRow == food[0] && newCol == food[1])
            food = new int[]{new Random().nextInt(ROWS),
                    new Random().nextInt(COLS)};
        else
            snake.removeLast();

        init();
        return true;
    }
}

@Getter
static class DoubleLinkedList<T> {
    private MatrixNode<T> head;
    private MatrixNode<T> tail;

    public void addFirst(MatrixNode newNode) {
        if (head == null) {
            head = tail = newNode;
            return;
        }
        newNode.right = head;
        head.left = newNode;
        head = newNode;
    }

    public void removeLast() {
        if (tail == null)
            return;

        if (tail.left != null) {
            tail.left.right = null;
            tail = tail.left;
        } else {
            head = tail = null;
        }
    }

    public MatrixNode getHead() {
        return head;
    }

}
*/