/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import com.leetcode.util.TreeNode;
import org.junit.Test;

import static com.leetcode.util.MathUtils.cTree;
import static java.lang.Integer.MAX_VALUE;
import static java.lang.Math.abs;
import static java.lang.Math.min;

/**
    [TREE]
    (简单)
    530. 二叉搜索树的最小绝对差
        给你一个二叉搜索树的根节点 root ，返回 树中任意两不同节点值之间的最小差值 。
        差值是一个正数，其数值等于两值之差的绝对值。
    示例 1：
        输入：root = {4, 2, 6, 1, 3}
        输出：1
    示例 2：
        输入：root = {1, 0, 48, null, null, 12, 49}
        输出：1
    提示：
        树中节点的数目范围是 [2, 104]
        0 <= Node.val <= 105
*/
public class NO530_E_GetMinimumDifference_x2 {

    @Test
    public void test() {
        assert 1 == getMinimumDifference(cTree(4, 2, 6, 1, 3));
        assert 1 == getMinimumDifference(cTree(1, 0, 48, null, null, 12, 49));
    }

    public int getMinimumDifference(TreeNode<Integer> root) {
        int ans = MAX_VALUE;
        return ans;
    }

}


















/**
// 方法1：中序遍历法
public int getMinimumDifference(TreeNode root) {
    int ans = MAX_VALUE;
    int pre = MAX_VALUE;
    TreeNode cur = root;
    Stack<TreeNode> stack = new Stack<>();
    while (!stack.isEmpty() || cur != null) {
        if (cur != null) {
            stack.push(cur);
            cur = cur.left;
        } else {
            cur = stack.pop();
            ans = min(ans, abs(cur.val - pre));
            pre = cur.val;
            cur = cur.right;
        }
    }
    return ans;
}
*/