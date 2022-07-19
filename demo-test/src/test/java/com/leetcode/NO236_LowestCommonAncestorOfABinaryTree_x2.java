/**
 * copyright 2022/1/19
 */
package com.leetcode;

import com.leetcode.util.TreeNode;
import org.junit.Test;
import static com.leetcode.util.LogUtil.info;

/**
    （中等）
    236. 二叉树的最近公共祖先
    给定一个二叉树, 找到该树中两个指定节点的最近公共祖先。
    百度百科中最近公共祖先的定义为：“对于有根树 T 的两个节点 p、q，最近公共祖先
    表示为一个节点 x，满足 x 是 p、q 的祖先且 x 的深度尽可能大（一个节点也可以
    是它自己的祖先）。”

    示例 1：
        输入：root = [3, 5, 1, 6, 2, 0, 8, null, null, 7, 4], p = 5, q = 1
        输出：3
        解释：节点 5 和节点 1 的最近公共祖先是节点 3 。
*/
public class NO236_LowestCommonAncestorOfABinaryTree_x2 {

    @Test
    public void test() {
        info(lowestCommonAncestor(
            new TreeNode(3,
        new TreeNode(5,
            6,new TreeNode(2,
                        7,4)), new TreeNode(1,
                                                0, 8)
            ),5,1
        ));// 3
    }

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        return null;
    }












/**
// 方法1 递归法
public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
    if (root == null)
        return null;

    if (p.val == root.val || q.val == root.val)
        return root;

    TreeNode left = lowestCommonAncestor(root.left, p, q);
    TreeNode right = lowestCommonAncestor(root.right, p, q);

    if (left != null && right == null)
        return left;

    if (left == null && right != null)
        return right;

    if (left != null && right != null)
        return root;

    return null;
}
*/

public TreeNode lowestCommonAncestor(TreeNode root, int p, int q) {
    return lowestCommonAncestor(root, new TreeNode(p), new TreeNode(q));
}
}