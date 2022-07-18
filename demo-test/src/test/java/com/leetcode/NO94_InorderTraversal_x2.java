/**
 * copyright 2022/1/19
 */
package com.leetcode;

import com.leetcode.util.TreeNode;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import static com.leetcode.util.LogUtil.info;

/**
    94. 二叉树的中序遍历
    给定一个二叉树的根节点 root ，返回 它的 中序 遍历 。
    示例 1：
        输入：root = [1,null,2,3]
        输出：[1,3,2]
*/
public class NO94_InorderTraversal_x2 {

    @Test
    public void test() {
        info(inorderTraversal(new TreeNode(
                1,
        null, new TreeNode(2,
                    3))));
    }

    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode cur = root;
        while (cur != null || !stack.isEmpty()) {
            while (cur != null) {
                stack.push(cur);
                cur = cur.left;
            }
            TreeNode node = stack.pop();
            res.add(node.val);
            cur = node.right;
        }
        return res;
    }

}











/**
public List<Integer> inorderTraversal(TreeNode root) {
    List<Integer> res = new ArrayList<>();
    if (root == null) {
        return res;
    }
    Stack<TreeNode> stack = new Stack<>();
    TreeNode cur = root;
    while (cur != null || !stack.isEmpty()) {
        while (cur != null) {
            stack.push(cur);
            cur = cur.left;
        }
        TreeNode node = stack.pop();
        res.add(node.val);
        cur = node.right;
    }
    return res;
}
*/