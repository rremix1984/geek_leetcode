/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import com.leetcode.util.TreeNode;
import org.junit.Assert;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import static com.leetcode.util.MathUtils.cTree;
import static com.leetcode.util.MathUtils.getArray;
import static org.junit.Assert.assertEquals;

/**
    [TREE] ||||||
    （简单）
    NO.145 二叉树的后序遍历
    给你一棵二叉树的根节点 root ，返回其节点值的 后序遍历 。
    示例 1：
        输入：root = [1, null, 2, 3]
        输出：[3, 2, 1]
    示例 2：
        输入：root = []
        输出：[]
    示例 3：
        输入：root = [1]
        输出：[1]
*/
public class NO145_E_PostorderTraversal {

    @Test
    public void test() {
        assertEquals(getArray(3, 2, 1),
                postorderTraversal(cTree(1, null, 2, null, null, 3)));
        assertEquals(getArray(1, 2, 3),
                postorderTraversal(cTree(3, 1, 2)));
        assertEquals(getArray(0),
                postorderTraversal(cTree(0)));
        assertEquals(getArray(1),
                postorderTraversal(cTree(1)));
    }

    public List<Integer> postorderTraversal(TreeNode root) {
        // 2024/3/12 NO.1
        // 2024/3/16 NO.2 递归法很简单，迭代法没有真正理解
        // 2024/3/17 NO.3
        // 2024/3/19-22-23 NO.4-5-6 迭代法做出来了
        List<Integer> res = new ArrayList<>();
        return res;
    }

}










/*
// 方法1：递归法
List<Integer> res = new ArrayList<>();
public List<Integer> postorderTraversal(TreeNode root) {
    postorder(root);
    return res;
}

private void postorder(TreeNode root) {
    if (root == null) {
        return;
    }
    postorder(root.left);
    postorder(root.right);
    res.add(root.val);
}


// 方法2
public List<Integer> postorderTraversal(TreeNode root) {
    List<Integer> res = new ArrayList<>();
    if(root == null)
        return res;

    Stack<TreeNode> stack = new Stack<>();
    stack.push(root);
    while (!stack.isEmpty()) {
        TreeNode node = stack.pop();

        //和传统先序遍历不一样，先将左结点入栈
        if(node.left != null)
            stack.push(node.left);

        //后将右结点入栈
        if(node.right != null)
            stack.push(node.right);

        //逆序添加结点值
        res.add(0, node.val);
    }
    return res;
}
*/