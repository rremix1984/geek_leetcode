/**
 * @copyright wxz
 */
package com.leetcode.undo;

import com.leetcode.util.TreeNode;
import org.junit.Test;
import static com.leetcode.util.TreeNode.treeEquals;

/**
    [ARRAY] |
    (中等)
    NO.701 二叉搜索树中的插入操作
    给定二叉搜索树（BST）的根节点 root 和要插入树中的值 value ，将值插入二叉搜索树。
    返回插入后二叉搜索树的根节点。 输入数据 保证 ，新值和原始二叉搜索树中的任意节点值
    都不同。
    注意，可能存在多种有效的插入方式，只要树在插入后仍保持为二叉搜索树即可。 你可以返回
    任意有效的结果。
    示例 1：
        输入：root = [4, 2, 7, 1, 3], val = 5
        输出：[4, 2, 7, 1, 3, 5]
        解释：另一个满足题目要求可以通过的树是：
    示例 2：
        输入：root = [40, 20, 60, 10, 30, 50, 70], val = 25
        输出：[40, 20, 60, 10, 30, 50, 70, null, null, 25]
    示例 3：
        输入：root = [4, 2, 7, 1, 3, null, null, null, null, null, null], val = 5
        输出：[4, 2, 7, 1, 3, 5]
        提示：
            树中的节点数将在 [0, 104]的范围内。
            -108 <= Node.val <= 108
            所有值 Node.val 是 独一无二 的。
            -108 <= val <= 108
        保证 val 在原始BST中不存在。
    Related Topics:树,二叉搜索树,二叉树
*/
public class NO701_N_InsertIntoBST {

    @Test
    public void test() {
        assert treeEquals(insertIntoBST(
                new TreeNode<>(4, new TreeNode<>(2,
                        new TreeNode<>(7), new TreeNode<>(1)),
                        new TreeNode<>(3)), 5),
                new TreeNode<>(4, new TreeNode<>(2,
                        new TreeNode<>(7), new TreeNode<>(1)),
                        new TreeNode<>(3, null, new TreeNode<>(5))));
        assert treeEquals(insertIntoBST(
                new TreeNode<>(4, new TreeNode<>(2,
                        new TreeNode<>(7), new TreeNode<>(1)),
                        new TreeNode<>(3)), 5),
                new TreeNode<>(4, new TreeNode<>(2,
                        new TreeNode<>(7), new TreeNode<>(1)),
                        new TreeNode<>(3, null, new TreeNode<>(5))));
    }

    public TreeNode<Integer> insertIntoBST(TreeNode<Integer> root, int val) {
        // TODO
        return root;
    }

}


















/*
// 方法1：
public TreeNode<Integer> insertIntoBST(TreeNode<Integer> root, int val) {
    TreeNode<Integer> node = new TreeNode<>(val);
    if (root == null)
        return node;

    TreeNode<Integer> cur = root;
    while (true) {
        if (cur.val > val) {
            if (cur.left == null) {
                cur.left = node;
                break;
            }
            cur = cur.left;
        } else {
            if (cur.right == null) {
                cur.right = node;
                break;
            }
            cur = cur.right;
        }
    }
    return root;
}
*/