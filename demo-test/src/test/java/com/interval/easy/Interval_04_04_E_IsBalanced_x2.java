/**
 * copyright 2022/1/19
 */
package com.interval.easy;

import com.leetcode.util.TreeNode;
import org.junit.Test;
import static com.leetcode.util.MathUtils.cTree;

/**
    (简单)
    面试题 04.04. 检查平衡性
        实现一个函数，检查二叉树是否平衡。在这个问题中，平衡树的定义如下：任意一个节点，其两棵子树的高度差不超过 1。
        示例 1: 给定二叉树 [3, 9, 20, null, null, 15, 7]
                3
               / \
              9  20
                /  \
               15   7
        返回 true。
        示例 2: 给定二叉树 [1, 2, 2, 3, 3, null, null, 4, 4]
                1
               / \
              2   2
             / \
            3   3
           / \
          4   4
        返回 false。
*/
public class Interval_04_04_E_IsBalanced_x2 {

    @Test
    public void test() {
        assert isBalanced(cTree(3, 9, 20, null, null, 15, 7));
        assert !isBalanced(cTree(1, 2, 2, 3, 3, null, null, 4, 4));
        assert !isBalanced(cTree(1, 2, 2, 3, null, null, 3, 4, null, null, 4));
    }

    public boolean isBalanced(TreeNode root) {
        return true;
    }

}
















/**
// 方法1：
public boolean isBalanced(TreeNode root) {
    if (root == null)
        return true;
    return Math.abs(height(root.left) - height(root.right)) <= 1 && isBalanced(root.left) && isBalanced(root.right);
}

private int height(TreeNode node) {
    if (node == null)
        return 0;
    return Math.max(height(node.left), height(node.right)) + 1;
}
*/