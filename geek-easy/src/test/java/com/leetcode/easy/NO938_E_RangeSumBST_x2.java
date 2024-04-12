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
    938. 二叉搜索树的范围和
        给定二叉搜索树的根结点 root，返回值位于范围 [low, high]
        之间的所有结点的值的和。
    示例 1：
        输入：root = {10, 5, 15, 3, 7, null, 18},
             low = 7, high = 15
        输出：32
    示例 2：
        输入：root = {10, 5, 15, 3, 7, 13, 18, 1, null, 6},
             low = 6, high = 10
        输出：23
    提示：
        树中节点数目在范围 [1, 2 * 104] 内
        1 <= Node.val <= 10 ^ 5
        1 <= low <= high <= 10 ^ 5
        所有 Node.val 互不相同
*/
public class NO938_E_RangeSumBST_x2 {

    @Test
    public void test() {
        assert 32 == rangeSumBST(
                cTree(10, 5, 15, 3, 7, null, 18),
                7,  15);
        assert 23 == rangeSumBST(
                cTree(10, 5, 15, 3, 7, 13, 18, 1, null, 6),
                6, 10);
    }

    public int rangeSumBST(TreeNode<Integer> root, int low, int high) {
        return 0;
    }

}

















/*
// 方法1：
public int rangeSumBST(TreeNode root, int low, int high) {
    if (root == null)
        return 0;

    if (root.val > high)
        return rangeSumBST(root.left, low, high);

    if (root.val < low)
        return rangeSumBST(root.right, low, high);

    return root.val + rangeSumBST(root.left, low, high)
            + rangeSumBST(root.right, low, high);
}
*/
