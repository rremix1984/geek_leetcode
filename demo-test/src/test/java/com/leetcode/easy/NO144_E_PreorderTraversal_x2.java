/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import com.leetcode.util.TreeNode;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import static com.leetcode.util.LogUtil.info;
import static org.junit.Assert.assertEquals;

/**
    （简单）
    144. 二叉树的前序遍历
    给你二叉树的根节点 root ，返回它节点值的 前序 遍历。
    示例 1：
        输入：root = [1,null,2,3]
        输出：[1,2,3]
    示例 2：
        输入：root = []
        输出：[]
    示例 3：
        输入：root = [1]
        输出：[1]
    示例 4：
        输入：root = [1,2]
        输出：[1, 2]
    示例 5：
        输入：root = [1,null,2]
        输出：[1, 2]
*/
public class NO144_E_PreorderTraversal_x2 {

    @Test
    public void test() {
        assertEquals(new ArrayList<Integer>(){{add(1);add(2);add(3);}},
            preorderTraversal(new TreeNode(1,
                            null, new TreeNode(2,
                                        3))));// [1, 2, 3]
        assertEquals(new ArrayList<Integer>(){{add(0);}},
            preorderTraversal(new TreeNode()));// []
        assertEquals(new ArrayList<Integer>(){{add(1);}},
            preorderTraversal(new TreeNode(1)));// [1]
        assertEquals(new ArrayList<Integer>(){{add(1);add(2);}},
            preorderTraversal(new TreeNode(1,2, null)));// [1, 2]
        assertEquals(new ArrayList<Integer>(){{add(1);add(2);}},
            preorderTraversal(new TreeNode(1,null,2)));// [1, 2]
    }

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
