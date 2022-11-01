/**
 * copyright 2022/1/19
 */
package com.offer.normal;

import com.leetcode.util.TreeNode;
import org.junit.Test;

import java.util.Deque;
import java.util.LinkedList;

import static com.leetcode.util.MathUtils.cTree;

/**
    (中等)
    剑指 Offer II 045. 二叉树最底层最左边的值
        给定一个二叉树的 根节点 root，请找出该二叉树的 最底层 最左边 节点的值。
        假设二叉树中至少有一个节点。
    示例 1:
        输入: root = [2, 1, 3]
        输出: 1
    示例 2:
        输入: [1, 2, 3, 4, null, 5, 6, null, null, 7]
        输出: 7
*/
public class OfferII_045_N_FindBottomLeftValue_x2 {

    @Test
    public void test() {
        assert 1 == findBottomLeftValue(cTree(2, 1, 3));
        assert 7 == findBottomLeftValue(cTree(1, 2, 3, 4, null, 5, 6, null, null, null, null, 7));
    }

    public int findBottomLeftValue(TreeNode root) {
        int ret = 0;
        return ret;
    }

}
























/**
// 方法1：层序遍历
public int findBottomLeftValue(TreeNode root) {
    int ret = 0;
    Queue<TreeNode> queue = new LinkedList<>();
    queue.offer(root);

    while (!queue.isEmpty()) {
        TreeNode p = queue.poll();
        if (p.right != null)
            queue.offer(p.right);

        if (p.left != null)
            queue.offer(p.left);

        ret = p.val;
    }
    return ret;
}
*/