package com.leetcode.util;

/**
 * 树
 * @author wangxiaozhe
 */
@SuppressWarnings("all")
public class TreeNode {

    public int val;

    public TreeNode left;

    public TreeNode right;

    public TreeNode(){

    }

    public TreeNode(int x) { val = x; }

    public TreeNode(int x, int left) { val = x; this.left = new TreeNode(left); }

    public TreeNode(int x, TreeNode left, TreeNode right) {
        val = x;
        this.left = left;
        this.right = right;
    }

    public TreeNode(int x, TreeNode left) {
        val = x;
        this.left = left;
    }

    public TreeNode(int x, int left, int right) {
        val = x;
        this.left = new TreeNode(left);
        this.right = new TreeNode(right);
    }

    public TreeNode(int x, TreeNode left, int right) {
        val = x;
        this.left = left;
        this.right = new TreeNode(right);
    }

    public TreeNode(int x, int left, TreeNode right) {
        val = x;
        this.left = new TreeNode(left);
        this.right = right;
    }

    @Override
    public String toString() {
        return "[" + val + ((left==null&&right==null)?"":", " + left + ", "+ right +"]") ;
    }

    public int val() {
        return this==null?0:val;
    }

    public static String printRight(TreeNode tn) {
        StringBuilder sb = new StringBuilder();
        sb.append(tn.val);
        while (tn.right!=null) {
            tn = tn.right;
            sb.append(" -> " + tn.val);
        }
        return sb.toString();
    }
}