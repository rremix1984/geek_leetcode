package com;

import com.leetcode.util.TreeNode;
import org.junit.Test;
import java.util.*;
import static com.leetcode.util.MathUtils.cTree;
import static com.leetcode.util.SystemUtil.print;
import static com.leetcode.util.SystemUtil.printArr;
import static org.junit.Assert.assertArrayEquals;

/**
    [TREE] ||||||||
    (中等,抖音,面试题)
    二叉树顶部视图

    输入：                    1
                          /     \
                         /        \
                        /           \
                      /               \
                   2                    3

              4          5         6          7

           8     9    10    11  12   13    14    15

        16 17 18 19 20 21 2
    输出：
         [16, 8, 4, 2, 1, 3, 7, 15]
     ChatGPT
    要实现二叉树的顶部视图，我们需要首先理解顶部视图的定义。
    二叉树的顶部视图是从树顶部看下去时能看到的节点的集合。为了实现这一点，
    我们可以使用水平距离的概念，其中根节点的水平距离为0，左子节点的水平距离
    为父节点的水平距离减1，右子节点的水平距离为父节点的水平距离加1。我们遍历树，
    记录每个水平距离上首次遇到的节点，这样就可以生成二叉树的顶部视图。
    创建二叉树节点类TreeNode。
    使用队列进行层序遍历（BFS），同时使用一个Map来记录每个水平距离上的节点。
    对于每个遍历到的节点，如果其水平距离在Map中还没有记录，就将其添加到Map中。
    最后，根据水平距离的顺序输出Map中的节点，这就是二叉树的顶部视图。
*/
@SuppressWarnings("all")
public class TopView {

    @Test
    public void test() {
        assertArrayEquals(new int[]{16, 8, 4, 2, 1, 3, 7, 15},
                topView(cTree(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11,
                        12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 2)));
        assertArrayEquals(new int[]{8, 4, 2, 1, 3, 7, 15},
                topView(cTree(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11,
                        12, 13, 14, 15)));
    }

    public int[] topView(TreeNode root) {
        // 2024/3/19 NO.1 看懂了
        // 2024/3/20 NO.2 没做出来
        // 2024/3/21 NO.3 没做对
        // 2024/3/22 NO.4 能做出来了
        // 2024/3/24-26-27-29 No.5-6-7-8 一遍过
        return null;
    }

}

















/*
// 方法1：
public int[] topView(TreeNode root) {
    if (root == null)
        return null;

    // 使用TreeMap以保证键值按水平距离排序
    // 一定要用treeMap
    Map<Integer, Integer> map = new TreeMap<>();
    Queue<TreeNode> queue = new LinkedList<>();
    Queue<Integer> distance = new LinkedList<>();

    queue.add(root);
    distance.add(0);

    while (!queue.isEmpty()) {
        TreeNode current = queue.remove();
        int hd = distance.remove();

        // 如果这个水平距离还没有被添加到map中，则添加
        if (!map.containsKey(hd))
            map.put(hd, current.val);

        if (current.left != null) {
            queue.add(current.left);
            distance.add(hd - 1);
        }

        if (current.right != null) {
            queue.add(current.right);
            distance.add(hd + 1);
        }
    }
    return map.values().stream().mapToInt(a -> a).toArray();
}
*/