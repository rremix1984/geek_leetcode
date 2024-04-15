/**
 * copyright 2022/1/19
 */
package com.leetcode.normal;

import com.leetcode.util.TreeNode;
import org.junit.Test;

import static com.leetcode.util.MathUtils.cTree;

/**
    （中等）
    235. 二叉搜索树的最近公共祖先
    给定一个二叉搜索树, 找到该树中两个指定节点的最近公共祖先。
    百度百科中最近公共祖先的定义为：“对于有根树 T 的两个结点 p、q，
    最近公共祖先表示为一个结点 x，满足 x 是 p、q 的祖先且 x 的深度尽可
    能大（一个节点也可以是它自己的祖先）。”
    例如，给定如下二叉搜索树: root =[6, 2, 8, 0, 4, 7, 9, null, null, 3, 5]
    示例 1:
        输入: root = [6, 2, 8, 0, 4, 7,
                     9, null, null, 3, 5], p = 2, q = 8
        输出: 6
        解释: 节点 2 和节点 8 的最近公共祖先是 6。

    示例 2:
        输入: root = [6, 2, 8, 0, 4, 7,
                     9, null, null, 3, 5], p = 2, q = 4
        输出: 2
        解释: 节点 2 和节点 4 的最近公共祖先是 2, 因为根据定义
             最近公共祖先节点可以为节点本身。

    二叉查找树（Binary Search Tree）
    （又：二叉搜索树，二叉排序树）它或者是一棵空树，或者是具有下列性质的二叉树：
    若它的左子树不空，则左子树上所有结点的值均小于它的根结点的值；
    若它的右子树不空，则右子树上所有结点的值均大于它的根结点的值；
*/
public class NO235_N_LowestCommonAncestorOfABinarySearchTree_x2 {

    @Test
    public void test() {
        assert 6 == lowestCommonAncestor(cTree(6, 2, 8, 0, 4, 7, 9, null, null, 3, 5)
            , cTree(2), cTree(8)
        ).val; // 6
        assert 2 == lowestCommonAncestor(cTree(6, 2, 8, 0, 4, 7, 9, null, null, 3, 5)
            , cTree(2), cTree(4)).val; // 2
    }

    public TreeNode<Integer> lowestCommonAncestor(TreeNode<Integer> root,
                                                  TreeNode<Integer> p,
                                                  TreeNode<Integer> q) {
        return root;
    }

}













/**
public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
    // 让（p, q）互换一次，方便后面的查找
    if (p.val > q.val) { // 保证 p 是左节点
        TreeNode tmp = p;
        p = q;
        q = tmp;
    }

    // 如果一边小于root，另一边大于root，那就在中间
    if (root.val >= p.val && root.val <= q.val)
        return root;

    // p,q都在一边， 如果root比左边的大，那就都在左边
    // 接下来递归左边的公共祖先
    if (root.val > p.val)
        return lowestCommonAncestor(root.left , p ,q);

    // 如果 root 比左边的小，那就都在右边
    // 接下来递归右边的公共祖先
    return lowestCommonAncestor(root.right , p ,q);
}
*/