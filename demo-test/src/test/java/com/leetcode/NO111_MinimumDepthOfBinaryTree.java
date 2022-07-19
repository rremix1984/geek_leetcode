/**
 * copyright 2022/1/19
 */
package com.leetcode;

import com.leetcode.util.TreeNode;
import org.junit.Test;

import static com.leetcode.util.LogUtil.info;

/**
    111. 二叉树的最小深度
    给定一个二叉树，找出其最小深度。
    最小深度是从根节点到最近叶子节点的最短路径上的节点数量。
    说明：叶子节点是指没有子节点的节点。

    示例 1：
        输入：root = [3,9,20,null,null,15,7]
        输出：2
    示例 2：
        输入：root = [2,null,3,null,4,null,5,null,6]
        输出：5
*/
public class NO111_MinimumDepthOfBinaryTree {

    @Test
    public void test() {
//        info(minDepth(new TreeNode(
//                3,
//        9, new TreeNode(20,
//                        15, 7))));

        info(minDepth(new TreeNode(
            2,
null, new TreeNode(3,
        null, new TreeNode(4,
                null, new TreeNode(5,
                        null, new TreeNode(6)))))));
    }

    public int minDepth(TreeNode root) {
        if (root == null)
            return 0;
        if (root.left == null)
            return minDepth(root.right) + 1;

        if (root.right == null)
            return minDepth(root.left) + 1;

        return Math.min(
                minDepth(root.left),
                minDepth(root.right)) + 1;
    }
}
