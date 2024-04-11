/**
 * copyright 2022/1/19
 */
package com.interval.easy;

import com.leetcode.util.TreeNode;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static com.leetcode.util.MathUtils.cTree;

/**
    (简单)
    面试题 17.12. BiNode
        二叉树数据结构TreeNode可用来表示单向链表（其中left置空，right为下一个链表节点）。
        实现一个方法，把二叉搜索树转换为单向链表，要求依然符合二叉搜索树的性质，转换操作应是原址的，
        也就是在原始的二叉搜索树上直接修改。返回转换后的单向链表的头节点。
        注意：本题相对原题稍作改动
    示例：
        输入： [4, 2, 5, 1, 3, null, 6, 0]
        输出： [0, null, 1, null, 2, null, 3, null, 4, null, 5, null, 6]
*/
public class Interval_17_12_E_ConvertBiNode {

    @Test
    public void test() {
        assert cTree(1, null, 2, null, null, null, 3).equals(
                convertBiNode(new TreeNode<>(2,1,3)));
    }

    public TreeNode<Integer> convertBiNode(TreeNode<Integer> root) {
        // TODO
        return root;
    }

}


















/*
// 方法1：中序遍历
public TreeNode convertBiNode(TreeNode root) {
    List<Integer> res = new ArrayList<>();

    // 二叉搜索树，中序遍历就是向右展开的
    inorder(root, res);

    TreeNode dummy = new TreeNode(-1);
    TreeNode cur = dummy;
    for (int v : res) {
        cur.right = new TreeNode(v);
        cur = cur.right;
    }
    return dummy.right;
}

private void inorder(TreeNode root, List<Integer> res) {
    if (root == null)
        return;

    if (root.left != null)
        inorder(root.left, res);

    res.add(root.val);

    if (root.right != null)
        inorder(root.right, res);
}
*/