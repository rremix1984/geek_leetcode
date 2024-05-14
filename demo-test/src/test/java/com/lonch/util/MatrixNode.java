/**
 * copyright 2020-2024
 */
package com.lonch.util;

import lombok.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

import static java.lang.Integer.MAX_VALUE;
import static java.lang.Integer.MIN_VALUE;
import static java.lang.System.out;

/**
 * 四方向链表
 */
@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@SuppressWarnings({"unused"})
public class MatrixNode<T> {

    @NonNull public T val;

    public int col, row;

    // 上、下、左、右四个指针
    public MatrixNode<T> left, right, up, down;

    public int dist = MAX_VALUE;

    public List<MatrixNode<T>> prevs = new ArrayList<>();

    public MatrixNode(T val, MatrixNode<T> left, MatrixNode<T> right, int row, int col) {
        this.val = val;
        this.left = left;
        this.right = right;
        this.row = row;
        this.col = col;
    }

    public MatrixNode(int row, int col) {
        this.row = row;
        this.col = col;
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

    public static MatrixNode<Character> initC(int n) {
        AtomicInteger c = new AtomicInteger(0);
        Supplier<Character> supplier =
                () -> (char)('A' + c.getAndIncrement() % 26);
        return init(n, n, supplier);
    }

    public static <T> MatrixNode<T> init(int n, Supplier<T> supplier) {
        return init(n, n, supplier);
    }

    public static MatrixNode<Integer> init(int n) {
        Supplier<Integer> supplier =
                new AtomicInteger(1)::getAndIncrement;
        return init(n, n, supplier);
    }

    public static MatrixNode<Integer> initIR(int n) {
        Supplier<Integer> supplier =
                () -> new Random().nextInt(10);
        return init(n, n, supplier);
    }

    public static <T> MatrixNode<T> init(int row, int col, Supplier<T> supplier) {
        MatrixNode<T> dummy = new MatrixNode<>(supplier.get());
        MatrixNode<T> row1 = dummy;
        for (int i = 1; i < row; i++) {
            row1.right = new MatrixNode<>(supplier.get());
            row1.right.left = row1;
            row1 = row1.right;
        }

        MatrixNode<T> pre = dummy;
        for (int i = 1; i < row; i++) {
            MatrixNode<T> newHead = new MatrixNode<>(supplier.get());
            newHead.up = pre;
            pre.down = newHead;

            MatrixNode<T> up = pre;
            MatrixNode<T> right = newHead;
            for (int j = 1; j < col; j++) {
                right.right = new MatrixNode<>(supplier.get());
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

    public static <T> void print(MatrixNode<T> head,
                               MatrixNode<T> start, MatrixNode<T> end) {
        MatrixNode<T> col = head;
        StringBuilder sb = new StringBuilder();
        while (col != null) {
            MatrixNode<T> row = col;
            while (row != null) {
                if (row == start || row == end)
                    if ((row.val + "").length() == 1)
                        out.printf(" (%s)", row.val);
                    else
                        out.printf("(%2s)", row.val);
                else
                    out.printf(" %2s ", row.val);
                row = row.right;
            }
            out.println();
            col = col.down;
        }
    }

    public static <T> void print(MatrixNode<T> head) {
        print(head, null, null);
    }

    public static <T> void print(List<List<MatrixNode<T>>> allPaths) {
        print(allPaths, null, null);
    }

    public static <T> void print(List<List<MatrixNode<T>>> allPaths,
                                 MatrixNode<T> start, MatrixNode<T> end) {
        out.println("\n All paths from start to end: " + allPaths.size());
        for (List<MatrixNode<T>> path : allPaths) {
            for (MatrixNode<T> node : path) {
                if (node == start || node == end) {
                    out.printf(" (%2s) -> ", node.val);
                    continue;
                }
                if ((node.val + "").length() == 1)
                    out.printf(" (%s) -> ", node.val);
                else
                    out.printf("(%2s) -> ", node.val);
            }
            out.println();
        }
    }

    public MatrixNode<T> pos(int n) {
        return pos(n, n);
    }

    public MatrixNode<T> pos(int row, int col) {
        MatrixNode<T> pr = this;
        while (row-- > 1)
            pr = pr.down;

        while (col-- > 1)
            pr = pr.right;

        return pr;
    }

}