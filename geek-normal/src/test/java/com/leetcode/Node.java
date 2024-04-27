package com.leetcode;

import static java.lang.System.out;
import static org.junit.Assert.assertEquals;

public class Node {

    public int val;
    public Node left;
    public Node right;
    public Node next;

    public Node() {}

    public Node(int _val) {
        val = _val;
    }

    public Node(int _val, Node _left, Node _right, Node _next) {
        val = _val;
        left = _left;
        right = _right;
        next = _next;
    }

    public Node(int _val, Node _left, Node _right) {
        val = _val;
        left = _left;
        right = _right;
    }

    @Override
    public boolean equals(Object obj) {
        Node v = (Node) obj;
        return nodeEqual(v, this);
    }

    private boolean nodeEqual(Node p, Node q) {
        if (p == null && q == null)
            return true;

        if (p == null || q == null)
            return false;

        if (p.val == q.val)
            return true;

        return nodeEqual(p.left, q.left) && nodeEqual(p.right, q.right);
    }

    public static void printTree(Node root) {
        Node levelStart = root;
        while (levelStart != null) {
            Node cur = levelStart;
            levelStart = null;
            Node nextLevelStart = null;

            // 遍历当前层，同时找到下一层的起点
            while (cur != null) {
                out.print(cur.val + " ");
                // 设置下一层的起始点
                if (nextLevelStart == null)
                    nextLevelStart = (cur.left != null) ? cur.left : cur.right;

                // 移动到同一层的下一个节点
                cur = cur.next;
            }
            // 移动到下一层的第一个节点
            if (levelStart == null) {
                levelStart = nextLevelStart;
            }
            // 每层的结束标志
            out.println("#");
        }
    }

    public static Node cTree(Integer... values) {
        Node[] nodes = new Node[values.length];
        for (int i = 0; i < values.length; i++) {
            if (values[i] != null) {
                nodes[i] = new Node(values[i]);
            }
        }
        for (int i = 0; i < values.length; i++) {
            if (nodes[i] != null) {
                int leftIndex = 2 * i + 1;
                int rightIndex = 2 * i + 2;
                if (leftIndex < values.length) {
                    nodes[i].left = nodes[leftIndex];
                }
                if (rightIndex < values.length) {
                    nodes[i].right = nodes[rightIndex];
                }
            }
        }
        return nodes[0];
    }

    public static void assertNextNode(Node node, Node expectedNext) {
        assertEquals(expectedNext, node.next);
    }

}