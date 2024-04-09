/**
 * @copyright @lonch
 */
package com.lonch;

import com.lonch.util.MatrixNode;
import org.junit.Test;
import java.util.*;
import static com.lonch.util.MatrixNode.getMatrix;
import static com.lonch.util.MatrixNode.printMatrix;
import static org.junit.Assert.assertEquals;

/**
    [MATRIXLINKED] ||
    (简单)
    NO.5 定义链表来表示 N * N 的矩阵，节点内包括数值和
         四个指针分别为上、下、左、右
 */
@SuppressWarnings("all")
public class NO5_MatrixLinkedList {

    @Test
    public void test() {
        MatrixNode<Integer> head = init(5, 5);
        printMatrix(head);
        assertEquals("2\t3\t4\t2\t3\t4\t",
            getMatrix(head.down.right.down.right.down));
    }

    public MatrixNode<Integer> init(int row, int col) {
        // 2024/4/7 NO.1 没思路，可以看懂。4月18日 20:00 面试
        // 2024/4/8 NO.2 没思路，能看懂。至少要写 10 遍才行
        // 2024/4/9 NO.3 一遍过，做了几遍都是一遍过
        MatrixNode dummy = new MatrixNode(0);
        // TODO 你能做出来的，相信自己

        return dummy;
    }

    /*
    static class MatrixNode {
        private int val;
        private MatrixNode left, right, up, down;
        public MatrixNode(int val) {
            this.val = val;
            this.left = this.right = this.up = this.down = null;
        }
    }
    */

}






















/*
public static MatrixNode init(int row, int col) {
    MatrixNode dummy = new MatrixNode(0);
    MatrixNode row1 = dummy;
    for (int j = 1; j < col; j++) {
        row1.right = new MatrixNode(j);
        row1.right.left = row1;
        row1 = row1.right;
    }

    MatrixNode pre = dummy;
    for (int i = 1; i < row; i++) {
        MatrixNode rowHead = new MatrixNode(i);
        rowHead.up = pre;
        pre.down = rowHead;

        MatrixNode up = pre;
        MatrixNode right = rowHead;
        for (int j = 1; j < col; j++) {
            right.right = new MatrixNode(j);
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

static class MatrixNode {
    public int val;
    public MatrixNode left, right, up, down;

    public MatrixNode(int val) {
        this.val = val;
        this.left = null;
        this.right = null;
        this.up = null;
        this.down = null;
    }
}
*/