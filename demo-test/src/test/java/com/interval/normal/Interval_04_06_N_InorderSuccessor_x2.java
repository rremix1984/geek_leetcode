/**
 * copyright 2022/1/19
 */
package com.interval.normal;

import com.leetcode.util.TreeNode;
import org.junit.Test;
import static com.leetcode.util.MathUtils.cTree;
import static org.junit.Assert.assertArrayEquals;

/**
    (中等)
    面试题 04.06. 后继者
        设计一个算法，找出二叉搜索树中指定节点的“下一个”节点（也即中序后继）。
        如果指定节点没有对应的“下一个”节点，则返回null。
    示例 1:
        输入: root = [2, 1, 3], p = 1
             2
            / \
           1   3
        输出: 2
    示例 2:
        输入: root = [5, 3, 6, 2, 4, null, null, 1], p = 6
                5
               / \
              3   6
             / \
            2   4
           /
          1
    输出: null
*/
public class Interval_04_06_N_InorderSuccessor_x2 {

    @Test
    public void test() {
        TreeNode left = new TreeNode(1);
        TreeNode t1 = new TreeNode(2, left, 3);
        assert t1.equals(inorderSuccessor(t1, left));
        TreeNode t2 = cTree(5, 3, 6, 2, 4, null, null, 1);
        assert null == inorderSuccessor(t2, t2.right);
    }

    public TreeNode inorderSuccessor(TreeNode root, TreeNode p) {
        return null;
    }

}

















/**
// 方法1：
public TreeNode inorderSuccessor(TreeNode root, TreeNode p) {
    Stack<TreeNode> stack = new Stack<>();
    TreeNode prev = null;
    TreeNode curr = root;
    while (!stack.isEmpty() || curr != null) {
        while (curr != null) {
            stack.push(curr);
            curr = curr.left;
        }

        curr = stack.pop();

        if (prev == p) {
            return curr;
        }
        prev = curr;
        curr = curr.right;
    }
    return null;
}

// 方法2：
public TreeNode inorderSuccessor(TreeNode root, TreeNode p) {
    if (root == null)
        return null;

    if (root.val <= p.val)
        return inorderSuccessor(root.right, p);

    TreeNode ans = inorderSuccessor(root.left, p);
    return ans == null ? root : ans;
}
*/