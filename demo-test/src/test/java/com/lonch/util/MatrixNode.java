package com.lonch.util;

import java.util.ArrayList;
import java.util.List;

/**
    四方向链表
 */
@SuppressWarnings("unused")
public class MatrixNode<T> {

    public T val;
    public MatrixNode<T> left, right, up, down;

    public MatrixNode(T val) {
        this.val = val;
        this.left = null;
        this.right = null;
        this.up = null;
        this.down = null;
    }

    public static <T> void printMatrix(MatrixNode<T> head) {
        MatrixNode<T> col = head;
        StringBuilder sb = new StringBuilder();
        while (col != null) {
            MatrixNode<T> row = col;
            while (row != null) {
                sb.append(row.val).append("\t");
                row = row.right;
            }
            sb.append("\n");
            col = col.down;
        }
        System.out.println(sb);
    }

    public static <T> String getMatrix(MatrixNode<T> head) {
        StringBuilder sb = new StringBuilder();
        MatrixNode<T> col = head;
        while (col != null) {
            MatrixNode<T> row = col;
            while (row != null) {
                sb.append(row.val).append("\t");
                row = row.right;
            }
            col = col.down;
        }
        return sb.toString();
    }

    public List<MatrixNode<T>> getNeighbors() {
        List<MatrixNode<T>> neighbors = new ArrayList<>();
        if (this.left != null)
            neighbors.add(this.left);

        if (this.right != null)
            neighbors.add(this.right);

        if (this.up != null)
            neighbors.add(this.up);

        if (this.down != null)
            neighbors.add(this.down);

        return neighbors;
    }

    public static MatrixNode<Character> init(int row, int col) {
        MatrixNode<Character> dummy = new MatrixNode<>(nextChar());
        MatrixNode<Character> row1 = dummy;
        for (int i = 1; i < row; i++) {
            row1.right = new MatrixNode<>(nextChar());
            row1.right.left = row1;
            row1 = row1.right;
        }

        MatrixNode<Character> pre = dummy;
        for (int i = 1; i < row; i++) {
            MatrixNode<Character> newHead = new MatrixNode<>(nextChar());
            newHead.up = pre;
            pre.down = newHead;

            MatrixNode<Character> up = pre;
            MatrixNode<Character> right = newHead;
            for (int j = 1; j < col; j++) {
                right.right = new MatrixNode<>(nextChar());
                right.right.left = right;

                right = right.right;
                up = up.right;

                right.up = up;
                up.down = right;
            }
            pre = newHead;
        }
        return dummy;
    }
    static int c;
    private static char nextChar() {
        return (char) ('a' + (c++ % 26));
    }

}
