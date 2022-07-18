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
    144. 二叉树的前序遍历
    给你二叉树的根节点 root ，返回它节点值的 前序 遍历。
    示例 1：
        输入：root = [1,null,2,3]
        输出：[1,2,3]
*/
public class NO144_PreorderTraversal {

    @Test
    public void test() {
        info(preorderTraversal(
                new TreeNode(1,
                    null, new TreeNode(2,
                                    3))));
    }

    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        return res;
    }
}











/**
public List<Integer> preorderTraversal(TreeNode root) {
    List<Integer> res = new ArrayList<>();
    if (root == null)
        return res;
    Stack<TreeNode> stack = new Stack<>();
    stack.push(root);
    while (!stack.isEmpty()) {
        TreeNode node = stack.pop();
        res.add(node.val);
        if (node.right != null)
            stack.push(node.right);
        if (node.left != null)
            stack.push(node.left);
    }
    return res;
}
*/
