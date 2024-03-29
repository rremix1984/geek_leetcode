/**
 * copyright@2019/08/05 lsm
 */
package com.leetcode.tree;

import com.leetcode.util.TreeNode;
import org.junit.Test;
import static com.leetcode.util.MathUtils.cTree;

/**
    [ARRAY] |
    NO.101 对称二叉树
    给你一个二叉树的根节点 root ， 检查它是否轴对称。
    示例 1：
        输入：root = [1, 2, 2, 3, 4, 4, 3]
        输出：true
    示例 2：
        输入：root = [1, 2, 2, null, 3, null, 3]
        输出：false
    提示：
        树中节点数目在范围 [1, 1000] 内
        -100 <= Node.val <= 100
        进阶：你可以运用递归和迭代两种方法解决这个问题吗？
    Related Topics:树,深度优先搜索,广度优先搜索,二叉树
*/
public class NO101_E_IsSymmetric {

    @Test
    public void test() {
        assert isSymmetric(cTree(1,
                                     2, 2,
                                  3, 4, 4, 3));
        assert !isSymmetric(cTree(1,
                                     2,     2,
                                null, 3, null, 3));
    }

    public boolean isSymmetric(TreeNode root) {
        // 2024/3/15 NO.1
        return false;
    }

}















/*
// 方法1：
public boolean isSymmetric(TreeNode root) {
    if (root == null)
        return true;

    //调用递归函数，比较左节点，右节点
    return dfs(root.left, root.right);
}

boolean dfs(TreeNode left, TreeNode right) {
    //递归的终止条件是两个节点都为空
    //或者两个节点中有一个为空
    //或者两个节点的值不相等
    if (left == null && right == null)
        return true;

    if (left == null || right == null)
        return false;

    if (left.val != right.val)
        return false;

    //再递归的比较 左节点的左孩子 和 右节点的右孩子
    //以及比较  左节点的右孩子 和 右节点的左孩子
    return dfs(left.left, right.right)
        && dfs(left.right, right.left);
}
*/