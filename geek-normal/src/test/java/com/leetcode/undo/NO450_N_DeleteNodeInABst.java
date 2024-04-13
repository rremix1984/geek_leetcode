/**
 * copyright 2022/1/19
 */
package com.leetcode.undo;

import com.leetcode.util.TreeNode;
import org.junit.Test;

import static com.leetcode.util.MathUtils.cTree;

/**
    (中等)
    450. 删除二叉搜索树中的节点
        给定一个二叉搜索树的根节点 root 和一个值 key，删除二叉搜索树中的 key 对应的节点，并保证二叉搜索树的性质不变。返回二叉搜索树（有可能被更新）的根节点的引用。
        一般来说，删除节点可分为两个步骤：
        首先找到需要删除的节点；
        如果找到了，删除它。
    示例 1:
        输入：root = {5, 3, 6, 2, 4, null, 7}, key = 3
        输出：[5, 4, 6, 2, null, null, 7]
        解释：给定需要删除的节点值是 3，所以我们首先找到 3 这个节点，然后删除它。
             一个正确的答案是 [5, 4, 6, 2, null, null, 7], 如下图所示。
             另一个正确答案是 [5, 2, 6, null, 4, null, 7]。
    示例 2:
        输入: root = {5, 3, 6, 2, 4, null, 7}, key = 0
        输出: [5, 3, 6, 2, 4, null, 7]
        解释: 二叉树不包含值为 0 的节点
    示例 3:
        输入: root = [], key = 0
        输出: []
*/
public class NO450_N_DeleteNodeInABst {

    @Test
    public void test() {
        assert cTree(5, 4, 6, 2, null, null, 7).equals(
            deleteNode(cTree(5, 3, 6, 2, 4, null, 7), 3));
        assert cTree(5, 3, 6, 2, 4, null, 7).equals(
            deleteNode(cTree(5, 3, 6, 2, 4, null, 7),0));
    }

    public TreeNode<Integer> deleteNode(TreeNode<Integer> root, int key) {
        return null;
    }

}

















/*
// 方法1：
public TreeNode deleteNode(TreeNode root, int key) {
    TreeNode cur = root, curParent = null;
    while (cur != null && cur.val != key) {
        curParent = cur;
        if (cur.val > key)
            cur = cur.left;
        else
            cur = cur.right;
    }

    if (cur == null)
        return root;

    if (cur.left == null && cur.right == null)
        cur = null;
    else if (cur.right == null)
        cur = cur.left;
    else if (cur.left == null)
        cur = cur.right;
    else {
        TreeNode successor = cur.right, successorParent = cur;
        while (successor.left != null) {
            successorParent = successor;
            successor = successor.left;
        }
        if (successorParent.val == cur.val) {
            successorParent.right = successor.right;
        } else {
            successorParent.left = successor.right;
        }
        successor.right = cur.right;
        successor.left = cur.left;
        cur = successor;
    }

    if (curParent == null) {
        return cur;
    } else {
        if (curParent.left != null && curParent.left.val == key)
            curParent.left = cur;
        else
            curParent.right = cur;
        return root;
    }
}


// 方法2：递归法
public TreeNode deleteNode(TreeNode root, int key) {
    if (root == null)
        return null;

    // 如果根节点等于 key 值
    if (root.val == key) {
        if (root.left == null)
            return root.right;

        if (root.right == null)
            return root.left;

        TreeNode t = root.right;
        while (t.left != null)
            t = t.left;

        t.left = root.left;
        return root.right;
        // 根节点小于 key 值
    } else if (root.val < key) {
        // 递归右子树
        root.right = deleteNode(root.right, key);
        // 根节点大于 key 值
    } else {
        // 递归左子树
        root.left = deleteNode(root.left, key);
    }
    return root;
}
*/