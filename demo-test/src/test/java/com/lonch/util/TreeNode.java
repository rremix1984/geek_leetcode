package com.lonch.util;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
    根据二叉树高度，生成满二叉树
*/
public class TreeNode<E> {

    public E val;
    public TreeNode<E> left;
    public TreeNode<E> right;

    public TreeNode(E e) {
        this.val = e;
    }

    public TreeNode() {

    }

    public E getVal() {
        return val;
    }

    public void setVal(E val) {
        this.val = val;
    }

    public TreeNode<E> getLeft() {
        return left;
    }

    public void setLeft(TreeNode<E> left) {
        this.left = left;
    }

    public TreeNode<E> getRight() {
        return right;
    }

    public void setRight(TreeNode<E> right) {
        this.right = right;
    }

    @Override
    public String toString() {
        return "[" + val + (
                (left == null && right == null) ? "" : ", " + left + ", "+ right +"]") ;
    }

    public static void preOrder(TreeNode root, List res) {
        if (root == null)
            return;

        res.add(root.val);
        preOrder(root.left, res);
        preOrder(root.right, res);
    }

    public static void postOrder(TreeNode root, List res) {
        if (root == null)
            return;

        preOrder(root.left, res);
        preOrder(root.right, res);
        res.add(root.val);
    }

    public static void inOrder(TreeNode root, List res) {
        if (root == null)
            return;

        inOrder(root.left, res);
        res.add(root.val);
        inOrder(root.right, res);
    }

    public static TreeNode cTree(int depth, Integer value) {
        // 基准情况
        if (depth < 0)
            return null;

        // 创建当前节点
        TreeNode node = new TreeNode<>(value);
        // 递归创建左右子树
        node.left = cTree(depth - 1, value);
        node.right = cTree(depth - 1, value);
        return node;
    }

    public static void levelOrder(TreeNode<Character> root,
                                  List<Character> list) {
        if (root == null)
            return;

        // TODO
        Deque<TreeNode<Character>> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            Character val = null;
            while (size > 0) {
                TreeNode<Character> node = queue.poll();
                val = node.val;
                if (node.left != null)
                    queue.offer(node.left);

                if (node.right != null)
                    queue.offer(node.right);

                size--;
                list.add(val);
            }
        }

    }

}





















/*

// 填充父节点
public void setParents(TreeNode<E> node, TreeNode<E> parent) {
    if (node != null) {
        node.setParent(parent);
        if (node.getLeft() != null)
            setParents(node.getLeft(), node);

        if (node.getRight() != null)
            setParents(node.getRight(), node);
    }
}
*/