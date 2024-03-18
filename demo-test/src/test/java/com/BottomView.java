package com;

import com.leetcode.util.TreeNode;
import org.junit.Test;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;

import static com.leetcode.util.MathUtils.cTree;
import static com.leetcode.util.SystemUtil.*;
import static org.junit.Assert.assertArrayEquals;

/**
     [TREE]
     二叉树底部视图
 */
public class BottomView {

    @Test
    public void test() {
        assertArrayEquals(new int[]{16, 8, 20, 12, 2, 14, 7, 15},
                bottomView(cTree(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11,
                        12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 2)));
        assertArrayEquals(new int[]{2, 1, 3},
                bottomView(cTree(1, 2, 3)));
    }

    public int[] bottomView(TreeNode root) {
        if (root == null)
            return null;

        Map<Integer, Integer> map = new TreeMap<>(); // 使用TreeMap以保证键值按水平距离排序
        Queue<TreeNode> queue = new LinkedList<>();
        Queue<Integer> horizontalDistance = new LinkedList<>();

        queue.add(root);
        horizontalDistance.add(0);

        while (!queue.isEmpty()) {
            TreeNode current = queue.remove();
            int hd = horizontalDistance.remove();

            // 不同于顶部视图，这里我们每次遇到一个节点时都更新map中对应水平距离的节点
            map.put(hd, current.val);

            if (current.left != null) {
                queue.add(current.left);
                horizontalDistance.add(hd - 1);
            }

            if (current.right != null) {
                queue.add(current.right);
                horizontalDistance.add(hd + 1);
            }
        }

        return map.values().stream().mapToInt(a->a).toArray();
    }

}
