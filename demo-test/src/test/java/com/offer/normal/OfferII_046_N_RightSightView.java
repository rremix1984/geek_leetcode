/**
 * copyright 2022/1/19
 */
package com.offer.normal;

import com.leetcode.util.TreeNode;
import org.junit.Test;
import java.util.*;
import static com.leetcode.util.MathUtils.cTree;
import static com.leetcode.util.MathUtils.getArray;
import static com.leetcode.util.SystemUtil.arrayAllMatch;
import static org.junit.Assert.assertArrayEquals;

/**
    [TREE] |
    (中等)
    剑指 Offer II 046. 二叉树的右侧视图
        给定一个二叉树的 根节点 root，想象自己站在它的右侧，
        按照从顶部到底部的顺序，返回从右侧所能看到的节点值。
    示例 1:
        输入: [1, 2, 3, null, 5, null, 4]
        输出: [1, 3, 4]
    示例 2:
        输入: [1, null, 3]
        输出: [1, 3]
    示例 3:
        输入: []
        输出: []
*/
public class OfferII_046_N_RightSightView {

    @Test
    public void test () {
        assert arrayAllMatch(getArray(1, 3, 4),
                rightSideView(cTree(1,
                                        2,   3,
                                 null, 5, null, 4)));
        assert arrayAllMatch(getArray(1, 3),
                rightSideView(cTree(1,
                                      null, 3)));
    }

    public List<Integer> rightSideView(TreeNode root) {
        // 2024/3/12 NO.1
        List<Integer> res = new ArrayList<>();
        return res;
    }

}

























/*
// 方法1：
public List<Integer> rightSideView(TreeNode root) {
    List<Integer> res = new ArrayList<>();
    if (root == null)
        return res;

    Deque<TreeNode> queue = new LinkedList<>();
    queue.offer(root);
    while (!queue.isEmpty()) {
        int size = queue.size();
        int val = 0;
        while (size > 0) {
            TreeNode node = queue.poll();
            if (node == null)
                continue;

            if (node.left != null)
                queue.offer(node.left);

            if (node.right != null)
                queue.offer(node.right);

            val = node.val;
            size--;
        }
        res.add(val);
    }
    return res;
}
*/