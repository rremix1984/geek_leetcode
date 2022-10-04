/**
 * copyright 2022/1/19
 */
package com.offer.easy;

import com.leetcode.util.TreeNode;
import org.junit.Test;
import java.util.*;

import static com.leetcode.util.LogUtil.info;
import static com.leetcode.util.MathUtils.*;

/**
    (简单)
    剑指 Offer 32 - III. 从上到下打印二叉树 III
        请实现一个函数按照之字形顺序打印二叉树，即第一行按照从左到右的顺序打印，
        第二层按照从右到左的顺序打印，第三行再按照从左到右的顺序打印，其他行以此类推。
    例如:
        给定二叉树: {3, 9, 20, null, null, 15, 7},
                3
               / \
              9  20
                /  \
               15   7
        返回其层次遍历结果：
        [[3],
         [20,9],
         [15,7]]
*/
public class Offer_032_III_N_LevelOrder_x2 {

    @Test
    public void test() {
        assert levelOrder(createFullTree(3, 9, 20, null, null, 15, 7)).equals(
                getArray(new int[][]{{3}, {20, 9}, {15, 7}}));
        assert getArray(new int[][]{{1}, {3, 2}, {4, 5}}).equals(
                levelOrder(createFullTree(1, 2, 3, 4, null, null, 5)));
    }

    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> ans = new LinkedList<>();
        return ans;
    }

}






















/**
// 方法1：双链表
public List<List<Integer>> levelOrder(TreeNode root) {
    List<List<Integer>> ans = new LinkedList<>();
    if (root == null)
        return ans;

    Deque<TreeNode> nodeQueue = new LinkedList<>();
    nodeQueue.offer(root);
    // 从左到右
    boolean isOrderLeft = true;

    while (!nodeQueue.isEmpty()) {
        Deque<Integer> levelList = new LinkedList<>();
        int size = nodeQueue.size();
        for (int i = 0; i < size; ++i) {
            TreeNode cur = nodeQueue.poll();
            if (isOrderLeft)
                levelList.offerLast(cur.val);
            else
                levelList.offerFirst(cur.val);

            if (cur.left != null)
                nodeQueue.offer(cur.left);

            if (cur.right != null)
                nodeQueue.offer(cur.right);
        }
        ans.add(new LinkedList<>(levelList));
        isOrderLeft = !isOrderLeft;
    }
    return ans;
}
*/