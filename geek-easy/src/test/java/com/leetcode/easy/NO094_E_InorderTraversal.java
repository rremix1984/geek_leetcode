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
    [TREE] |||||||
    (简单)
    NO.94 二叉树的中序遍历
    给定一个二叉树的根节点 root ，返回 它的 中序 遍历 。
    示例 1：
        输入：root = [1, null, 2, 3]
        输出：[1, 3, 2]
*/
public class NO094_E_InorderTraversal {

    @Test
    public void test() {
        assertEquals(getArray(1, 3, 2),
                inorderTraversal(cTree( 1,
                                        null,     2,
                                    null, null, 3)));
        assertEquals(getArray(3, 9, 12, 11, 10),
                inorderTraversal(new TreeNode<>(3,
                            null, new TreeNode<>(9,
                                    null, new TreeNode<>(10,
                                        new TreeNode<>(11,
                                new TreeNode<>(12)))))));
        assertEquals(getArray(9, 3, 15, 20, 7),
                inorderTraversal(new TreeNode<>(3,
                            new TreeNode<>(9), new TreeNode<>(20,
                                        15, 7))));
        assertEquals(getArray(0),
                inorderTraversal(new TreeNode<>()));
        assertEquals(getArray(1),
                inorderTraversal(new TreeNode<>(1)));
    }

    public List<Integer> inorderTraversal(TreeNode<Integer> root) {
        // 2024/3/12 NO.1
        // 2024/3/16 NO.2 迭代法、递归法 需要复习一下
        // 2024/3/19 NO.3 迭代法 还是不熟悉，要看答案才能做出来
        // 2024/3/22 NO.4 还是没做出来
        // 2024/3/23 NO.5 思路对，但是忘了怎么做了，还是没一次过
        // 2024/3/27 NO.6 一遍过
        // 2024/3/30 NO.7 居然忘了怎么做了...
        // 2024/4/1  NO.8 一遍过
        // 2024/4/15 NO.9 没做对，但是思路对了，忘了一部分了
        List<Integer> res = new ArrayList<>();

        return res;
    }

}











/*
// 方法1：
public List<Integer> inorderTraversal(TreeNode root) {
    List<Integer> res = new ArrayList<>();
    if (root == null)
        return res;
    Stack<TreeNode> stack = new Stack<>();
    while (root != null || !stack.isEmpty()) {
        while (root != null) {
            stack.push(root);
            root = root.left;
        }
        TreeNode node = stack.pop();
        res.add(node.val);
        root = node.right;
    }
    return res;
}

// 方案2
List<Integer> res = new ArrayList<>();
public List<Integer> inorderTraversal(TreeNode root) {
    inorder(root);
    return res;
}

public void inorder(TreeNode root) {
    if (root == null)
        return;

    if (root.left != null)
        inorder(root.left);

    res.add(root.val);

    if (root.right != null)
        inorder(root.right);
}
*/