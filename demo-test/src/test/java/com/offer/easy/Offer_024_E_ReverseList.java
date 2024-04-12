/**
 * copyright 2022/1/19
 */
package com.offer.easy;

import com.leetcode.util.ListNode;
import org.junit.Test;

/**
    [LISTNODE] |||
    (简单)
    (做了很多次，感觉已经拿捏了)
    剑指 Offer 24. 反转链表
        定义一个函数，输入一个链表的头节点，反转该链表并输出反转后链表的头节点。
    示例:
        输入: 1 -> 2 -> 3 -> 4 -> 5 -> NULL
        输出: 5 -> 4 -> 3 -> 2 -> 1 -> NULL
*/
public class Offer_024_E_ReverseList {

    @Test
    public void test() {
        assert new ListNode<>(5, 4, 3, 2, 1).equals(
                reverseList(new ListNode<>(1, 2, 3, 4, 5)));
        assert new ListNode<>(3, 4, 5, 1, 2).equals(
                reverseList(new ListNode<>(2, 1, 5, 4, 3)));
    }

    public ListNode<Integer> reverseList(ListNode<Integer> head) {
        // 2024/3/16 NO.1
        // 2024/3/23 NO.2 一遍过
        // 2024/3/25 NO.3 一遍过

        return null;
    }

}

















/*
// 方法1：递归
public ListNode reverseList(ListNode head) {
    if (head == null || head.next == null)
        return head;

    ListNode newNode = reverseList(head.next);
    head.next.next = head;
    head.next = null;
    return newNode;
}

// 方法2：迭代法
public ListNode reverseList(ListNode head) {
    if (head == null)
        return head;

    ListNode pre = null;
    while (head != null) {
        ListNode next = head.next;
        head.next = pre;
        pre = head;
        head = next;
    }
    return pre;
}
*/