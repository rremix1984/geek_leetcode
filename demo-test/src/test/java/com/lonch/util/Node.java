package com.lonch.util;

import java.util.LinkedList;
import java.util.Queue;

public class Node<E> {
    public E data;
    public Node<E> parent;
    public Node<E> left;
    public Node<E> right;

    // 构造器
    public Node(E data) {
        this.data = data;
    }

    // getter 和 setter 方法
    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }

    public Node<E> getParent() {
        return parent;
    }

    public void setParent(Node<E> parent) {
        this.parent = parent;
    }

    public Node<E> getLeft() {
        return left;
    }

    public void setLeft(Node<E> left) {
        this.left = left;
        if (left != null) {
            left.setParent(this);
        }
    }

    public Node<E> getRight() {
        return right;
    }

    public void setRight(Node<E> right) {
        this.right = right;
        if (right != null) {
            right.setParent(this);
        }
    }

    // 层次遍历打印二叉树
    public static void printTree(Node<Character> root) {
        if (root == null)
            return;

        Queue<Node<Character>> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size > 0) {
                Node<Character> node = queue.poll();
                System.out.print(node.data + " ");
                if (node.left != null)
                    queue.offer(node.left);

                if (node.right != null)
                    queue.offer(node.right);

                if (--size == 0)
                    System.out.println();
            }

        }
        System.out.println();
    }

    public static Node<Character> cTree(int depth, int currentIndex) {
        if (depth == 0)
            return null;

        Node<Character> node = new Node<>((char) ('A' + currentIndex % 26));
        node.left = cTree(depth - 1, 2 * currentIndex + 1);
        if (node.left != null) {
            node.left.parent = node;
        }
        node.right = cTree(depth - 1, 2 * currentIndex + 2);
        if (node.right != null) {
            node.right.parent = node;
        }
        return node;
    }


}