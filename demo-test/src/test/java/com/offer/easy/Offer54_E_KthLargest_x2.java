/**
 * copyright 2022/1/19
 */
package com.offer.easy;

import com.leetcode.util.TreeNode;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

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
public class Offer54_E_KthLargest_x2 {

    @Test
    public void test() {
        assert 4 == kthLargest(new TreeNode(3,
                new TreeNode(1,
                    null,2),        4), 1);
        assert 4 == kthLargest(new TreeNode(5,
                new TreeNode(3,
            new TreeNode(2,
                    1),     4), new TreeNode(6)),3);
        assert 2 == kthLargest(new TreeNode(3,
                new TreeNode(1,
                        null,2),        4), 3);
    }

    public int kthLargest(TreeNode root, int k) {
        List<Integer> queue = new ArrayList<>();
        inorder(root, queue, k);
        return queue.get(queue.size() - k);
    }

    private void inorder(TreeNode root, List<Integer> queue, int k) {
        if (root == null)
            return;

        if (root.left != null)
            inorder(root.left,queue,k);

        queue.add(root.val);

        if (root.right != null)
            inorder(root.right,queue,k);
    }
}























/**
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