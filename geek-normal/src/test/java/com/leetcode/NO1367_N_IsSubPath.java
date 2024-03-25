package com.leetcode;

import com.leetcode.util.ListNode;
import com.leetcode.util.TreeNode;
import org.junit.Test;
import static com.leetcode.util.MathUtils.cTree;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
    [LISTNODE]
    (中等)
    NO.1367 二叉树中的链表
    给你一棵以 root 为根的二叉树和一个 head 为第一个节点的链表。
    如果在二叉树中，存在一条一直向下的路径，且每个点的数值恰好一一对应以 head 为
    首的链表中每个节点的值，那么请你返回 True ，否则返回 False 。
    一直向下的路径的意思是：从树中某个节点开始，一直连续向下的路径。
    示例 1：
        输入：head = [4,2,8], root = [1,4,4,null,2,2,null,1,null,6,8,null,null,null,null,1,3]
        输出：true
        解释：树中蓝色的节点构成了与链表对应的子路径。
                      【1】,
                    /       \
                  /           \
                /               \
              4,                 【4】,
           /    \               /      \
          /      \             /        \
      null,       2,       【2】,       null,
                /  \        /  \
              /     \     /      \
            1,     null,【6】,     8,
          /   \         /  \     /  \
        null, null,  null, null, 1,   3

    示例 2：
        输入：head = [1,4,2,6], root = [1,4,4,null,2,2,null,1,null,6,8,null,null,null,null,1,3]
        输出：true
    示例 3：
        输入：head = [1,4,2,6,8], root = [1,4,4,null,2,2,null,1,null,6,8,null,null,null,null,1,3]
        输出：false
        解释：二叉树中不存在一一对应链表的路径。
    提示：
        二叉树和链表中的每个节点的值都满足 1 <= node.val <= 100 。
        链表包含的节点数目在 1 到 100 之间。
        二叉树包含的节点数目在 1 到 2500 之间。
    Related Topics:树,深度优先搜索,广度优先搜索,链表,二叉树
*/
public class NO1367_N_IsSubPath {

    @Test
    public void test() {
        assertTrue(isSubPath(new ListNode(4, 2, 8),
                cTree(1, 4, 4, null, 2, 2, null, 1,
                        null, 6, 8, null, null, null, null, 1, 3)));
        assertTrue(isSubPath(new ListNode(1,4,2,6),
                cTree(1, 4, 4, null, 2, 2, null, 1,
                        null, 6, 8, null, null, null, null, 1, 3)));
        assertFalse(isSubPath(new ListNode(1, 4, 2, 6, 8),
                cTree(1, 4, 4, null, 2, 2, null, 1,
                        null, 6, 8, null, null, null, null, 1, 3)));
    }

    public boolean isSubPath(ListNode head, TreeNode root) {
        return false;
    }

}















/*
// 方法1：
public boolean isSubPath(ListNode head, TreeNode root) {
    if (head == null)
        return true;

    if (root == null)
        return false;

    //先判断当前的节点，如果不对，再看左子树和右子树呗
    return isSub(head, root)
            || isSubPath(head, root.left)
            || isSubPath(head, root.right);
}

private boolean isSub(ListNode head, TreeNode node) {
    //特判：链表走完了，返回true
    if (head == null) {
        return true;
    }
    //特判：链表没走完，树走完了，这肯定不行，返回false
    if (node == null) {
        return false;
    }
    //如果值不同，必定不是啊
    if (head.val != node.val) {
        return false;
    }
    //如果值相同，继续看，左边和右边有一个满足即可
    return isSub(head.next, node.left) || isSub(head.next, node.right);
}
*/