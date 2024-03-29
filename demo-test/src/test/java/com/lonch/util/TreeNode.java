package com.lonch.util;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
    根据二叉树高度，生成满二叉树
*/
@Setter
@Getter
@NoArgsConstructor
public class TreeNode<E> {

    public E val;
    public TreeNode<E> left;
    public TreeNode<E> right;

    public TreeNode(E e) {
        this.val = e;
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

        preOrder(root.left, res);
        res.add(root.val);
        preOrder(root.right, res);
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