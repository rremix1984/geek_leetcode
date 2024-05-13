/**
 * @copyright @lonch
 */
package com.lonch;

import com.lonch.util.MatrixNode;
import lombok.*;
import org.junit.Test;
import java.util.*;
import static com.lonch.util.MatrixNode.*;
import static com.lonch.util.MatrixNode.print;
import static java.lang.System.out;
import static org.junit.Assert.assertEquals;

/**
    [MATRIXLINKED] ||||||||||||||
    (中等)
    NO.5 定义链表来表示 N * N 的矩阵，节点内包括数值和
         四个指针分别为上、下、左、右
 */
@SuppressWarnings("all")
public class NO5_MatrixLinkedList {

    @Test
    public void test() {
        // 2024/4/7  NO.1  没思路，可以看懂。4月18日 20:00 面试
        // 2024/4/8  NO.2  没思路，能看懂。至少要写 10 遍才行
        // 2024/4/9  NO.3  一遍过，做了几遍都是一遍过
        // 2024/4/11 NO.4  没做对，思路全对，但是忘了细节
        // 2024/4/12-16、18
        //           NO.5-10 一遍过
        // 2024/4/30 NO.11 大部分做对了，还是有些细节没记住
        // 2024/5/6  NO.12 大致思路对，忘了怎么做了，手生了
        // 2024/5/7、9-10、12
        //           NO.13-16 一遍过
        MatrixNode<Character> head = initC(6);
        print(head);

    }

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

@RequiredArgsConstructor
static class MatrixNode {
    @NonNull public int val;
    public MatrixNode left, right, up, down;
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
    out.println(sb);
}
*/