package com.lonch.util;

public class TernaryTreeNode<T> {
    public T value;
    public TernaryTreeNode<T> left;
    public TernaryTreeNode<T> middle;
    public TernaryTreeNode<T> right;

    public TernaryTreeNode(T value) {
        this.value = value;
        this.left = null;
        this.middle = null;
        this.right = null;
    }

    public static StringBuilder preOrder(TernaryTreeNode root, StringBuilder sb) {
        if (root == null)
            return sb;

        sb.append(root.value);
        preOrder(root.left, sb);
        preOrder(root.right, sb);
        return sb;
    }

    // 打印三叉树的方法
    public static <T> void printTernaryTree(TernaryTreeNode<T> node) {
        if (node == null) {
            return;
        }

        // 打印当前节点值
        System.out.print(node.value + " ");

        // 递归打印左子树
        printTernaryTree(node.left);

        // 递归打印中子树
        printTernaryTree(node.middle);

        // 递归打印右子树
        printTernaryTree(node.right);
    }


}

