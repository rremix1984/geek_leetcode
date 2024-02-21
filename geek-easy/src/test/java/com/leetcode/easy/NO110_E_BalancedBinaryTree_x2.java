/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import com.leetcode.util.TreeNode;
import org.junit.Test;

import static com.leetcode.util.MathUtils.cTree;
import static java.lang.Math.abs;
import static java.lang.Math.max;

/**
    [TREE]
    (简单)
    110. 平衡二叉树
        给定一个二叉树，判断它是否是高度平衡的二叉树。
        本题中，一棵高度平衡二叉树定义为：
        一个二叉树每个节点 的左右两个子树的高度差的绝对值不超过 1 。
    示例 1：
        输入：root = [3, 9, 20, null, null, 15, 7]
        输出：true
    示例 2：
        输入：root = [1, 2, 2, 3, 3, null, null, 4, 4]
        输出：false
    示例 3：
        输入：root = []
        输出：true
*/
public class NO110_E_BalancedBinaryTree_x2 {

    @Test
    public void test() {
        assert isBalanced(cTree(3, 9, 20, null, null, 15, 7));
        assert !isBalanced(cTree(1, 2, 2, 3, 3, null, null, 4, 4));
        assert isBalanced(new TreeNode());
    }

    public boolean isBalanced(TreeNode root) {
        return true;
    }

}















/**
// 方法1：递归法
public boolean isBalanced(TreeNode root) {
    if (root == null)
        return true;

    return abs(height(root.left) - height(root.right)) <= 1
            && isBalanced(root.left)
            && isBalanced(root.right);
}

public int height(TreeNode root) {
    if (root == null)
        return 0;

    return max(height(root.left),
            height(root.right)) + 1;
}
*/