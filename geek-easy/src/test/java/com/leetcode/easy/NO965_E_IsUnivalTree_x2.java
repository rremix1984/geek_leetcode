/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import com.leetcode.util.TreeNode;
import org.junit.Test;
import static com.leetcode.util.MathUtils.cTree;

/**
    [TREE]
    (简单)
    965. 单值二叉树
        如果二叉树每个节点都具有相同的值，那么该二叉树就是单值二叉树。
        只有给定的树是单值二叉树时，才返回 true；否则返回 false。
    示例 1：
        输入：[1, 1, 1, 1, 1, null, 1]
        输出：true
    示例 2：
        输入：[2, 2, 2, 5, 2]
        输出：false
    提示：
        给定树的节点数范围是 [1, 100]。
        每个节点的值都是整数，范围为 [0, 99] 。
*/
public class NO965_E_IsUnivalTree_x2 {

    @Test
    public void test() {
        assert isUnivalTree(cTree(1, 1, 1, 1, 1, null, 1));
        assert !isUnivalTree(cTree(2, 2, 2, 5, 2));
    }

    public boolean isUnivalTree(TreeNode<Integer> root) {
        return true;
    }

}

















/*
// 方法1：
public boolean isUnivalTree(TreeNode root) {
    if (root == null)
        return true;

    if (root.left != null)
        if (root.val != root.left.val || !isUnivalTree(root.left))
            return false;

    if (root.right != null)
        return root.val == root.right.val && isUnivalTree(root.right);

    return true;
}
*/