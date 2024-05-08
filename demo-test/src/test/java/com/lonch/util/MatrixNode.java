/**
 * copyright 2020-2024
 */
package com.lonch.util;

import com.sun.istack.internal.NotNull;
import lombok.*;

import java.util.*;

import static java.lang.Integer.MAX_VALUE;
import static java.lang.System.out;

/**
 * 四方向链表
 */
@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@SuppressWarnings({"all", "unused"})
public class MatrixNode<T> {

    @NonNull public T val;

    public int col,row;

    // 上、下、左、右四指针
    public MatrixNode<T> left, right, up, down;
    public int dist = MAX_VALUE;
    public List<MatrixNode> prevs = new ArrayList<>();

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

    public static <T> void printMatrix(MatrixNode<T> head) {
        MatrixNode<T> col = head;
        StringBuilder sb = new StringBuilder();
        while (col != null) {
            MatrixNode<T> row = col;
            while (row != null) {
                sb.append(row.val + "\t");
                row = row.right;
            }
            sb.append("\n");
            col = col.down;
        }
        out.println(sb);
    }

    public static MatrixNode<Integer> initInt(int row, int col) {
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

    public static MatrixNode<Integer> initIntRandom(int row, int col) {
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

    public static void printAllPaths(Set<List<MatrixNode<Integer>>> allPaths) {
        out.println("All paths from start to end:");
        for (List<MatrixNode<Integer>> path : allPaths) {
            int sum = 0;
            for (MatrixNode<Integer> node : path) {
                out.print("(" + node.val + ") -> ");
                sum += node.val;
            }
            out.println("end:" + sum);
        }
    }

    public static List<List<MatrixNode>> findShortestPaths(MatrixNode start, MatrixNode end) {
        List<List<MatrixNode>> res = new ArrayList<>();
        // 剪枝法
        dfs(res, new ArrayList<>(), start, end, new HashSet<>());
        return res;
    }

    // 递归搜索最短路径
    private static void dfs(List<List<MatrixNode>> res, List<MatrixNode> list,
                     MatrixNode start, MatrixNode end,
                     Set<MatrixNode> visited) {

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
                    cur -> dfs(res, list, (MatrixNode) cur, end, visited)
            );
        }

        // 回溯，移除当前节点，继续搜索其他可能的路径
        list.remove(start);
        visited.remove(start);
    }

}