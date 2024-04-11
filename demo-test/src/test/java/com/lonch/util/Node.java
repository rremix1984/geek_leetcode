/**
 * @copyright wxz
 */
package com.lonch.util;

import lombok.Getter;
import lombok.Setter;
import java.util.*;

@Setter
@Getter
public class Node<E> {

    public E data;

    public Node<E> parent;

    public Node<E> left;

    public Node<E> right;

    // 构造器
    public Node(E data) {
        this.data = data;
    }

    public void setLeft(Node<E> left) {
        this.left = left;
        if (left != null)
            left.setParent(this);
    }

    public void setRight(Node<E> right) {
        this.right = right;
        if (right != null)
            right.setParent(this);
    }

    public static <E> Node<E> copy(Node<E> root) {
        if (root == null)
            return null;

        Node<E> node = new Node<>(root.data);

        node.left = copy(root.left);
        if (node.left != null)
            node.left.parent = node; // 设置父节点

        node.right = copy(root.right);
        if (node.right != null)
            node.right.parent = node; // 设置父节点

        return node;
    }

    public static Node<Character> cTree(int depth, int currentIndex) {
        if (depth == 0)
            return null;

        Node<Character> node = new Node<>((char) ('A' + currentIndex % 26));
        node.left = cTree(depth - 1, 2 * currentIndex + 1);
        if (node.left != null)
            node.left.parent = node;

        node.right = cTree(depth - 1, 2 * currentIndex + 2);
        if (node.right != null)
            node.right.parent = node;

        return node;
    }

    public static String travel(Node<String> node) {
        if (node == null)
            return "";

        StringBuilder sb = new StringBuilder();
        dfs(node, new HashSet<>(), sb);
        return sb.toString();
    }

    private static void dfs(Node<String> node, HashSet<Node<String>> visit, StringBuilder sb) {
        if (node == null || visit.contains(node))
            return;

        sb.append(node.data);
        visit.add(node);

        dfs(node.left, visit, sb);
        dfs(node.right, visit, sb);
        dfs(node.parent, visit, sb);
    }

    public static <T> void printTree(Node<T> root) {
        if (root == null)
            return;

        Deque<Node<T>> queue = new LinkedList<>();
        queue.add(root);

        Node<T> next = root.parent;
        while (next != null && next != root) {
            queue.offer(next);
            next = next.parent;
        }

        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size > 0) {
                Node<T> node = queue.poll();
                if (node == null)
                    continue;

                System.out.print(node.data + " ");

                if (node.left != null)
                    queue.add(node.left);

                if (node.right != null)
                    queue.add(node.right);

                size--;
            }
            System.out.println(); // 每层遍历结束后换行
        }
    }
}