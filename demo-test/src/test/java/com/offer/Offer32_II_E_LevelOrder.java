/**
 * copyright 2022/1/19
 */
package com.offer;

import com.leetcode.util.TreeNode;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import static com.leetcode.util.MathUtils.createFullTree;
import static com.leetcode.util.MathUtils.getArray;
import static org.junit.Assert.assertEquals;

public class Offer32_II_E_LevelOrder {

    @Test
    public void test() {
        assertEquals(getArray(new int[][]{{1}, {2, 3}, {4, 5}}),
                levelOrder(createFullTree(1, 2, 3, 4, null, null, 5)));
        assertEquals(getArray(new int[][]{{3}, {9, 20}, {15, 7}}),
                levelOrder(createFullTree(3, 9, 20, null, null, 15, 7)));
        assertEquals(getArray(new int[][]{{1}}),
                levelOrder(createFullTree(1)));// [1]
    }

    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        return res;
    }

}




















/**
// 方法1：层序遍历
public List<List<Integer>> levelOrder(TreeNode root) {
    List<List<Integer>> res = new ArrayList<>();
    if (root == null)
        return res;

    Deque<TreeNode> queue = new LinkedList<>();
    queue.offer(root);
    while (!queue.isEmpty()) {
        List<Integer> level = new ArrayList<>();
        int size = queue.size();
        while (size > 0) {
            TreeNode node = queue.pollFirst();
            level.add(node.val);
            if (node.left != null)
                queue.offer(node.left);
            if (node.right != null)
                queue.offer(node.right);
            size--;
        }
        res.add(new ArrayList(level));
    }
    return res;
}
*/