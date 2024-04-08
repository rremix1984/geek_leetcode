package com.lonch.util;

import java.util.ArrayList;
import java.util.List;

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
        StringBuilder sb = new StringBuilder();
        while (col != null) {
            MatrixNode row = col;
            while (row != null) {
                sb.append(row.val).append("\t");
                row = row.right;
            }
            sb.append("\n");
            col = col.down;
        }
        System.out.println(sb);
    }

    public static String getMatrix(MatrixNode head) {
        StringBuilder sb = new StringBuilder();
        MatrixNode col = head;
        while (col != null) {
            MatrixNode row = col;
            while (row != null) {
                sb.append(row.val).append("\t");
                row = row.right;
            }
            col = col.down;
        }
        return sb.toString();
    }

    public List<MatrixNode> getNeighbors() {
        List<MatrixNode> neighbors = new ArrayList<>();
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

}
