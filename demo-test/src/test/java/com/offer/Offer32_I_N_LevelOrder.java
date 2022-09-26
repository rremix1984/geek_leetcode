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
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
    (中等)
    剑指 Offer 32 - I. 从上到下打印二叉树
        从上到下打印出二叉树的每个节点，同一层的节点按照从左到右的顺序打印。
    例如:
        给定二叉树: [3,9,20,null,null,15,7],
            3
           / \
          9  20
        /  \
       15   7
    返回：{3, 9, 20, 15, 7}
*/
public class Offer32_I_N_LevelOrder {

    @Test
    public void test() {
        assertArrayEquals(new int[]{3, 9, 20, 15, 7},
            levelOrder(createFullTree(3, 9, 20, 15, 7)));
        assertArrayEquals(new int[]{1, 2, 3, 4, 5},
                levelOrder(createFullTree(1, 2, 3, 4, null, null, 5)));
        assertArrayEquals(new int[]{3, 9, 20, 15, 7},
                levelOrder(createFullTree(3, 9, 20, null, null, 15, 7)));
    }

    public int[] levelOrder(TreeNode root) {
        return new int[0];
    }

}

















/**
// 方法1：通过双向队列实现层序遍历
public int[] levelOrder(TreeNode root) {
    List<Integer> res = new ArrayList<>();
    if (root == null)
        return new int[0];

    Deque<TreeNode> queue = new LinkedList<>();
    queue.offer(root);
    while (!queue.isEmpty()) {
        int size = queue.size();
        while (size > 0) {
            TreeNode node = queue.pollFirst();
            res.add(node.val);
            if (node.left != null)
                queue.offer(node.left);
            if (node.right != null)
                queue.offer(node.right);
            size--;
        }
    }
    int[] rest = new int[res.size()];
    for (int i = 0; i < rest.length; i++) {
        rest[i] = res.get(i);
    }
    return rest;
}
*/