/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import com.leetcode.util.TreeNode;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import static com.leetcode.util.LogUtil.info;

/**
    （简单）
    94. 二叉树的中序遍历
    给定一个二叉树的根节点 root ，返回 它的 中序 遍历 。
    示例 1：
        输入：root = [1,null,2,3]
        输出：[1,3,2]
*/
public class NO94_InorderTraversal_x2 {

    @Test
    public void test() {
        info(inorderTraversal(new TreeNode(
                1,
        null, new TreeNode(2,
                    3))));
    }

    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        return res;
    }

}











/**
public List<Integer> inorderTraversal(TreeNode root) {
    List<Integer> res = new ArrayList<>();
    if (root == null)
        return res;
    Stack<TreeNode> stack = new Stack<>();
    while (root != null || !stack.isEmpty()) {
        while (root != null) {
            stack.push(root);
            root = root.left;
        }
        TreeNode node = stack.pop();
        res.add(node.val);
        root = node.right;
    }
    return res;
}

// 方案2
List<Integer> res = new ArrayList<>();
public List<Integer> inorderTraversal(TreeNode root) {
    inorder(root);
    return res;
}

public void inorder(TreeNode root) {
    if (root == null)
        return;
    if (root.left != null)
        inorder(root.left);

    res.add(root.val);

    if (root.right != null)
        inorder(root.right);
}
*/