package com.lonch;

import com.lonch.util.MatrixNode;
import org.junit.Test;

import java.util.*;

import static com.lonch.util.MatrixNode.printMatrix;

/**
   定义链表来表示 N * N 的矩阵，节点内包括数值和四个指针分别为上下左右
 */
public class NO5_MatrixLinkedList {

    @Test
    public void test() {
        MatrixNode head = init(5);
        printMatrix(head);

        MatrixNode start = head.right.right.down;
        MatrixNode end = head.down.down.down;

        List<MatrixNode> list = findShortestPath(start, end);
        list.stream().forEach(node -> System.out.print(node.val + " "));
    }

    // 示例方法，用于找到一条最短路径（仅供参考，未完全实现所有最短路径的查找）
    public List<MatrixNode> findShortestPath(MatrixNode start, MatrixNode end) {
        // 使用广度优先搜索找到一条最短路径
        // 注意：这个示例代码未完全实现功能，仅为思路说明
        Queue<MatrixNode> queue = new LinkedList<>();
        Map<MatrixNode, MatrixNode> prev = new HashMap<>();
        Set<MatrixNode> visited = new HashSet<>();

        queue.offer(start);
        visited.add(start);
        prev.put(start, null); // 起始节点的前一个节点设置为null

        while (!queue.isEmpty()) {
            MatrixNode current = queue.poll();
            if (current == end) {
                break; // 找到终点
            }

            // 遍历当前节点的所有邻居节点
            for (MatrixNode neighbor : Arrays.asList(current.up, current.down, current.left, current.right)) {
                if (neighbor != null && !visited.contains(neighbor)) {
                    queue.offer(neighbor);
                    visited.add(neighbor);
                    prev.put(neighbor, current);
                }
            }
        }

        // 回溯路径
        List<MatrixNode> path = new ArrayList<>();
        for (MatrixNode at = end; at != null; at = prev.get(at)) {
            path.add(at);
        }
        Collections.reverse(path);
        return path; // 返回路径列表
    }

    // 构造方法，用于初始化矩阵
    public MatrixNode init(int n) {
        MatrixNode head = new MatrixNode(0); // 创建头节点
        // 初始化第一行
        MatrixNode row1 = head;
        for (int j = 1; j < n; j++) {
            row1.right = new MatrixNode(j);
            row1.right.left = row1;
            row1 = row1.right;
        }

        // 初始化剩余行
        MatrixNode preRow = head;
        for (int i = 1; i < n; i++) {
            MatrixNode rowHead = new MatrixNode(i);
            rowHead.up = preRow;
            preRow.down = rowHead;

            MatrixNode up = preRow; // 用于连接上下节点的临时节点
            MatrixNode right = rowHead;
            for (int j = 1; j < n; j++) {
                right.right = new MatrixNode(j);
                right.right.left = right;
                right = right.right;

                up = up.right; // 移动tempUp到下一个节点
                right.up = up;
                up.down = right;
            }
            preRow = rowHead; // 更新当前行的头节点，以便下一次迭代
        }
        return head;
    }

}





















/*
public void init(int n) {
    MatrixNode head = new MatrixNode(0); // 创建头节点
    // 初始化第一行
    MatrixNode row1 = head;
    for (int j = 1; j < n; j++) {
        row1.right = new MatrixNode(j);
        row1.right.left = row1;
        row1 = row1.right;
    }

    // 初始化剩余行
    MatrixNode preRow = head;
    for (int i = 1; i < n; i++) {
        MatrixNode rowHead = new MatrixNode(i);
        rowHead.up = preRow;
        preRow.down = rowHead;

        MatrixNode up = preRow; // 用于连接上下节点的临时节点
        MatrixNode right = rowHead;
        for (int j = 1; j < n; j++) {
            right.right = new MatrixNode(j);
            right.right.left = right;
            right = right.right;

            up = up.right; // 移动tempUp到下一个节点
            right.up = up;
            up.down = right;
        }
        preRow = rowHead; // 更新当前行的头节点，以便下一次迭代
    }
}
*/