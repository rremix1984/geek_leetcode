/**
 * copyright 2022/1/19
 */
package com.offer.normal;

import com.leetcode.util.TreeNode;
import org.junit.Test;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Stack;

import static com.leetcode.util.MathUtils.createFullTree;

/**
    (中等)
    剑指 Offer 07. 重建二叉树
        输入某二叉树的前序遍历和中序遍历的结果，请构建该二叉树并返回其根节点。
        假设输入的前序遍历和中序遍历的结果中都不含重复的数字。
    示例 1:
        Input: preorder = [3, 9, 20, 15, 7], inorder = [9, 3, 15, 20, 7]
        Output: [3, 9, 20, null, null, 15, 7]
    示例 2:
        Input: preorder = [-1], inorder = [-1]
        Output: [-1]
*/
public class Offer_07_N_BuildTreeLcof_x2 {

    @Test
    public void test() {
        assert createFullTree(3, 9, 20, null, null, 15, 7).equals(
                buildTree(new int[]{3, 9, 20, 15, 7}, new int[]{9, 3, 15, 20, 7}
            ));
        assert createFullTree(-1).equals(
                buildTree(new int[]{-1}, new int[]{-1}
            ));
    }

    public TreeNode buildTree(int[] preorder, int[] inorder) {
        return null;
    }

}



















/**
// 方法1：
public TreeNode buildTree(int[] preorder, int[] inorder) {
    if (preorder == null || preorder.length == 0) {
        return null;
    }
    TreeNode root = new TreeNode(preorder[0]);
    Deque<TreeNode> stack = new LinkedList<TreeNode>();
    stack.push(root);
    int inorderIndex = 0;
    for (int i = 1; i < preorder.length; i++) {
        int preorderVal = preorder[i];
        TreeNode node = stack.peek();
        if (node.val != inorder[inorderIndex]) {
            node.left = new TreeNode(preorderVal);
            stack.push(node.left);
        } else {
            while (!stack.isEmpty() && stack.peek().val == inorder[inorderIndex]) {
                node = stack.pop();
                inorderIndex++;
            }
            node.right = new TreeNode(preorderVal);
            stack.push(node.right);
        }
    }
    return root;
}
*/