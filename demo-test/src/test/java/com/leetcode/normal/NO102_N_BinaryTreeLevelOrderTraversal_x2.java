/**
 * copyright 2022/1/19
 */
package com.leetcode.normal;

import com.leetcode.util.TreeNode;
import org.junit.Test;
import java.util.*;
import static com.leetcode.util.LogUtil.info;

/**
    (中等)
    102. 二叉树的层序遍历
        给你二叉树的根节点 root ，返回其节点值的 层序遍历 。
        （即逐层地，从左到右访问所有节点）。
    示例 1：
        输入：root = {3, 9, 20, null, null, 15, 7}
        输出：[[3], [9, 20], [15, 7]]
    示例 2：
        输入：root = {1}
        输出：[[1]]
    示例 3：
        输入：root = {}
        输出：[]
*/
public class NO102_N_BinaryTreeLevelOrderTraversal_x2 {

    @Test
    public void test() {
        info(levelOrder(new TreeNode(1,
                new TreeNode(2,
                    4), new TreeNode(3,
                                    null,5))));// [[1], [2, 3], [4, 5]]
        info(levelOrder(new TreeNode(3,
                        9, new TreeNode(20,
                                        15, 7))));// [[3], [9, 20], [15, 7]]
        info(levelOrder(new TreeNode(1)));// [1]
        info(levelOrder(new TreeNode()));// []
    }

    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> ans = new ArrayList();

        return ans;
    }
}












/*
// 方法1 层序遍历（BFS）
public List<List<Integer>> levelOrder(TreeNode root) {
    List<List<Integer>> ret = new ArrayList<>();
    if (root == null)
        return ret;

    Deque<TreeNode> queue = new LinkedList<>();
    queue.offer(root);

    while (!queue.isEmpty()) {
        List<Integer> level = new ArrayList<>();
        int size = queue.size();
        for (int i = 1; i <= size; i++) {
            TreeNode node = queue.poll();
            level.add(node.val);
            if (node.left != null)
                queue.offer(node.left);

            if (node.right != null)
                queue.offer(node.right);
        }
        ret.add(level);
    }
    return ret;
}
*/
