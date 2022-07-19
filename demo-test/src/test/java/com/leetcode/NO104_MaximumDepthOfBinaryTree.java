/**
 * copyright 2022/1/19
 */
package com.leetcode;

import com.leetcode.util.TreeNode;
import org.junit.Test;
import static com.leetcode.util.LogUtil.info;

/**
    104. 二叉树的最大深度
    给定一个二叉树，找出其最大深度。
    二叉树的深度为根节点到最远叶子节点的最长路径上的节点数。
    说明: 叶子节点是指没有子节点的节点。
    示例：给定二叉树 [3,9,20,null,null,15,7]，
        3
        / \
        9  20
        /  \
        15   7
    返回它的最大深度 3
*/
public class NO104_MaximumDepthOfBinaryTree {

    @Test
    public void test() {
        info(maxDepth(
            new TreeNode(3,
                9, new TreeNode(20,
                                15, 7))));
    }


    public int maxDepth(TreeNode root) {
        return -1;
    }

}







/**
// 方法1 递归法
public int maxDepth(TreeNode root) {
    if (root == null)
        return 0;
    int res = Math.max(maxDepth(root.left), maxDepth(root.right));
    return res + 1;
}
*/