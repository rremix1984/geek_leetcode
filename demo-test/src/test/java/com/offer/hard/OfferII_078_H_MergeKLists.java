/**
 * copyright 2022/1/19
 */
package com.offer.hard;

import com.leetcode.util.ListNode;
import org.junit.Test;

import static com.leetcode.util.LogUtil.info;
import static com.leetcode.util.MathUtils.getListNodes;
import static com.leetcode.util.MathUtils.mergeTwoLists;

/**
    (困难)
    剑指 Offer II 078. 合并排序链表
        给定一个链表数组，每个链表都已经按升序排列。
        请将所有链表合并到一个升序链表中，返回合并后的链表。
    示例 1：
        输入：lists = [[1, 4, 5], [1, 3, 4], [2, 6]]
        输出：[1, 1, 2, 3, 4, 4, 5, 6]
        解释：链表数组如下：[1->4->5,
                          1->3->4,
                          2->6]
        将它们合并到一个有序链表中得到。
        1 -> 1 -> 2 -> 3 -> 4 -> 4 -> 5 -> 6
    示例 2：
        输入：lists = []
        输出：[]
    示例 3：
        输入：lists = [[]]
        输出：[]
*/
public class OfferII_078_H_MergeKLists {

    @Test
    public void test() {
        assert new ListNode(1, 1, 2, 3, 4, 4, 5, 6).equals(
                mergeKLists(getListNodes(new int[][]{{1, 4, 5}, {1, 3, 4}, {2, 6}})));
        assert new ListNode().equals(
                mergeKLists(getListNodes(new int[][]{})));
        assert new ListNode().equals(
                mergeKLists(getListNodes(new int[][]{{}})));
    }

    public ListNode mergeKLists(ListNode[] lists) {
        ListNode dummy = new ListNode(-1);
        return dummy.next;
    }

}



















/**
// 方法1：
public ListNode mergeKLists(ListNode[] lists) {
    ListNode ans = null;

    for (ListNode list : lists)
        ans = mergeTwoLists(ans, list);

    return ans;
}

public ListNode mergeTwoLists(ListNode a, ListNode b) {
    if (a == null || b == null)
        return a != null ? a : b;

    ListNode head = new ListNode(-1);
    ListNode tail = head;
    ListNode p1 = a;
    ListNode p2 = b;

    while (p1 != null && p2 != null) {
        if (p1.val < p2.val) {
            tail.next = p1;
            p1 = p1.next;
        } else {
            tail.next = p2;
            p2 = p2.next;
        }
        tail = tail.next;
    }
    tail.next = p1 != null ? p1 : p2;
    return head.next;
}
*/