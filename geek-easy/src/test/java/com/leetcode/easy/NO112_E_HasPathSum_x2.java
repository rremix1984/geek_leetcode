/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import com.leetcode.util.TreeNode;
import org.junit.Test;

import java.util.LinkedList;
import java.util.Queue;

import static com.leetcode.util.MathUtils.cTree;

/**
    (简单)
    112. 路径总和
        给你二叉树的根节点 root 和一个表示目标和的整数 targetSum 。
        判断该树中是否存在根节点到叶子节点的路径，这条路径上所有节点值相加等于目标和targetSum。
        如果存在，返回 true ；否则，返回 false 。
        【叶子节点 是指没有子节点的节点】
    示例 1：
        输入：root = [5, 4, 8, 11, null, 13, 4, 7, 2, null, null, null, 1],
             targetSum = 22
        输出：true
        解释：等于目标和的根节点到叶节点路径如上图所示。
    示例 2：
        输入：root = [1, 2, 3], targetSum = 5
        输出：false
        解释：树中存在两条根节点到叶子节点的路径：
             (1 --> 2): 和为 3
             (1 --> 3): 和为 4
             不存在 sum = 5 的根节点到叶子节点的路径。
    示例 3：
        输入：root = [], targetSum = 0
        输出：false
        解释：由于树是空的，所以不存在根节点到叶子节点的路径。
    提示：
        树中节点的数目在范围 [0, 5000] 内
        -1000 <= Node.val <= 1000
        -1000 <= targetSum <= 1000
*/
public class NO112_E_HasPathSum_x2 {

    @Test
    public void test() {
        assert hasPathSum(
            cTree(5, 4, 8, 11, null, 13, 4, 7, 2, null, null, null, 1),22);
        assert !hasPathSum(
            cTree(1, 2, 3), 5);
        assert !hasPathSum(null, 0);
    }

    public boolean hasPathSum(TreeNode root, int sum) {
        return false;
    }

}



















/**
// 方法1：
public boolean hasPathSum(TreeNode root, int sum) {
    if (root == null)
        return false;

    Queue<TreeNode> queNode = new LinkedList<>();
    queNode.offer(root);

    Queue<Integer> queVal = new LinkedList<>();
    queVal.offer(root.val);

    while (!queNode.isEmpty()) {
        TreeNode node = queNode.poll();
        int temp = queVal.poll();
        // 叶节点
        if (node.left == null && node.right == null) {
            // 累计和等于 sum 时，代表找到了
            if (temp == sum)
                return true;
            continue;
        }

        // 左边不为空
        if (node.left != null) {
            queNode.offer(node.left);
            queVal.offer(node.left.val + temp);
        }

        // 右边不为空
        if (node.right != null) {
            queNode.offer(node.right);
            queVal.offer(node.right.val + temp);
        }
    }
    return false;
}
*/