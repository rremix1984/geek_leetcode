/**
 * copyright
 */
package com.interval;

import com.leetcode.util.ListNode;
import com.leetcode.util.TreeNode;
import org.junit.Test;
import java.util.*;
import static com.leetcode.util.MathUtils.cTree;
import static com.leetcode.util.SystemUtil.printListNodes;

/**
    [LISTNODE] ||
    [TREE]
    Interval.04.03 特定深度节点链表
    给定一棵二叉树，设计一个算法，创建含有某一深度上所有节点的链表
    （比如，若一棵树的深度为 D，则会创建出 D 个链表）。
    返回一个包含所有深度的链表的数组。
    示例：
        输入：[1, 2, 3, 4, 5, null, 7, 8]
                1
              /  \
             2    3
            / \    \
           4   5    7
          /
         8
        输出：[[1],[2,3],[4,5,7],[8]]
    Related Topics:树,广度优先搜索,链表,二叉树
*/
public class Interval_04_03_N_ListOfDepth {

    @Test
    public void test() {
        printListNodes(
                listOfDepth(cTree(1, 2, 3, 4, 5, null, 7, 8)));
    }

    public ListNode[] listOfDepth(TreeNode<Integer> root) {
        // 2024/3/28 NO.1 没做出来，层序遍历经典题
        // 2024/3/31 NO.2 没做出来，思路对了。还要多练习啊
        if (root == null)
            return new ListNode[]{};

        List<ListNode> res = new ArrayList<>();
        // TODO
        
        return res.toArray(new ListNode[0]);
    }

}

















/*
// 方法1：
public ListNode[] listOfDepth(TreeNode tree) {
    if (tree == null)
        return new ListNode[]{};

    List<ListNode> res = new ArrayList<>();

    Deque<TreeNode> queue = new LinkedList<>();
    queue.offer(tree);

    while (!queue.isEmpty()) {
        int size = queue.size();

        // 这里和普通的层序遍历不同
        ListNode dummy = new ListNode(0);
        ListNode head = dummy;

        while (size > 0) {
            TreeNode node = queue.poll();
            head.next = new ListNode(node.val);

            if (node.left != null)
                queue.offer(node.left);

            if (node.right != null)
                queue.offer(node.right);

            head = head.next;
            size--;
        }
        res.add(dummy.next);
    }
    return res.toArray(new ListNode[0]);
}
*/