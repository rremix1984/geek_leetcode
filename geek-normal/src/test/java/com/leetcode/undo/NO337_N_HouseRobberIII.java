/**
 * copyright 2022/1/19
 */
package com.leetcode.undo;

import com.leetcode.util.TreeNode;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static com.leetcode.util.MathUtils.cTree;
import static java.lang.Math.max;

/**
    [ARRAY] |
    (中等)
    NO.337. 打家劫舍 III
        小偷又发现了一个新的可行窃的地区。这个地区只有一个入口，我们称之为 root 。
        除了 root 之外，每栋房子有且只有一个“父“房子与之相连。一番侦察之后，
        聪明的小偷意识到“这个地方的所有房屋的排列类似于一棵二叉树”。 如果两个直接
        相连的房子在同一天晚上被打劫，房屋将自动报警。
        给定二叉树的 root 。返回 在不触动警报的情况下 ，小偷能够盗取的最高金额 。
    示例 1:
        输入: root = [    3,
                     2,       3,
                null,  3, null,  1]
        输出: 7
        解释: 小偷一晚能够盗取的最高金额 3 + 3 + 1 = 7
    示例 2:
        输入: root = [    3,
                     4,       5,
                 1,    3, null,  1]
        输出: 9
        解释: 小偷一晚能够盗取的最高金额 4 + 5 = 9
    思路：
        f 映射一个节点到一个值，表示如果偷这个节点，能从这个节点的子树中得到的最大金额。
        g 映射一个节点到一个值，表示如果不偷这个节点，能从这个节点的子树中得到的最大金额。
 */
public class NO337_N_HouseRobberIII {

    @Test
    public void test() {
        assert 7 == rob(cTree(3, 2, 3, null, 3, null, 1));
        assert 9 == rob(cTree(3, 4, 5, 1, 3, null, 1));
    }

    public int rob(TreeNode root) {
        // 2024/4/1 NO.1
        Map<TreeNode, Integer> f = new HashMap<>(),
                               g = new HashMap<>();
        dfs(f, g, root);

        return max(f.getOrDefault(root, 0),
                   g.getOrDefault(root, 0));
    }

    public void dfs(Map<TreeNode, Integer> f, Map<TreeNode, Integer> g,
                    TreeNode node) {
        // TODO
        if (node == null)
            return;

    }

}



















/*
// 方法1：
public int rob(TreeNode root) {
    Map<TreeNode, Integer> f = new HashMap<>(),
                           g = new HashMap<>();
    dfs(f, g, root);
    return max(f.getOrDefault(root, 0),
               g.getOrDefault(root, 0));
}

public void dfs(Map<TreeNode, Integer> f, Map<TreeNode, Integer> g,
                TreeNode node) {
    if (node == null)
        return;

    // 在处理root节点之前，先把左右节点处理掉
    dfs(f, g, node.left);

    dfs(f, g, node.right);

    // 只偷根节点，左、右子树都不偷
    f.put(node, node.val
            + g.getOrDefault(node.left, 0)
            + g.getOrDefault(node.right, 0));

    // 不偷根节点，左、右子树都偷
    g.put(node, 0
          + max(f.getOrDefault(node.left, 0),
                g.getOrDefault(node.left, 0))
          + max(f.getOrDefault(node.right, 0),
                g.getOrDefault(node.right, 0)));
}
*/