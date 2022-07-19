/**
 * copyright 2022/1/19
 */
package com.leetcode;

import com.leetcode.util.TreeNode;
import org.junit.Test;

import java.util.Stack;

import static com.leetcode.util.LogUtil.info;

/**
    （简单）
    226. 翻转二叉树
    给你一棵二叉树的根节点 root ，翻转这棵二叉树，并返回其根节点。
    示例 1：
        输入：root = [4,2,7,1,3,6,9]
        输出：[4,7,2,9,6,3,1]
    示例 2：
        输入：root = [2,1,3]
        输出：[2,3,1]
*/
public class NO226_InvertBinaryTree_x2 {

    @Test
    public void test() {
        TreeNode tmp = new TreeNode(4,
        new TreeNode(2,
                1, 3), new TreeNode(7,
                                        6, 9));
        info(tmp);
        info(invertTree(tmp));
    }

    public TreeNode invertTree(TreeNode root) {
        if (root == null)
            return null;

        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();

            TreeNode tmp = node.left;
            node.left = node.right;
            node.right = tmp;

            if (node.left != null)
                stack.push(node.left);

            if (node.right != null)
                stack.push(node.right);
        }
        return root;
    }

}











/**
// 方法2  迭代法（前序遍历）
public TreeNode invertTree(TreeNode root) {
    if (root==null)
        return null;

    Stack<TreeNode> stack = new Stack<>();
    stack.push(root);
    while (!stack.isEmpty()) {
        TreeNode head = stack.pop();

        // 核心左、右互换逻辑
        TreeNode tmp = head.left;
        head.left = head.right;
        head.right = tmp;

        if (head.left!=null)
            stack.push(head.left);

        if (head.right!=null)
            stack.push(head.right);
    }
    return root;
}


// 方法2 递归法
public TreeNode invertTree(TreeNode root) {
    if (root == null)
        return null;
    root.left = invertTree(root.right);;
    root.right = invertTree(root.left);;
    return root;
}
*/