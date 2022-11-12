package com.leetcode;

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
}