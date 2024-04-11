/**
 * copyright 2022/1/19
 */
package com.offer.easy;

import com.leetcode.util.TreeNode;
import org.junit.Test;
import static com.leetcode.util.LogUtil.info;
import static com.leetcode.util.MathUtils.cTree;

/**
    (简单)
    剑指 Offer 55 - I. 二叉树的深度
        输入一棵二叉树的根节点，求该树的深度。从根节点到叶节点依次经过的节点（含根、叶节点）形成树的一条路径，最长路径的长度为树的深度。
    例如：
        给定二叉树 [3, 9, 20, null, null, 15, 7]，
              3
             / \
            9  20
          /  \
         15   7
        返回它的最大深度 3 。
*/
public class Offer_055_I_E_MaxDepth_x2 {

    @Test
    public void test() {
        assert 3 == maxDepth(cTree(3, 9, 20, null, null, 15, 7));
    }

    public int maxDepth(TreeNode<Integer> root) {
        return -1;
    }

}




















/*
// 方法1：迭代法
public int maxDepth(TreeNode root) {
    if (root == null)
        return 0;

    int depth = 0;
    Deque<TreeNode> stack = new LinkedList<>();
    stack.offerLast(root);
    while (!stack.isEmpty()) {
        int size = stack.size();
        while (size > 0) {
            TreeNode node = stack.pop();

            if(node.left!=null)
                stack.offerLast(node.left);

            if(node.right!=null)
                stack.offerLast(node.right);

            size--;
        }
        depth++;
    }
    return depth;
}

// 方法2：递归法
public int maxDepth(TreeNode root) {
    if (root == null)
        return 0;

    return 1 + max(maxDepth(root.left), maxDepth(root.right));
}
*/