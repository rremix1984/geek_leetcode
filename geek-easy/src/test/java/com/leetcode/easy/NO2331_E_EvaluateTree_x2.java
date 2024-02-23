/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import com.leetcode.util.TreeNode;
import org.junit.Test;
import static com.leetcode.util.MathUtils.cTree;

/**
    [TREE]
    (简单)
    2331. 计算布尔二叉树的值
        给你一棵 完整二叉树 的根，这棵树有以下特征：
        叶子节点 要么值为 0 要么值为 1 ，其中 0 表示 False ，1 表示 True 。
        非叶子节点 要么值为 2 要么值为 3 ，其中 2 表示逻辑或 OR ，3 表示逻辑与 AND 。
        计算 一个节点的值方式如下：
            如果节点是个叶子节点，那么节点的 值 为它本身，即 True 或者 False 。
            否则，计算 两个孩子的节点值，然后将该节点的运算符对两个孩子值进行 运算 。
            返回根节点 root 的布尔运算值。
        完整二叉树 是每个节点有 0 个或者 2 个孩子的二叉树。
        叶子节点 是没有孩子的节点。
    示例 1：
        输入：root = {2, 1, 3, null, null, 0, 1}
        输出：true
        解释：上图展示了计算过程。
             AND 与运算节点的值为 False AND True = False 。
             OR 运算节点的值为 True OR False = True 。
             根节点的值为 True ，所以我们返回 true 。
    示例 2：
        输入：root = {0}
        输出：false
        解释：根节点是叶子节点，且值为 false，所以我们返回 false 。
*/
public class NO2331_E_EvaluateTree_x2 {

    @Test
    public void test() {
        assert evaluateTree(cTree(2, 1, 3, null, null, 0, 1));
        assert !evaluateTree(new TreeNode(0));
    }

    public boolean evaluateTree(TreeNode root) {
        return false;
    }

}
















/**
public boolean evaluateTree(TreeNode root) {
    return dfs(root);
}

public boolean dfs(TreeNode root) {
    if (root.left == null && root.right == null)
        return root.val == 1;

    if (root.val == 2)
        return dfs(root.left) || dfs(root.right);

    return dfs(root.left) && dfs(root.right);
}
*/