/**
 * copyright 2022/1/19
 */
package com.leetcode.offer;

import com.leetcode.util.TreeNode;
import org.junit.Test;

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
public class Offer27_E_MirrorTree_x2 {

    @Test
    public void test() {
        TreeNode tmp = new TreeNode(4,
                new TreeNode(2,
                        1, 3), new TreeNode(7,
                6, 9));
        assert mirrorTree(tmp).toString().equals(new TreeNode(4,
                new TreeNode(7,
                        9, 6), new TreeNode(2,
                3, 1)).toString());
        TreeNode tmp2 = new TreeNode(2,
                new TreeNode(1,
                        3));
        assert mirrorTree(tmp2).toString().equals(
                new TreeNode(2,
                        null, new TreeNode(1,
                        null,3)).toString()
        );
    }

    public TreeNode mirrorTree(TreeNode root) {
        if (root == null)
            return null;
        return root;
    }

}



















/**
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

    Stack<TreeNode> stack = new Stack<TreeNode>(){{
        add(root);
    }};

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