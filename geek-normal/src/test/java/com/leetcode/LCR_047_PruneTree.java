package com.leetcode;

import com.leetcode.util.TreeNode;
import org.junit.Test;
import static com.leetcode.util.MathUtils.cTree;
import static com.leetcode.util.TreeNode.treeEquals;

/**
    [TREE]
    (中等)
    LCR.047 二叉树剪枝
    给定一个二叉树 根节点 root ，树的每个节点的值要么是 0，要么是 1。
    请剪除该二叉树中所有节点的值为 0 的子树。
    节点 node 的子树为 node 本身，以及所有 node 的后代。
    示例 1:
            输入: [1, null, 0, 0, 1]
            输出: [1, null, 0, null, 1]
            解释:
            只有红色节点满足条件“所有不包含 1 的子树”。
            右图为返回的答案。
    示例 2:
            输入: [1, 0, 1, 0, 0, 0, 1]
            输出: [1, null, 1, null, 1]
            解释:
    示例 3:
            输入: [1, 1, 0, 1, 1, 0, 1, 0]
            输出: [1, 1, 0, 1, 1, null, 1]
            解释:
    提示:
        二叉树的节点个数的范围是 [1,200]
        二叉树节点的值只会是 0 或 1
    Related Topics:树,深度优先搜索,二叉树
*/
public class LCR_047_PruneTree {

    @Test
    public void test() {
        assert treeEquals(cTree(1, null, 0, null, null, null, 1),
            pruneTree(cTree(1, null, 0, null, null, 0, 1)));
        assert treeEquals(cTree(1, null, 1, null, null, null, 1),
            pruneTree(cTree(1, 0, 1, 0, 0, 0, 1)));
        assert treeEquals(cTree(1, 1, 0, 1, 1, null, 1),
            pruneTree(cTree(1, 1, 0, 1, 1, 0, 1, 0)));
    }

    public TreeNode pruneTree(TreeNode root) {
        return root;
    }

}















/*
// 方法1：
public TreeNode pruneTree(TreeNode root) {
    boolean res = dfs(root);
    if (res)
        return null;

    return root;
}

public boolean dfs(TreeNode node) {
    if (node == null)
        return true;

    // 问左边，告诉我你是不是全为0的树？
    boolean left = dfs(node.left);

    // 问右边，告诉我你是不是全为0的树？
    boolean right = dfs(node.right);

    // 如果是，就不要你了
    if (left)
        node.left = null;

    if (right)
        node.right = null;

    // 如果我的值是0， 并且左右两边都是0，那么告诉上一级我是全为0的树
    return node.val == 0 && left && right;
}
*/