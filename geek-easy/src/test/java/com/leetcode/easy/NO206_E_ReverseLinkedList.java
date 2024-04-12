/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import com.leetcode.util.ListNode;
import org.junit.Test;

/**
    [LINKEDLIST] ||||
    (简单)
    206. 反转链表
        给你单链表的头节点 head ，请你反转链表，并返回反转后的链表。
    示例 1：
        输入：head = [1, 2, 3, 4, 5]
        输出：[5, 4, 3, 2, 1]
    示例 2：
        输入：head = [1, 2]
        输出：[2, 1]
    示例 3：
        输入：head = []
        输出：[]
*/
public class NO206_E_ReverseLinkedList {

    @Test
    public void test() {
        assert new ListNode<>(5, 4, 3, 2, 1).equals(
            reverseList(new ListNode<>(1, 2, 3, 4, 5)));// [5, 4, 3, 2, 1]
        assert new ListNode<>(2, 1).equals(
            reverseList(new ListNode<>(1,2)));// [2, 1]
        assert new ListNode<>().equals(
            reverseList(new ListNode<>()));// []
    }

    public ListNode<Integer> reverseList(ListNode<Integer> head) {
        // 2024/3/5  NO.1 要练思路，两种解法
        // 2024/3/10 NO.2 第一种做出来了，第二种错了
        // 2024/3/11 NO.3
        // 2024/3/17-20 NO.4-5 两种都做出来了
        ListNode<Integer> pre = null;
        return pre;
    }

}















/*
// 方法1：
public ListNode reverseList(ListNode head) {
    if (head == null || head.next == null)
        return head;
    ListNode newHead = reverseList(head.next);
    head.next.next = head;
    head.next = null;
    return newHead;
}

// 方法2：
public ListNode reverseList(ListNode head) {
    ListNode pre = null;
    ListNode cur = head;
    while (cur != null) {
        ListNode next = cur.next;
        cur.next = pre;
        pre = cur;
        cur = next;
    }
    return pre;
}
*/