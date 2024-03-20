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
    [TREE] |
    (中等，面试题)
    二叉树底部视图

     输入：                    1
                    2                    3
              4          5         6         7
           8     9    10    11  12   13    14    15
         16 17 18 19 20 21 2
     输出：
        [16, 8, 20, 12, 2, 14, 7, 15]

    实现二叉树的底部视图与顶部视图类似，但有一个关键的不同：对于每个水平距离，
    我们需要记录在该水平距离上最后遇到的节点，而不是第一个。这意味着在遍历过程中，
    如果我们遇到了相同水平距离的另一个节点，我们将在Map中更新该水平距离对应的节点。

    下面是使用Java实现二叉树底部视图的步骤：
    创建二叉树节点类TreeNode。
    使用队列进行层序遍历（BFS），同时使用一个Map来记录每个水平距离上最后遇到的节点。
    对于每个遍历到的节点，更新其水平距离在Map中对应的节点。
    最后，根据水平距离的顺序输出Map中的节点，这就是二叉树的底部视图。
*/
public class BottomView {

    @Test
    public void test() {
        assertArrayEquals(new int[]{16, 8, 20, 12, 2, 14, 7, 15},
                bottomView(cTree(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11,
                        12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 2)));
        assertArrayEquals(new int[]{2, 1, 3},
                bottomView(cTree(1, 2, 3)));
        assertArrayEquals(new int[]{8, 4, 12, 6, 14, 7, 15},
                bottomView(cTree(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11,
                        12, 13, 14, 15)));
    }

    public int[] bottomView(TreeNode root) {
        // 2024/3/19 NO.1
        // 2024/3/21 NO.2
        if (root == null)
            return null;

        Map<Integer, Integer> map = new TreeMap<>();
        Queue<TreeNode> queue = new LinkedList<>();
        Queue<Integer> dis = new LinkedList<>();
        queue.offer(root);
        dis.add(0);
        while (!queue.isEmpty()) {
            TreeNode cur = queue.remove();
            Integer hd = dis.remove();

            map.put(hd, cur.val);

            if (cur.left != null) {
                queue.offer(cur.left);
                dis.add(hd - 1);
            }

            if (cur.right != null) {
                queue.offer(cur.right);
                dis.add(hd + 1);
            }
        }

        return map.values().stream().mapToInt(a->a).toArray();
    }

}



















/*
// 方法1：
public int[] bottomView(TreeNode root) {
    if (root == null)
        return null;

    // 使用TreeMap以保证键值按水平距离排序
    Map<Integer, Integer> map = new TreeMap<>();
    Queue<TreeNode> queue = new LinkedList<>();
    Queue<Integer> distance = new LinkedList<>();

    queue.add(root);
    distance.add(0);

    while (!queue.isEmpty()) {
        TreeNode current = queue.remove();
        int hd = distance.remove();

        // 不同于顶部视图，这里我们每次遇到一个节点时都更新map
        // 中对应水平距离的节点
        // 如果是顶视图，就要加上 if (!map.containsKey(hd)) 的判断
        map.put(hd, current.val);

        if (current.left != null) {
            queue.offer(current.left);
            distance.offer(hd - 1);
        }

        if (current.right != null) {
            queue.offer(current.right);
            distance.offer(hd + 1);
        }
    }
    return map.values().stream().mapToInt(a->a).toArray();
}
*/