package com;

import com.leetcode.util.TreeNode;
import org.junit.Test;
import java.util.*;
import static com.leetcode.util.MathUtils.cTree;
import static org.junit.Assert.assertArrayEquals;

/**
    [TREE]
    二叉树顶部视图
*/
public class TopView {

    @Test
    public void test() {
        assertArrayEquals(new int[]{16, 8, 4, 2, 1, 3, 7, 15},
                topView(cTree(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11,
                        12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 2)));
        assertArrayEquals(new int[]{2, 1, 3},
                topView(cTree(1, 2, 3)));
    }

    public int[] topView(TreeNode root) {
        if (root == null)
            return null;

        // 使用TreeMap以保证键值按水平距离排序
        Map<Integer, Integer> map = new TreeMap<>();
        Queue<TreeNode> queue = new LinkedList<>();
        Queue<Integer> horizontalDistance = new LinkedList<>();

        queue.add(root);
        horizontalDistance.add(0);

        while (!queue.isEmpty()) {
            TreeNode current = queue.remove();
            int hd = horizontalDistance.remove();

            // 如果这个水平距离还没有被添加到map中，则添加
            if (!map.containsKey(hd)) {
                map.put(hd, current.val);
            }

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

















/*
// 方法1：
public int[] topView(TreeNode root) {
    if (root == null)
        return null;

    // 使用TreeMap以保证键值按水平距离排序
    Map<Integer, Integer> map = new TreeMap<>();
    Queue<TreeNode> queue = new LinkedList<>();
    Queue<Integer> horizontalDistance = new LinkedList<>();

    queue.add(root);
    horizontalDistance.add(0);

    while (!queue.isEmpty()) {
        TreeNode current = queue.remove();
        int hd = horizontalDistance.remove();

        // 如果这个水平距离还没有被添加到map中，则添加
        if (!map.containsKey(hd)) {
            map.put(hd, current.val);
        }

        if (current.left != null) {
            queue.add(current.left);
            horizontalDistance.add(hd - 1);
        }

        if (current.right != null) {
            queue.add(current.right);
            horizontalDistance.add(hd + 1);
        }
    }
    return map.values().stream().mapToInt(a -> Integer.parseInt(String.valueOf(a))).toArray();
}
*/