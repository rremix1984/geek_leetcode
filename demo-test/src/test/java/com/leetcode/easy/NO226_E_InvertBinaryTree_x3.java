/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import com.leetcode.util.TreeNode;
import org.junit.Test;

import static com.leetcode.util.LogUtil.info;
import static com.leetcode.util.MathUtils.createFullTree;
import static com.leetcode.util.SwapUtil.swap;

/**
    (简单)
    226. 翻转二叉树
    给你一棵二叉树的根节点 root ，翻转这棵二叉树，并返回其根节点。
    示例 1：
        输入：root = [4, 2, 7, 1, 3, 6, 9]
        输出：[4, 7, 2, 9, 6, 3, 1]
    示例 2：
        输入：root = [2, 1, 3]
        输出：[2, 3, 1]
*/
public class NO226_E_InvertBinaryTree_x3 {

    @Test
    public void test() {
        TreeNode tmp = createFullTree(4, 2, 7, 1, 3, 6, 9);
        assert invertTree(tmp).equals(createFullTree(4, 7, 2, 9, 6, 3, 1));
        TreeNode tmp2 = createFullTree(2, 1, 3);
        assert invertTree(tmp2).equals(createFullTree(2, 3, 1));
    }

    public TreeNode invertTree(TreeNode root) {
        return null;
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

    TreeNode left = invertTree(root.left);
    TreeNode right = invertTree(root.right);

    root.left = right;
    root.right = left;
    return root;
}
*/