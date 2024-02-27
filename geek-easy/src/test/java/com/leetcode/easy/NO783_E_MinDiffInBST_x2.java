/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import com.leetcode.util.TreeNode;
import org.junit.Test;

import static java.lang.Math.min;

/**
    [TREE]
    (简单)
    783. 二叉搜索树节点最小距离
        给你一个二叉搜索树的根节点root，返回树中任意两不同节点值之间的最小差值。
        差值是一个正数，其数值等于两值之差的绝对值。
    示例 1：
        输入：root = [4, 2, 6, 1, 3]
        输出：1
    示例 2：
        输入：root = [1, 0, 48, null, null, 12, 49]
        输出：1
    提示：
        树中节点的数目范围是 [2, 100]
        0 <= Node.val <= 105
*/
public class NO783_E_MinDiffInBST_x2 {

    @Test
    public void test() {
//        assert 1 == minDiffInBST(
//                cTree(4, 2, 6, 1, 3));
//        assert 1 == minDiffInBST(
//                cTree(1, 0, 48, null, null, 12, 49));
        assert 6 == minDiffInBST(
                new TreeNode(27,
                null, new TreeNode(34,
                        null, new TreeNode(58,
                        new TreeNode(50,
                                44,null)))));
    }

    int pre = -1;
    int ans;
    // 方法1：
    public int minDiffInBST(TreeNode root) {
        ans = Integer.MAX_VALUE;
        return ans;
    }

}



















/**
int pre;
int ans;
// 方法1：
public int minDiffInBST(TreeNode root) {
    ans = Integer.MAX_VALUE;
    pre = -1;
    dfs(root);
    return ans;
}

public void dfs(TreeNode root) {
    if (root == null)
        return;

    dfs(root.left);
    if (pre != -1)
        ans = min(ans, root.val - pre);

    pre = root.val;
    dfs(root.right);
}
*/