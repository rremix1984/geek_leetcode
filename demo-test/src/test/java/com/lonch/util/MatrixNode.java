/**
 * copyright 2020-2024
 */
package com.lonch.util;

import lombok.*;
import java.util.*;
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

    public MatrixNode<T> head, tail;

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

    public static MatrixNode<Character> initC(int n) {
        return initC(n, n);
    }

    public static MatrixNode<Character> initC(int row, int col) {
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

    public static <T> void printMatrix(MatrixNode<T> head,
                                       MatrixNode<T> start, MatrixNode<T> end) {
        MatrixNode<T> col = head;
        StringBuilder sb = new StringBuilder();
        while (col != null) {
            MatrixNode<T> row = col;
            while (row != null) {
                if (row == start || row == end) {
                    if ((row.val + "").length() == 1) {
                        out.printf(" (%s)", row.val);
                    } else {
                        out.printf("(%2s)", row.val);
                    }
                } else {
                    out.printf(" %2s ", row.val);
                }
                row = row.right;
            }
            out.println();
            col = col.down;
        }
    }

    public static <T> void printMatrix(MatrixNode<T> head) {
        printMatrix(head, null, null);
    }

    public static MatrixNode<Integer> initI(int n) {
        return initI(n, n);
    }

    public static MatrixNode<Integer> initI(int row, int col) {
        int c = 1;
        MatrixNode<Integer> dummy = new MatrixNode<>(c++);
        MatrixNode<Integer> row1 = dummy;
        for (int i = 1; i < row; i++) {
            row1.right = new MatrixNode<>(c++);
            row1.right.left = row1;
            row1 = row1.right;
        }

        MatrixNode<Integer> pre = dummy;
        for (int i = 0; i < row - 1; i++) {
            MatrixNode<Integer> newHead = new MatrixNode<>(c++);
            newHead.up = pre;
            pre.down = newHead;

            MatrixNode<Integer> up = pre;
            MatrixNode<Integer> right = newHead;
            for (int j = 1; j < col; j++) {
                right.right = new MatrixNode<>(c++);
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

    public static MatrixNode<Integer> initIR(int n) {
        return initIR(n, n);
    }

    public static MatrixNode<Integer> initIR(int row, int col) {
        MatrixNode<Integer> dummy = new MatrixNode<>(new Random().nextInt(10));
        MatrixNode<Integer> row1 = dummy;
        for (int i = 1; i < row; i++) {
            row1.right = new MatrixNode<>(i);
            row1.right.left = row1;
            row1 = row1.right;
        }

        MatrixNode<Integer> pre = dummy;
        for (int i = 1; i < row; i++) {
            MatrixNode<Integer> newHead = new MatrixNode<>(
                    new Random().nextInt(10));
            newHead.up = pre;
            pre.down = newHead;

            MatrixNode<Integer> up = pre;
            MatrixNode<Integer> right = newHead;
            for (int j = 1; j < col; j++) {
                right.right = new MatrixNode<>(
                        new Random().nextInt(10));
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

    public static char nextChar() {
        return (char) ('a' + (c++ % 26));
    }

    public static <T> void printAllPaths(List<List<MatrixNode<T>>> allPaths) {
        out.println("\n All paths from start to end: " + allPaths.size());
        for (List<MatrixNode<T>> path : allPaths) {
            for (MatrixNode<T> node : path) {
                if ((node.val + "").length() == 1) {
                    out.printf(" (%s) -> ", node.val);
                } else {
                    out.printf("(%2s) -> ", node.val);
                }
            }
            out.println();
        }
    }

    public static  <T> List<List<MatrixNode<T>>> findShortestPaths(MatrixNode<T> start, MatrixNode<T> end) {
        List<List<MatrixNode<T>>> res = new ArrayList<>();
        // 剪枝法
        dfs(res, new ArrayList<>(), start, end, new HashSet<>());
        return res;
    }

    // 递归搜索最短路径
    public static <T> void dfs(List<List<MatrixNode<T>>> res,
                                List<MatrixNode<T>> list,
                                 MatrixNode<T> start, MatrixNode<T> end,
                                 Set<MatrixNode<T>> visited) {

        if (visited.contains(start))
            return;

        // 加入当前节点到路径中
        list.add(start);
        visited.add(start);

        // 如果当前节点是目标节点，且路径比已找到的最短路径短，则更新最短路径列表
        if (start == end) {
            if (res.isEmpty() || list.size() < res.get(0).size()) {
                res.clear();
                res.add(new ArrayList<>(list));
            } else if (list.size() == res.get(0).size())
                res.add(new ArrayList<>(list));
        } else {
            // 否则，继续搜索当前节点的相邻节点
            start.getNeighbors().forEach(
                    cur -> dfs(res, list, cur, end, visited)
            );
        }

        // 回溯，移除当前节点，继续搜索其他可能的路径
        list.remove(start);
        visited.remove(start);
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

    public MatrixNode<T> getFirst() {
        return head;
    }

    // 在链表头部添加节点
    public void addFirst(T value) {
        MatrixNode<T> newNode = new MatrixNode<>(value);
        newNode.row = ((int[])value)[0];
        newNode.col = ((int[])value)[1];
        if (head == null) {
            tail = newNode;
            head = tail;
            return;
        }

        newNode.right = head;
        head.left = newNode;
        head = newNode;
    }

    // 在链表尾部添加节点
    public void addLast(T value) {
        MatrixNode<T> newNode = new MatrixNode<>(value);
        newNode.row = ((int[])value)[0];
        newNode.col = ((int[])value)[1];
        if (tail == null) {
            head = tail = newNode;
        } else {
            tail.right = newNode;
            newNode.left = tail;
            tail = newNode;
        }
    }

    // 移除尾部节点
    public void removeLast() {
        if (tail == null)
            return;

        if (tail.left != null) {
            tail = tail.left;
            tail.right = null;
        } else {
            head = tail = null;
        }
    }

}