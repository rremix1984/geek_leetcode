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

import static com.leetcode.util.LogUtil.info;
import static com.leetcode.util.MathUtils.cTree;
import static com.leetcode.util.MathUtils.getArray;
import static java.util.Collections.emptyList;
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
public class NO144_E_PreorderTraversal_x5 {

    @Test
    public void test() {
        Assert.assertEquals(getArray(1, 2, 3),
            preorderTraversal(cTree(1, null, 2, null, null, 3)));
        Assert.assertEquals(emptyList(), preorderTraversal(cTree()));// []
        Assert.assertEquals(getArray(1), preorderTraversal(cTree(1)));// [1]
        Assert.assertEquals(getArray(1, 2), preorderTraversal(cTree(1, 2, null)));// [1, 2]
        Assert.assertEquals(getArray(1, 2), preorderTraversal(cTree(1, null, 2)));// [1, 2]
    }

    public static List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> ans = new ArrayList<>();
        return ans;
    }

}











/**
// 方法1：迭代法
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

// 方法2：莫里斯遍历
public List<Integer> preorderTraversal(TreeNode root) {
    List<Integer> ans = new ArrayList<>();
    if(root == null) return ans;
    //morris遍历
    TreeNode current = root;

    while (current != null) {
        TreeNode mostR = current.left;
        if(mostR != null){
            while(mostR.right != null && mostR.right != current)
                mostR = mostR.right;

            if(mostR.right == null){
                ans.add(current.val);
                mostR.right = current;
                current = current.left;
            }else{
                //第二次遍历该节点
                current = current.right;
                mostR.right = null;
            }
        } else {
            ans.add(current.val);
            current = current.right;
        }
    }
    return ans;
}
*/