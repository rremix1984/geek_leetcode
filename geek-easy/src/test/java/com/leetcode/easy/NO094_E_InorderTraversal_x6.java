/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import com.leetcode.util.MathUtils;
import com.leetcode.util.TreeNode;
import org.junit.Assert;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

import static com.leetcode.util.LogUtil.info;
import static com.leetcode.util.MathUtils.createFullTree;
import static com.leetcode.util.MathUtils.getArray;
import static org.junit.Assert.assertEquals;

/**
    (简单)
    94. 二叉树的中序遍历
    给定一个二叉树的根节点 root ，返回 它的 中序 遍历 。
    示例 1：
        输入：root = [1, null, 2, 3]
        输出：[1, 3, 2]
*/
public class NO094_E_InorderTraversal_x6 {

    @Test
    public void test() {
//        assertEquals(getArray(1, 3, 2),
//                inorderTraversal(createFullTree(1, null, 2, null, null, 3)));
        Assert.assertEquals(getArray(3, 9, 12, 11, 10),
                inorderTraversal(new TreeNode(3,
                            null, new TreeNode(9,
                                    null, new TreeNode(10,
                                        new TreeNode(11,
                                new TreeNode(12)))))));// [3, 9, 12, 11, 10]
//        assertEquals(getArray(9, 3, 15, 20, 7),
//                inorderTraversal(new TreeNode(3,
//                            9, new TreeNode(20,
//                                        15, 7))));// [9, 3, 15, 20, 7]
//        assertEquals(getArray(0),
//                inorderTraversal(new TreeNode()));// []
//        assertEquals(getArray(1),
//                inorderTraversal(new TreeNode(1)));// [1]
    }

    List<Integer> res = new ArrayList<>();

    public List<Integer> inorderTraversal(TreeNode root) {
        return res;
    }

}











/**
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