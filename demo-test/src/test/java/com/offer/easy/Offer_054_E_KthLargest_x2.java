/**
 * copyright 2022/1/19
 */
package com.offer.easy;

import com.leetcode.util.TreeNode;
import org.junit.Test;
import static com.leetcode.util.MathUtils.cTree;

/**
    (简单)
    剑指 Offer 54. 二叉搜索树的第k大节点
        给定一棵二叉搜索树，请找出其中第 k 大的节点的值。
    示例 1:
        输入: root = [3,1,4,null,2], k = 1
                 3
                / \
              1    4
               \
                2

        输出: 4
    示例 2:
        输入: root = [5,3,6,2,4,null,null,1], k = 3
                5
              / \
             3   6
            / \
           2   4
          /
         1

        输出: 4
*/
public class Offer_054_E_KthLargest_x2 {

    @Test
    public void test() {
        assert 4 == kthLargest(cTree(3, 1, 4, null, 2), 1);
        assert 4 == kthLargest(cTree(5, 3, 6, 2, 4, null, null, 1), 3);
        assert 2 == kthLargest(cTree(3, 1, 4, null, 2), 3);
    }

    public int kthLargest(TreeNode<Integer> root, int k) {
        return -1;
    }
}























/*
int res, k;
public int kthLargest(TreeNode root, int k) {
    this.k = k;
    dfs(root);
    return res;
}

public void dfs(TreeNode root) {
    if(root == null)
        return;
    dfs(root.right);
    if(k == 0)
        return;
    if(--k == 0)
        res = root.val;
    dfs(root.left);
}
*/