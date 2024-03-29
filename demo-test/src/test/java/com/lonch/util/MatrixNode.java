package com.lonch.util;

/**
    四方向链表
 */
public class MatrixNode {
    public int val;
    public MatrixNode left, right, up, down;

    public MatrixNode(int val) {
        this.val = val;
        this.left = null;
        this.right = null;
        this.up = null;
        this.down = null;
    }

    public static void printMatrix(MatrixNode head) {
        MatrixNode col = head;
        while (col != null) {
            MatrixNode row = col;
            while (row != null) {
                System.out.printf("%d\t", row.val);
                row = row.right;
            }
            System.out.println();
            col = col.down;
        }
    }

}
