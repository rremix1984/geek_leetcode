/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import com.leetcode.util.TreeNode;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import static com.leetcode.util.LogUtil.info;
import static com.leetcode.util.MathUtils.cTree;
import static java.lang.Math.min;

/**
    [TREE]
    (简单)
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
@SuppressWarnings("all")
public class NO111_E_MinimumDepthOfBinaryTree_x3 {

    @Test
    public void test() {
        assert 2 == minDepth(cTree(3, 9, 20, null, null, 15, 7));
        assert 3 == minDepth(cTree(1, 2, 3, 4, null, null, 5));
        assert 5 == minDepth(new TreeNode<>(2,
                            null, new TreeNode<>(3,
                                    null, new TreeNode<>(4,
                                            null, new TreeNode<>(5,
                                                    null, new TreeNode<Integer>(6)))))); // 5
    }

    public int minDepth(TreeNode<Integer> root) {
        return -1;
    }

}












/**
// 方法1：深度优先，递归法
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

// 方法2：广度优先
public int minDepth(TreeNode root) {
    if (root == null)
        return 0;
    Deque<TreeNode> queue = new LinkedList<>();
    queue.offer(root);
    int height = 1;
    while (!queue.isEmpty()) {
        int size = queue.size();
        while (size > 0) {
            TreeNode node = queue.poll();
            if (node.left == null && node.right == null)
                return height;

            if (node.left != null) {
                queue.offer(node.left);
            }

            if (node.right != null) {
                queue.offer(node.right);
            }
            size--;
        }
        height++;
    }
    return height;
}
*/