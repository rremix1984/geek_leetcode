/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import com.leetcode.util.TreeNode;
import org.junit.Test;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import static com.leetcode.util.MathUtils.cTree;
import static com.leetcode.util.MathUtils.getArray;

/**
    [TREE]
    (简单)
    637. 二叉树的层平均值
        给定一个非空二叉树的根节点 root , 以数组的形式返回每一层节点的平均值。
        与实际答案相差 10 ^ -5 以内的答案可以被接受。
    示例 1：
        输入：root = {3, 9, 20, null, null, 15, 7}
        输出：{3.00000, 14.50000, 11.00000}
        解释：第 0 层的平均值为 3,第 1 层的平均值为 14.5,第 2 层的平均值为 11 。
        因此返回 {3, 14.5, 11} 。
    示例 2:
        输入：root = {3, 9, 20, 15, 7}
        输出：{3.00000, 14.50000, 11.00000}
    提示：
        树中节点数量在 [1, 104] 范围内
        -231 <= Node.val <= 231 - 1
*/
public class NO637_E_AverageOfLevels_x2 {

    @Test
    public void test() {
        assert getArray(3.00000, 14.50000, 11.00000).equals(
                averageOfLevels(cTree(3, 9, 20, null, null, 15, 7)));
        assert getArray(3.00000, 14.50000, 11.00000).equals(
                averageOfLevels(cTree(3, 9, 20, 15, 7)));
    }

    public List<Double> averageOfLevels(TreeNode root) {
        List<Double> res = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            double sum = 0;
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                sum += node.val;
                if (node.left != null)
                    queue.offer(node.left);

                if (node.right != null)
                    queue.offer(node.right);
            }
            res.add(sum / size);
        }
        return res;
    }

}

















/**
// 方法1：
public List<Double> averageOfLevels(TreeNode root) {
    List<Integer> counts = new ArrayList<>();
    List<Double> sums = new ArrayList<>();
    dfs(root, 0, counts, sums);
    List<Double> averages = new ArrayList<>();
    int size = sums.size();
    for (int i = 0; i < size; i++)
        averages.add(sums.get(i) / counts.get(i));

    return averages;
}

public void dfs(TreeNode root, int level, List<Integer> counts, List<Double> sums) {
    if (root == null)
        return;

    if (level < sums.size()) {
        sums.set(level, sums.get(level) + root.val);
        counts.set(level, counts.get(level) + 1);
    } else {
        sums.add(1.0 * root.val);
        counts.add(1);
    }
    dfs(root.left, level + 1, counts, sums);
    dfs(root.right, level + 1, counts, sums);
}
*/