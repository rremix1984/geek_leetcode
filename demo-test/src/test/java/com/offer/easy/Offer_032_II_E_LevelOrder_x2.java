/**
 * copyright 2022/1/19
 */
package com.offer.easy;

import com.leetcode.util.TreeNode;
import org.junit.Test;
import java.util.*;
import static com.leetcode.util.MathUtils.*;
import static org.junit.Assert.assertEquals;

/**
    (简单)
    剑指 Offer 32 - II. 从上到下打印二叉树 II
        从上到下按层打印二叉树，同一层的节点按从左到右的顺序打印，每一层打印到一行。
    例如:
        给定二叉树: [3,9,20,null,null,15,7],

                 3
                / \
               9  20
                 /  \
                15   7
        返回其层次遍历结果：
        [[3],
         [9,20],
         [15,7]]
*/
public class Offer_032_II_E_LevelOrder_x2 {

    @Test
    public void test() {
        assertEquals(getArray(new int[][]{{1}, {2, 3}, {4, 5}}),
                levelOrder(cTree(1, 2, 3, 4, null, null, 5)));
        assertEquals(getArray(new int[][]{{3}, {9, 20}, {15, 7}}),
                levelOrder(cTree(3, 9, 20, null, null, 15, 7)));
        assertEquals(getArray(new int[][]{{1}}),
                levelOrder(cTree(1)));// [1]
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