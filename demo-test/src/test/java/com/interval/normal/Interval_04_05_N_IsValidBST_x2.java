/**
 * copyright 2022/1/19
 */
package com.interval.normal;

import com.leetcode.util.TreeNode;
import org.junit.Test;
import static com.leetcode.util.MathUtils.cTree;

/**
    (中等)
    面试题 04.05. 合法二叉搜索树
        实现一个函数，检查一棵二叉树是否为二叉搜索树。
    示例 1:
        输入:    2
               / \
              1   3
        输出: true
    示例 2:
        输入:    5
               / \
              1   4
                 / \
                3   6
        输出: false
        解释: 输入为: [5, 1, 4, null, null, 3, 6]。
            根节点的值为 5，但是其右子节点值为 4。
*/
public class Interval_04_05_N_IsValidBST_x2 {

    @Test
    public void test() {
        assert isValidBST(cTree(2, 1, 3));
        assert !isValidBST(cTree(5, 1, 4, null, null, 3, 6));
    }

    public boolean isValidBST(TreeNode<Integer> root) {
        return dfs(root, null, null);
    }

    private boolean dfs(TreeNode<Integer> root, Integer lo, Integer hi) {
        if (root == null)
            return true;

        return (lo == null || lo < root.val)
            && (hi == null || hi > root.val )
            && dfs(root.left, lo, root.val)
            && dfs(root.right, root.val, hi);
    }

}


















/*
// 方法1：
public boolean isValidBST(TreeNode root) {
    return dfs(root, null, null);
}

public boolean dfs(TreeNode node, Integer lo, Integer hi) {
    if (node == null)
        return true;

    return (lo == null || node.val > lo)
            && (hi == null || node.val < hi)
            && dfs(node.right, node.val, hi)
            && dfs(node.left, lo, node.val);
}
*/