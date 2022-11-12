/**
 * copyright 2022/1/19
 */
package com.leetcode.normal;

import com.leetcode.util.TreeNode;
import org.junit.Test;
import static com.leetcode.util.MathUtils.cTree;

/**
    (中等)
    129. 求根节点到叶节点数字之和
        给你一个二叉树的根节点 root ，树中每个节点都存放有一个 0 到 9 之间的数字。
        每条从根节点到叶节点的路径都代表一个数字：
        例如，从根节点到叶节点的路径 1 -> 2 -> 3 表示数字 123 。
        计算从根节点到叶节点生成的 所有数字之和 。
        叶节点 是指没有子节点的节点。
    示例 1：
        输入：root = [1,2,3]
        输出：25
        解释：从根到叶子节点路径 1->2 代表数字 12
             从根到叶子节点路径 1->3 代表数字 13
             因此，数字总和 = 12 + 13 = 25
    示例 2：
        输入：root = [4,9,0,5,1]
        输出：1026
        解释：从根到叶子节点路径 4->9->5 代表数字 495
             从根到叶子节点路径 4->9->1 代表数字 491
             从根到叶子节点路径 4->0 代表数字 40
             因此，数字总和 = 495 + 491 + 40 = 1026
    提示：
        树中节点的数目在范围 [1, 1000] 内
        0 <= Node.val <= 9
        树的深度不超过 10
*/
public class NO129_N_SumNumbers_x2 {

    @Test
    public void test() {
        assert 25 == sumNumbers(cTree(1,2,3));
        assert 1026 == sumNumbers(cTree(4,9,0,5,1));
    }

    public int sumNumbers(TreeNode root) {
        return 0;
    }

}
















/**
// 方法1：
public int sumNumbers(TreeNode root) {
    return dfs(root, 0);
}

public int dfs(TreeNode root, int prevSum) {
    if (root == null)
        return 0;
    // 上一次的值需要乘10
    // 例如：1 x 100 + 2 x 10 + 3 = 123;
    int sum = prevSum * 10 + root.val;

    // 如果是叶节点直接返回
    if (root.left == null && root.right == null)
        return sum;

    // 如果不是叶节点，就往下面继续找
    return dfs(root.left, sum) + dfs(root.right, sum);
}
*/