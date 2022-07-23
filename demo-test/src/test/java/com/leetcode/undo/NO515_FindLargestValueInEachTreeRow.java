/**
 * copyright 2022/1/19
 */
package com.leetcode.undo;

import com.leetcode.util.TreeNode;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static com.leetcode.util.LogUtil.info;

/**
    515. 在每个树行中找最大值
        给定一棵二叉树的根节点 root ，请找出该二叉树中每一层的最大值。
    示例1：
        输入: root = [1, 3, 2, 5, 3, null, 9]
        输出: [1, 3, 9]
    示例2：
        输入: root = [1, 2, 3]
        输出: [1, 3]
*/
@SuppressWarnings("all")
public class NO515_FindLargestValueInEachTreeRow {

    @Test
    public void test() {
        // [1, 3, 9]
        info(largestValues(new TreeNode(1,
                new TreeNode(3,
                        5, 3), new TreeNode(2,
                                            null, 9))));
    }

    public List<Integer> largestValues(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        return res;
    }
}











/*
// 方法1：迭代法
public List<Integer> largestValues(TreeNode root) {
    List<Integer> res = new ArrayList<>();
    if (root == null)
        return res;

    Deque<TreeNode> queue = new LinkedList<>();
    queue.offer(root);
    while (!queue.isEmpty()) {
        int max = Integer.MIN_VALUE;
        int size = queue.size();
        for (int i = 0; i < size; i++) {
            TreeNode node = queue.poll();
            max = Math.max(max, node.val);
            if (node.left != null)
                queue.offer(node.left);
            if (node.right != null)
                queue.offer(node.right);
        }
        res.add(max);
    }
    return res;
}

// 方法2：深度优先
public List<Integer> largestValues(TreeNode root) {
    List<Integer> res = new ArrayList<>();
    if (root == null)
        return res;
    dfs(res, root, 0);
    return res;
}

public void dfs(List<Integer> res, TreeNode root, int level) {
    if (level == res.size())
        res.add(root.val);
    else
        res.set(level, Math.max(res.get(level), root.val));

    if (root.left != null)
        dfs(res, root.left, level + 1);

    if (root.right != null)
        dfs(res, root.right, level + 1);
}
*/