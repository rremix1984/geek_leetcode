/**
 * copyright 2024-2025
 */
package com.lonch.util;

import lombok.*;
import java.util.*;

import static java.lang.Integer.MAX_VALUE;
import static java.lang.System.out;

/**
 * Node
 * @author wangxiaozhe
 */
@Setter
@Getter
@NoArgsConstructor
@RequiredArgsConstructor
@SuppressWarnings("unused")
public class Node<E> {

    @NonNull public E data;

    public Node<E> parent, left, right;

    boolean visit;

    public int dist = MAX_VALUE;

    public void setLeft(Node<E> left) {
        this.left = left;
        if (left != null)
            left.parent = this;
    }

    public void setRight(Node<E> right) {
        this.right = right;
        if (right != null)
            right.parent = this;
    }

    public Node<E> copy() {
        Node<E> node = new Node<>(this.data);
        if (this.left != null) {
            node.left = this.left.copy();
            node.left.parent = node;
        }
        if (this.right != null) {
            node.right = this.right.copy();
            node.right.parent = node;
        }
        return node;
    }

    public static Node<Character> cTree(int depth, int idx) {
        if (depth == 0)
            return null;

        Node<Character> node = new Node<>((char) ('A' + idx % 26));
        node.left = cTree(depth - 1, 2 * idx + 1);
        if (node.left != null)
            node.left.parent = node;

        node.right = cTree(depth - 1, 2 * idx + 2);
        if (node.right != null)
            node.right.parent = node;

        return node;
    }

    public static <E> String travel(Node<E> node) {
        if (node == null)
            return "";

        StringBuilder sb = new StringBuilder();
        dfs(node, new HashSet<>(), sb);
        return sb.toString();
    }

    private static <E> void dfs(Node<E> node, HashSet<Node<E>> visit, StringBuilder sb) {
        if (node == null || visit.contains(node))
            return;

        sb.append(node.data).append(" ");
        visit.add(node);

        dfs(node.left, visit, sb);
        dfs(node.right, visit, sb);
        dfs(node.parent, visit, sb);
    }

    public static <E> void printTree(Node<E> root) {
        if (root == null)
            return;

        Deque<Node<E>> queue = new LinkedList<>();
        queue.add(root);

        Node<E> next = root.parent;
        while (next != null && next != root) {
            queue.offer(next);
            next = next.parent;
        }

        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size > 0) {
                Node<E> node = queue.poll();
                if (node == null)
                    continue;

                out.print(node.data + " ");

                if (node.left != null)
                    queue.add(node.left);

                if (node.right != null)
                    queue.add(node.right);

                size--;
            }
            out.println(); // 每层遍历结束后换行
        }
    }

    // 返回节点的邻居节点列表
    public List<Node<E>> getNeighbors() {
        List<Node<E>> neighbors = new ArrayList<>();
        if (parent != null)
            neighbors.add(parent);
        if (left != null)
            neighbors.add(left);
        if (right != null)
            neighbors.add(right);
        return neighbors;
    }

    @Override
    public String toString() {
        return "[" + data + (
            (left == null && right == null) ?
                "" : ", " + left + ", "+ right +"]") ;
    }

}