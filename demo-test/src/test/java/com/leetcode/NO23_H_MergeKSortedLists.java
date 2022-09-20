/**
 * copyright 2022/1/19
 */
package com.leetcode;

import com.leetcode.util.ListNode;
import org.junit.Test;

/**
    (困难)
    23. 合并K个升序链表
        给你一个链表数组，每个链表都已经按升序排列。
        请你将所有链表合并到一个升序链表中，返回合并后的链表。
    示例 1：
        输入：lists = [[1,4,5], [1,3,4], [2,6]]
        输出：[1, 1, 2, 3, 4, 4, 5, 6]
        解释：链表数组如下：
            [
                1->4->5,
                1->3->4,
                2->6
            ]
        将它们合并到一个有序链表中得到。
        1->1->2->3->4->4->5->6
    示例 2：
        输入：lists = []
        输出：[]
    示例 3：
        输入：lists = [[]]
        输出：[]
*/
public class NO23_H_MergeKSortedLists {

    @Test
    public void test() {
        assert new ListNode(1, 1, 2, 3, 4, 4, 5, 6).equals(
            mergeKLists(new ListNode[]{
                    new ListNode(1, 4, 5),
                    new ListNode(1, 3, 4),
                    new ListNode(2, 6)
                }));
        assert new ListNode().equals(
                mergeKLists(new ListNode[]{
                }));
        assert new ListNode().equals(
                mergeKLists(new ListNode[]{new ListNode()
                }));
        assert new ListNode(0, 1, 2, 3, 4, 4, 5, 6).equals(
                mergeKLists(new ListNode[]{
                        new ListNode(1, 4, 5, 6),
                        new ListNode(2, 3, 4),
                        new ListNode()
                }));
    }

    // 方法1：分治法
    public ListNode mergeKLists(ListNode[] lists) {
        return merge(lists, 0, lists.length - 1);
    }

    public ListNode merge(ListNode[] lists, int l, int r) {
        if (l == r)
            return lists[l];

        if (l > r)
            return null;

        int mid = (l + r) >> 1;
        return mergeTwoLists(merge(lists, l, mid), merge(lists, mid + 1, r));
    }

    public ListNode mergeTwoLists(ListNode a, ListNode b) {
        // 任何一个为空，就返回另一边
        if (a == null || b == null)
            return a != null ? a : b;

        // 虚拟头节点
        ListNode dummy = new ListNode(0);
        ListNode pre = dummy;
        ListNode p1 = a;
        ListNode p2 = b;

        while (p1 != null && p2 != null) {
            if (p1.val < p2.val) {
                pre.next = p1;
                p1 = p1.next;
            } else {
                pre.next = p2;
                p2 = p2.next;
            }
            pre = pre.next;
        }
        pre.next = (p1 != null ? p1 : p2);
        return dummy.next;
    }
}

















/**
// 方法1：分治法
public ListNode mergeKLists(ListNode[] lists) {
    return merge(lists, 0, lists.length - 1);
}

public ListNode merge(ListNode[] lists, int l, int r) {
    if (l == r)
        return lists[l];

    if (l > r)
        return null;

    int mid = (l + r) >> 1;
    return mergeTwoLists(merge(lists, l, mid), merge(lists, mid + 1, r));
}

public ListNode mergeTwoLists(ListNode a, ListNode b) {
    // 任何一个为空，就返回另一边
    if (a == null || b == null)
        return a != null ? a : b;

    // 虚拟头节点
    ListNode dummy = new ListNode(0);
    ListNode pre = dummy;
    ListNode p1 = a;
    ListNode p2 = b;

    while (p1 != null && p2 != null) {
        if (p1.val < p2.val) {
            pre.next = p1;
            p1 = p1.next;
        } else {
            pre.next = p2;
            p2 = p2.next;
        }
        pre = pre.next;
    }
    pre.next = (p1 != null ? p1 : p2);
    return dummy.next;
}
*/