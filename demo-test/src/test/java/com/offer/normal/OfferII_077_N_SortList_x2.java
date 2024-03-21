/**
 * copyright 2022/1/19
 */
package com.offer.normal;

import com.leetcode.util.ListNode;
import org.junit.Test;

/**
    [LINKEDLIST]
    (中等)
    剑指 Offer II 077. 链表排序
        给定链表的头结点 head ，请将其按 升序 排列并返回 排序后的链表 。
    示例 1：
        输入：head = [4, 2, 1, 3]
        输出：[1, 2, 3, 4]
    示例 2：
        输入：head = [-1, 5, 3, 4, 0]
        输出：[-1, 0, 3, 4, 5]
    示例 3：
        输入：head = []
        输出：[]
*/
public class OfferII_077_N_SortList_x2 {

    @Test
    public void test() {
        assert new ListNode(1, 2, 3, 4).equals(sortList(new ListNode(4, 2, 1, 3)));
        assert new ListNode(-1, 0, 3, 4, 5).equals(sortList(new ListNode(-1, 5, 3, 4, 0)));
        assert new ListNode().equals(sortList(new ListNode()));
    }

    public ListNode sortList(ListNode head) {
        return null;
    }

}

















/**
// 方法1：
public ListNode sortList(ListNode head) {
    if (head == null || head.next == null)
        return head;

    ListNode fast = head.next;
    ListNode slow = head;
    while (fast != null && fast.next != null) {
        fast = fast.next.next;
        slow = slow.next;
    }

    ListNode head2 = slow.next;
    slow.next = null;

    return mergeTwoLists(sortList(head), sortList(head2));
}
*/