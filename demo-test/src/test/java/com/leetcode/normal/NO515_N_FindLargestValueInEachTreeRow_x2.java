/**
 * copyright 2022/1/19
 */
package com.leetcode.normal;

import com.leetcode.util.TreeNode;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import static com.leetcode.util.LogUtil.info;
import static com.leetcode.util.MathUtils.createFullTree;
import static com.leetcode.util.MathUtils.getArray;
import static java.lang.Math.max;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
    （中等）
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
public class NO515_N_FindLargestValueInEachTreeRow_x2 {

    @Test
    public void test() {
        assertEquals(getArray(new int[]{1, 3, 9}),
                largestValues(createFullTree(1, 3, 2, 5, 3, null, 9)));
        assertEquals(getArray(new int[]{1, 3}),
                largestValues(createFullTree(1, 2, 3)));// [1, 3]
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
    if (root == null)
        return;

    if (level == res.size())
        res.add(root.val);
    else
        res.set(level, max(res.get(level), root.val));

    dfs(res, root.left, level+1);
    dfs(res, root.right, level+1);
}
*/