package com.leetcode.util;

import java.util.Objects;

/**
 * 树
 * @author wangxiaozhe
 */
@SuppressWarnings("all")
public class TreeNode<T> {

    public T val;

    public TreeNode<T> left;

    public TreeNode<T> right;

    public TreeNode(){

    }

    public TreeNode(T x) { val = x; }

    public TreeNode(T x, T left) { val = x; this.left = new TreeNode(left); }

    public TreeNode(T x, TreeNode<T> left, TreeNode<T> right) {
        val = x;
        this.left = left;
        this.right = right;
    }

    public TreeNode(T x, TreeNode<T> left) {
        val = x;
        this.left = left;
    }

    public TreeNode(T x, T left, T right) {
        val = x;
        if (left instanceof TreeNode)
            this.left = (TreeNode) left;
        else
            this.left = new TreeNode<>(left);

        if (right instanceof TreeNode)
            this.right = (TreeNode) right;
        else
            this.right = new TreeNode<>(right);
    }

    @Override
    public String toString() {
        return "[" + val + ((left==null&&right==null)?"":", " + left + ", "+ right +"]") ;
    }

    public static <T> String printRight(TreeNode<T> tn) {
        StringBuilder sb = new StringBuilder();
        sb.append(tn.val);
        while (tn.right != null) {
            tn = tn.right;
            sb.append(" -> " + tn.val);
        }
        return sb.toString();
    }

    public boolean equals(TreeNode<T> node) {
        if (node == null)
            return false;

        return equalsSub(node, this);
    }

    private boolean equalsSub(TreeNode<T> left, TreeNode<T> right) {
        if (left == null && right == null)
            return true;

        if (left == null || right == null || left.val != right.val)
            return false;

        return equalsSub(left.left, right.left) && equalsSub(left.right, right.right);
    }

    public static <T> boolean treeEquals(TreeNode<T> a, TreeNode<T> b) {
        if (a == null && b == null)
            return true;

        if (a == null || b == null)
            return false;

        return Objects.equals(a.val, b.val)
                && treeEquals(a.left, b.left)
                && treeEquals(a.right, b.right);
    }

}