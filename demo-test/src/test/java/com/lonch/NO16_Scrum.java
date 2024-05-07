package com.lonch;

import com.lonch.util.MatrixNode;
import org.junit.Test;
import java.util.Random;
import java.util.Scanner;
import static java.lang.System.in;

/**
    [ARRAY] |
    [简单]
    NO.1 之前 定义/构建/赋值/遍历，都是必考题！
    1）各种算法题型之间的互相转化
       例如双向链表（定义）加个节点改成三向链表...(让改名字 表现出二叉树？
       会问什么是满二叉树...每个节点进行顺序赋值，并且是一次递增 赋值构造4层
       满二叉树）实现一个方法，构造一颗N层的三叉树....转化成四向链表
       再用四向链表构造矩阵

    1. 双向链表定义（注意使用泛型）——带父节点的二叉树定义（或赋值构造满二叉树）
    2. 实现一个方法(参数是整型数字,构造一颗N层的满二叉树) （注意使用递归实现）
    3. 转换成双、三、四向链表或是四项链表构造矩阵（随机一个）（遍历 必考点）
       注意：形式上定义3棵满二叉树，3颗满二叉树的根节点相互为父节点（比如第一颗树的根
       节点的父节点为第二颗树的根节点，第二颗树的父节点为第三颗树的根节点，第三颗树的
       父节点为第一颗树的根节点），写一个算法，随机给定节点，从该给定的节点开始遍历这
       3棵树，不能有重复的遍历，就是每个节点只能出现一次，而且不允许先找个根节点然后再
       遍历整棵树，只能从给定的节点开始变遍历，而且不能使用任何中间介质做排重或存储
    5. 问双向链表、三向链表、四向链表，然后用四向链表结构构建一个双向链表，然后再把
       双向链表改为蛇形链表，最后是用蛇形链表写一个贪吃蛇的弹出和吃入方法

    问答环节（准备技术文档）
    1、数据库读写分离，如何保证主库写操作的效率？

    2、数据缓存如何存储？

    3、Mybatis框架优势？

    4、数据更新语句为何使用主键作为限制条件？

    5、... ...

*/
public class NO16_Scrum {

    public static void main(String[] args) {
        // TODO 注意：涉及到读屏幕输入的时候，必须用 main 函数才可以
    }

}














/*
// 方法1：
static class Game {
    int score;
    int ROWS, COLS;
    char[][] board;
    int[] food;
    MatrixNode<Character> snake;

    public Game() {
        ROWS = 10;
        COLS = 10;
        board = new char[ROWS][COLS];
        food = new int[]{3, 3};
        snake = new MatrixNode<>('*');
        init();
    }

    public void init() {
        for (int i = 0; i < ROWS; i++)
            for (int j = 0; j < COLS; j++)
                board[i][j] = '-';

        board[food[0]][food[1]] = 'F';

        MatrixNode<Character> cur = snake;
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

    public int move(char dir) {
        int[] pos = {snake.row, snake.col};
        switch(dir) {
            case 'W':
                pos[0]--;
                break;
            case 'S':
                pos[0]++;
                break;
            case 'A':
                pos[1]--;
                break;
            case 'D':
                pos[1]++;
                break;
        }

        if (pos[0] < 0 || pos[0] >= ROWS
                || pos[1] < 0 || pos[1] >= COLS || board[pos[0]][pos[1]] == '*') {
            return -1;
        }

        //addDFirst
        MatrixNode<Character> nHead = new MatrixNode<>(
                '*', null, snake, pos[0], pos[1]);
        snake.left = nHead;
        snake = nHead;

        if (food[0] == pos[0] && food[1] == pos[1]) {
            score++;
            food = new int[]{new Random().nextInt(ROWS),
                    new Random().nextInt(COLS)};
        } else {
            // removeLast
            MatrixNode<Character> p = nHead;
            while (p.right != null) {
                p = p.right;
            }
            p.left.right = null;
        }
        init();
        return score;
    }
}

static class MatrixNode<E> {
    E data;
    int row, col;
    MatrixNode<E> left, right, down, up;
    public MatrixNode(E data) {
        this.data = data;
    }

    public MatrixNode(E data, MatrixNode<E> left,
                      MatrixNode<E> right, int row, int col) {
        this.data = data;
        this.left = left;
        this.right = right;
        this.row = row;
        this.col = col;
    }
}

public static void main(String[] args) {
    Game g = new Game();
    while (g.move(new Scanner(in).nextLine().charAt(0)) != -1)
        out.println("W/S/A/D");
    out.println("game over! score" + g.score);
}
*/