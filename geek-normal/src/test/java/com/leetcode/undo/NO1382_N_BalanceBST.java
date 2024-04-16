/**
 * @copyright wxz
 */
package com.leetcode.undo;

import com.leetcode.util.TreeNode;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static com.leetcode.util.TreeNode.treeEquals;

/**
    [ARRAY]
    (中等,经典面试题)
    NO.1382 将二叉搜索树变平衡
    给你一棵二叉搜索树，请你返回一棵 平衡后 的二叉搜索树，新生成的树应该与原来
    的树有着相同的节点值。如果有多种构造方法，请你返回任意一种。
    如果一棵二叉搜索树中，每个节点的两棵子树高度差不超过 1 ，我们就称这棵二叉
    搜索树是平衡的。
    示例 1：
        输入：root = [1, null, 2, null, 3, null, 4, null, null]
        输出：[2, 1, 3, null, null, null, 4]
        解释：这不是唯一的正确答案，[3, 1, 4, null, 2, null, null]
             也是一个可行的构造方案。
            1                                      2
         /    \                                  /   \
        null   2                                1     3
             /  \                                       \
           null  3            ===>                        4
                /  \
              null  4

    示例 2：
        输入: root = [2, 1, 3]
        输出: [2, 1, 3]
    提示：
        树节点的数目在 [1, 104] 范围内。
        1 <= Node.val <= 105
    Related Topics:贪心,树,深度优先搜索,二叉搜索树,分治,二叉树
    解题思路:
        中序遍历，搜索二叉树是升序排列
        在[l, r]上二分构造二叉树
*/
public class NO1382_N_BalanceBST {

    @Test
    public void test() {
        assert treeEquals(
            balanceBST(new TreeNode<>(1, null,
                            new TreeNode<>(3,
                            new TreeNode<>(2),
                                new TreeNode<>(4)))),
                        new TreeNode<>(2,
                            new TreeNode<>(1),
                            new TreeNode<>(3, null,
                                new TreeNode<>(4))));
        assert treeEquals(
            balanceBST(new TreeNode<>(2,
                            new TreeNode<>(1),
                            new TreeNode<>(3))),
                        new TreeNode<>(2,
                            new TreeNode<>(1),
                            new TreeNode<>(3)));
    }

    public TreeNode<Integer> balanceBST(TreeNode<Integer> root) {
        // TODO
        List<Integer> nums = new ArrayList<>();
        this.f(root, nums);
        return this.f2(0, nums.size() - 1, nums);
    }

    private TreeNode<Integer> f2(int l, int r, List<Integer> nums) {
        if (l > r)
            return null;

        if (l == r)
            return new TreeNode<>(nums.get(l));

        int m = ((r - l) >> 1) + l;
        TreeNode<Integer> ans = new TreeNode<>(nums.get(m));
        ans.left = f2(l, m - 1, nums);
        ans.right = f2(m + 1, r, nums);
        return ans;
    }

    private void f(TreeNode<Integer> root, List<Integer> nums) {
        if (root == null)
            return;

        f(root.left, nums);
        nums.add(root.val);
        f(root.right, nums);
    }

}
















/*
// 方法1：
public TreeNode<Integer> balanceBST(TreeNode<Integer> root) {
    List<Integer> nums = new ArrayList<>();
    this.f(root, nums);
    return this.f2(0, nums.size() - 1, nums);
}

private TreeNode<Integer> f2(int l, int r, List<Integer> nums) {
    if (l > r)
        return null;

    if (l == r)
        return new TreeNode<>(nums.get(l));

    int m = ((r - l) >> 1) + l;
    TreeNode<Integer> ans = new TreeNode<>(nums.get(m));
    ans.left = f2(l, m - 1, nums);
    ans.right = f2(m + 1, r, nums);
    return ans;
}

private void f(TreeNode<Integer> root, List<Integer> nums) {
    if (root == null)
        return;

    f(root.left, nums);
    nums.add(root.val);
    f(root.right, nums);
}
*/