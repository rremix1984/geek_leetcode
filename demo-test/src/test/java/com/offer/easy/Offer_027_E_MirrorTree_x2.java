/**
 * copyright 2022/1/19
 */
package com.offer.easy;

import com.leetcode.util.TreeNode;
import org.junit.Test;
import static com.leetcode.util.MathUtils.cTree;

/**
    (简单)
    剑指 Offer 27. 二叉树的镜像
        请完成一个函数，输入一个二叉树，该函数输出它的镜像。
        例如输入：
                4
              /   \
             2     7
            / \   / \
           1   3 6   9
        镜像输出：
                4
              /   \
             7     2
            / \   / \
           9   6 3   1
    示例 1：
        输入：root = [4, 2, 7, 1, 3, 6, 9]
        输出：[4, 7, 2, 9, 6, 3, 1]
*/
public class Offer_027_E_MirrorTree_x2 {

    @Test
    public void test() {
        TreeNode<Integer> source = cTree(4, 2, 7, 1, 3, 6, 9);
        TreeNode<Integer> target = cTree(4, 7, 2, 9, 6, 3, 1);
        assert mirrorTree(source).equals(target);

        TreeNode<Integer> source2 = cTree(2, 1, null, 3);
        TreeNode<Integer> target2 = cTree(2, null, 1, null, null, null, 3);
        assert mirrorTree(source2).equals(target2);
    }

    public TreeNode<Integer> mirrorTree(TreeNode<Integer> root) {
        if (root == null)
            return null;

        return root;
    }

}



















/*
// 方法1：递归法
public TreeNode mirrorTree(TreeNode root) {
    if (root == null)
        return null;

    TreeNode left = mirrorTree(root.left);
    TreeNode right = mirrorTree(root.right);
    root.left = right;
    root.right = left;
    return root;
}

// 方法2：辅助栈
public TreeNode mirrorTree(TreeNode root) {
    if (root == null)
        return null;

    Stack<TreeNode> stack = new Stack<>();
    stack.push(root);

    while(!stack.isEmpty()) {
        TreeNode node = stack.pop();
        if(node.left != null)
            stack.add(node.left);

        if(node.right != null)
            stack.add(node.right);

        TreeNode tmp = node.left;
        node.left = node.right;
        node.right = tmp;
    }
    return root;
}
*/