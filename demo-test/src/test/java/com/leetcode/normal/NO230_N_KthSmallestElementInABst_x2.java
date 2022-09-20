/**
 * copyright 2022/1/19
 */
package com.leetcode.normal;

import com.leetcode.util.TreeNode;
import org.junit.Test;
import java.util.Deque;
import java.util.LinkedList;

/**
    (中等)
    230. 二叉搜索树中第K小的元素
        给定一个二叉搜索树的根节点 root ，和一个整数 k ，请你设计一个算法查找其中第 k 个最小元素（从 1 开始计数）。
    示例 1：
        输入：root = [3, 1, 4, null, 2], k = 1
        输出：1
    示例 2：
        输入：root = [5, 3, 6, 2, 4, null, null, 1], k = 3
        输出：3
*/
public class NO230_N_KthSmallestElementInABst_x2 {

    @Test
    public void test() {
        assert 1 == kthSmallest(new TreeNode(3,
                new TreeNode(1,
                    null,2),            4), 1);
        assert 3 == kthSmallest(new TreeNode(5,
                new TreeNode(3,
            new TreeNode(2,
                    1),      4), new TreeNode(6)), 3);
    }

    public int kthSmallest(TreeNode root, int k) {
        Deque<Integer> list = new LinkedList<>();
        inorder(root, list, k);
        return list.getLast();
    }

    private void inorder(TreeNode root, Deque<Integer> list, int k) {
        if (root == null)
            return;

        if (root.left != null)
            inorder(root.left, list, k);

        if (list.size() < k)
            list.add(root.val);
        else
            return;

        if (root.right != null)
            inorder(root.right, list, k);
    }

}
























/**
// 方法1：
public int kthSmallest(TreeNode root, int k) {
    Deque<Integer> queue = new LinkedList<>();

    //中序遍历,正序赋值数组
    inOrder(root, queue, k);

    //寻找第k大的数，输出
    return queue.getLast();
}

//中序遍历
private static void inOrder(TreeNode root, Deque<Integer> queue, int k) {
    if (root == null)
        return;

    inOrder(root.left, queue, k);

    if (queue.size() < k)
        queue.add(root.val);
    else
        return;

    inOrder(root.right, queue, k);
}
*/