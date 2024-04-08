package com.lonch.util;

import java.util.ArrayList;
import java.util.List;

/**
    四方向链表
 */
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

    public static MatrixNode<Integer> init(int row, int col) {
        MatrixNode<Integer> dummy = new MatrixNode<>(0);
        MatrixNode<Integer> row1 = dummy;
        for (int j = 1; j < col; j++) {
            row1.right = new MatrixNode<>(j);
            row1.right.left = row1;
            row1 = row1.right;
        }

        MatrixNode<Integer> pre = dummy;
        for (int i = 1; i < row; i++) {
            MatrixNode<Integer> rowHead = new MatrixNode<>(i);
            rowHead.up = pre;
            pre.down = rowHead;

            MatrixNode<Integer> up = pre;
            MatrixNode<Integer> right = rowHead;
            for (int j = 1; j < col; j++) {
                right.right = new MatrixNode<>(j);
                right.right.left = right;

                right = right.right;
                up = up.right;

                right.up = up;
                up.down = right;
            }
            pre = rowHead; // 更新当前行的头节点，以便下一次迭代
        }
        return dummy;
    }

}
