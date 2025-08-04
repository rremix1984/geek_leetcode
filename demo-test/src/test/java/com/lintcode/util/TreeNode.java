package com.lintcode.util;

import java.util.LinkedList;
import java.util.Queue;

/**
 * TreeNode
 */
public class TreeNode {

    /**
     * val
     */
    public Integer val;

    /**
     * left, right
     */
    public TreeNode left, right;

    /**
     * @param val val
     */
    public TreeNode(Integer val) {
        this.val = val;
        this.left = this.right = null;
    }

    /**
     * @param val val
     * @param args args
     */
    public TreeNode(Integer val, Integer... args) {
        this(val);

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(this);

        int i = 0;
        while (!queue.isEmpty() && i < args.length) {
            TreeNode node = queue.poll();
            if (args[i] != null) {
                node.left = new TreeNode(args[i]);
                queue.offer(node.left);
            }
            i++;

            if (i < args.length && args[i] != null) {
                node.right = new TreeNode(args[i]);
                queue.offer(node.right);
            }
            i++;
        }
    }

    /**
     * 打印二叉树的层次结构。
     * @param root 二叉树的根节点
     */
    public static void print(TreeNode root) {
        if (root == null) {
            System.out.println("The tree is empty.");
            return;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            for (int i = 0; i < levelSize; i++) {
                TreeNode currentNode = queue.poll();
                if (currentNode != null) {
                    System.out.print(currentNode.val + " ");
                    queue.offer(currentNode.left);
                    queue.offer(currentNode.right);
                } else {
                    System.out.print("# ");
                }
            }
            System.out.println();  // 换行到下一层
        }
    }

}
