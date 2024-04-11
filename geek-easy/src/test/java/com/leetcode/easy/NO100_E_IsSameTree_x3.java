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
    100. 相同的树
        给你两棵二叉树的根节点 p 和 q ，编写一个函数来检验这两棵树是否相同。
        如果两个树在结构上相同，并且节点具有相同的值，则认为它们是相同的。
    示例 1：
        输入：p = {1, 2, 3},  q = {1, 2, 3}
        输出：true
    示例 2：
        输入：p = {1, 2},  q = {1, null, 2}
        输出：false
    示例 3：
        输入：p = {1, 2, 1},  q = {1, 1, 2}
        输出：false
*/
public class NO100_E_IsSameTree_x3 {

    @Test
    public void test() {
        assert isSameTree(
            cTree(1, 2, 3), cTree(1, 2, 3));
        assert !isSameTree(
            cTree(1, 2), cTree(1, null, 2));
        assert !isSameTree(
            cTree(1, 2, 1), cTree(1, 1, 2));
    }

    public boolean isSameTree(TreeNode<Integer> p, TreeNode<Integer> q) {
        return false;
    }

}











/**
public boolean isSameTree(TreeNode p, TreeNode q) {
    if (p == null && q == null)
        return true;

    if (p == null || q == null || p.val != q.val)
        return false;

    return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
}
*/