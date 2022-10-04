/**
 * copyright 2022/1/19
 */
package com.offer.easy;

import com.leetcode.util.TreeNode;
import org.junit.Test;
import static com.leetcode.util.LogUtil.info;
import static com.leetcode.util.MathUtils.createFullTree;

/**
    (简单)
    剑指 Offer 68 - I. 二叉搜索树的最近公共祖先
        给定一个二叉搜索树, 找到该树中两个指定节点的最近公共祖先。
        百度百科中最近公共祖先的定义为：“对于有根树 T 的两个结点 p、q，最近公共祖先表示为一个结点 x，满足 x 是 p、q 的祖先且 x 的深度尽可能大（一个节点也可以是它自己的祖先）。”
        例如，给定如下二叉搜索树:  root = [6,2,8,0,4,7,9,null,null,3,5]
    示例 1:
        输入: root = {6, 2, 8, 0, 4, 7, 9, null, null, 3, 5},  p = 2,  q = 8
        输出: 6
        解释: 节点 2 和节点 8 的最近公共祖先是 6。
    示例 2:
        输入: root = {6, 2, 8, 0, 4, 7, 9, null, null, 3, 5},  p = 2,  q = 4
        输出: 2
        解释: 节点 2 和节点 4 的最近公共祖先是 2, 因为根据定义最近公共祖先节点可以为节点本身。
*/
public class Offer_068_I_E_LowestCommonAncestor_x2 {

    @Test
    public void test() {
        TreeNode t1 = createFullTree(6, 2, 8, 0, 4, 7, 9, null, null, 3, 5);
        assert t1 == lowestCommonAncestor(t1, t1.left, t1.right);
        TreeNode t2 = createFullTree(6, 2, 8, 0, 4, 7, 9, null, null, 3, 5);
        assert t2.left == lowestCommonAncestor(t2, t2.left, t2.left.right);
        TreeNode t3 = createFullTree(2, 1, 3);
        assert t3 == lowestCommonAncestor(t3, t3.left, t3.right);
    }

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        return root;
    }

}














/**
public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
    TreeNode ans = root;
    while (true)
        if (p.val < ans.val && q.val < ans.val)
            ans = ans.left;
        else if (p.val > ans.val && q.val > ans.val)
            ans = ans.right;
        else
            break;
    return ans;
}
*/