/**
 * copyright 2022/1/19
 */
package com.leetcode;

import com.leetcode.util.TreeNode;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import static com.leetcode.util.LogUtil.info;

/**
    145. 二叉树的后序遍历
    给你一棵二叉树的根节点 root ，返回其节点值的 后序遍历 。
    示例 1：
        输入：root = [1,null,2,3]
        输出：[3,2,1]
    示例 2：
        输入：root = []
        输出：[]
    示例 3：
        输入：root = [1]
        输出：[1]
*/
public class NO145_PostorderTraversal {

    @Test
    public void test() {
        info(postorderTraversal(
                new TreeNode(1,
                        null, new TreeNode(2,
                                    3))));
    }


    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        return res;
    }
}










/**
// 方法1
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