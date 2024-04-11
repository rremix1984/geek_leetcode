/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import com.leetcode.util.MathUtils;
import com.leetcode.util.TreeNode;
import org.junit.Test;
import static com.leetcode.util.LogUtil.info;
import static com.leetcode.util.MathUtils.cTree;

/**
    [TREE]
    (简单)
    1022. 从根到叶的二进制数之和
        给出一棵二叉树，其上每个结点的值都是 0 或 1 。每一条从根到叶的路径都代表一个从最高有效位开始的二进制数。
        例如，如果路径为 0 -> 1 -> 1 -> 0 -> 1，那么它表示二进制数 01101，也就是 13 。
        对树上的每一片叶子，我们都要找出从根到该叶子的路径所表示的数字。
        返回这些数字之和。题目数据保证答案是一个 32 位 整数。
    示例 1：
        输入：root = {1, 0, 1, 0, 1, 0, 1}
        输出：22
        解释：(100) + (101) + (110) + (111) = 4 + 5 + 6 + 7 = 22
    示例 2：
        输入：root = {0}
        输出：0
*/
public class NO1022_E_SumRootToLeaf_x2 {

    @Test
    public void test() {
        assert 22 == sumRootToLeaf(cTree(1, 0, 1, 0, 1, 0, 1));
        assert 0 == sumRootToLeaf(cTree(0));
    }

    public int sumRootToLeaf(TreeNode<Integer> root) {
        return -1;
    }

}















/*
// 方法1：递归：深度遍历dfs
public int sumRootToLeaf(TreeNode root) {
    return dfs(root, 0);
}

public int dfs(TreeNode root, int val) {
    if (root == null)
        return 0;

    // 左移 1 位，相当于乘 2
    int tmp = (val << 1) | root.val;

    // 左右都是空的
    if (root.left == null && root.right == null)
        return tmp;

    return dfs(root.left, tmp) + dfs(root.right, tmp);
}
*/