/**
 * copyright 2022/1/19
 */
package com.leetcode.normal;

import com.leetcode.util.TreeNode;
import org.junit.Test;

import java.util.Stack;

import static com.leetcode.util.LogUtil.info;
import static java.lang.Long.MIN_VALUE;

/**
    (中等)
    98. 验证二叉搜索树
    给你一个二叉树的根节点 root ，判断其是否是一个有效的二叉搜索树。
    有效 二叉搜索树定义如下：
    节点的左子树只包含 小于 当前节点的数。
    节点的右子树只包含 大于 当前节点的数。
    所有左子树和右子树自身必须也是二叉搜索树。
    示例 1：
        输入：root = [2,1,3]
        输出：true
    示例 2：
        输入：root = [5,1,4,null,null,3,6]
        输出：false
        解释：根节点的值是 5 ，但是右子节点的值是 4
*/
@SuppressWarnings("all")
public class NO98_ValidateBinarySearchTree_x2 {

    @Test
    public void test() {
//        info(isValidBST(new TreeNode(2,
//                                1, 3))); // true
//        info(isValidBST(new TreeNode(
//                        5,
//                1, new TreeNode(4,
//                                3,6))));// false
        info(isValidBST(new TreeNode(
                    5,
            4, new TreeNode(6,
                            3,7))));// false
//        info(isValidBST(new TreeNode(0)));// true
    }

    long pre = MIN_VALUE;

    public boolean isValidBST(TreeNode root) {
        if (root == null)
            return true;

        if (!isValidBST(root.left))
            return false;

        if (root.val <= pre)
            return false;

        pre = root.val;
        return isValidBST(root.right);
    }

}















/**
// 方案1 递归法
long pre = Long.MIN_VALUE;
public boolean isValidBST(TreeNode root) {
    if (root == null)
        return true;

    if (!isValidBST(root.left))
        return false;

    if (root.val <= pre)
        return false;

    pre = root.val;
    return isValidBST(root.right);
}


// 方案2 中序遍历
public boolean isValidBST(TreeNode root) {
    // 记录前一个节点
    TreeNode pre = null;
    Stack<TreeNode> stack = new Stack<>();
    while (stack.size() > 0 || root != null) {
        // 一直向左子树走，每一次将当前节点保存到栈中
        if (root != null) {
            stack.add(root);
            root = root.left;
        // 当前节点为空，证明走到了最左边，从栈中弹出节点
        // 开始对右子树重复上述过程
        } else {
            TreeNode cur = stack.pop();
            // 判断序列是否有序
            if (pre != null && cur.val <= pre.val)
                return false;
            pre = cur;
            root = cur.right;
        }
    }
    return true;
}
*/