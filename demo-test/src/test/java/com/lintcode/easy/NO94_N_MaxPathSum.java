package com.lintcode.easy;

import com.lintcode.util.TreeNode;
import lombok.val;
import org.junit.Test;

import static com.lintcode.util.TreeNode.print;
import static java.lang.Integer.MIN_VALUE;
import static java.lang.Math.max;

/**
    [TREENODE]
    NO.94 二叉树中的最大路径和
    描述
        给出一棵二叉树，寻找一条路径使其路径和最大，路径可以在任意一个节点中
        开始和结束（路径和为两个节点之间所在路径上的节点权值之和）
    样例
    样例 1：
        输入：tree = {2}
        输出：2
        解释：只有一个节点2
    样例 2：
        输入：
            tree = {1, 2, 3}
        输出：6
        解释：
            如下图，最长路径为2-1-3
                1
               / \
              2   3
    样例 3：
        输入：
            tree = {1, 2, 3, 4, 9, 6, null, 1, 3, 4, #, 8, 12, null, 14, null, 3, 6}
        输出：43
        解释：如下图，最长路径为 14 - 1 - 4 - 2 - 1 - 3 - 6 - 12

*/
public class NO94_N_MaxPathSum {

    @Test
    public void test() {
        TreeNode tree = new TreeNode(1, 2, 3, 4, 9, 6, null,
                1, 3, 4, null, 8, 12, null, 14, null, 3, 6);
        print(tree);
        assert 43 == maxPathSum(tree);

        TreeNode tree2 = new TreeNode(1,2, 3);
        print(tree2);
        assert 6 == maxPathSum(tree2);
        assert 2 == maxPathSum(new TreeNode(2));
    }

    private int maxSum = MIN_VALUE;

    public int maxPathSum(TreeNode root) {
        maxGain(root);
        return maxSum;
    }

    /**
     * 辅助递归函数，计算节点的最大贡献值
     * 最大贡献值定义为从当前节点出发，向下走到任何节点所能获得的最大路径和
     * 该函数同时更新全局最大路径和
     * @param node 当前考虑的节点
     * @return 返回节点的最大贡献值
     */
    private int maxGain(TreeNode node) {
        if (node == null)
            return 0;

        // 只有当子树贡献值为正数时，才会选择该子树
        int leftGain = max(maxGain(node.left), 0);
        int rightGain = max(maxGain(node.right), 0);

        // 当前节点的价值加上其左右子树的最大贡献值
        int currentPathSum = node.val + leftGain + rightGain;

        // 更新全局最大路径和
        maxSum = max(maxSum, currentPathSum);

        // 返回节点的最大贡献值，即当前节点值加上其最大子树贡献值
        return node.val + max(leftGain, rightGain);
    }

}