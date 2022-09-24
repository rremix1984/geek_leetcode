/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import com.leetcode.util.TreeNode;
import org.junit.Test;
import java.util.*;
import static com.leetcode.util.LogUtil.info;
import static com.leetcode.util.MathUtils.createFullTree;
import static com.leetcode.util.MathUtils.max;
import static org.junit.Assert.assertEquals;

/**
   （简单）
    104. 二叉树的最大深度
    给定一个二叉树，找出其最大深度。
    二叉树的深度为根节点到最远叶子节点的最长路径上的节点数。
    说明: 叶子节点是指没有子节点的节点。
    示例：给定二叉树 [3, 9, 20, null, null, 15, 7]，

          3
        /  \
       9   20
          /  \
         15   7

    返回它的最大深度 3
*/
@SuppressWarnings("all")
public class NO104_E_MaximumDepthOfBinaryTree_x3 {

    @Test
    public void test() {
        assertEquals(3, maxDepth(createFullTree(3, 9, 20, null, null, 15, 7)));// 3
        assertEquals(4, maxDepth(createFullTree(3, 9, null, 10, null, null, null, 11, null)));// 4
        assertEquals(4, maxDepth(createFullTree(3, null, 9, null, null, null, 10, null, null, null, null, null, null, 11, null)));
    }

    public int maxDepth(TreeNode root) {
        int depth = 0;
        if (root == null)
            return depth;

        return depth;
    }

}







/**
// 方法1 递归法
public int maxDepth(TreeNode root) {
    if (root == null)
        return 0;
    return 1 + max(maxDepth(root.left),
             maxDepth(root.right));
}

// 方法2  迭代法
public int maxDepth(TreeNode root) {
    int depth = 0;
    if (root == null)
        return depth;

    Queue<TreeNode> queue = new LinkedList<>();
    queue.offer(root);

    while (!queue.isEmpty()) {
        int size = queue.size();
        while (size > 0) {
            TreeNode node = queue.poll();
            if (node.left != null)
                queue.offer(node.left);

            if (node.right != null)
                queue.offer(node.right);

            size--;
        }
        depth++;
    }
    return depth;
}
*/