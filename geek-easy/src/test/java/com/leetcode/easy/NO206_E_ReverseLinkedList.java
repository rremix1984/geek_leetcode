/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import com.leetcode.util.ListNode;
import org.junit.Test;

import static com.leetcode.util.ListNode.assertNodeEquals;

/**
    [LINKEDLIST] |||||||
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
        assertNodeEquals(new ListNode<>(5, 4, 3, 2, 1),
            reverseList(new ListNode<>(1, 2, 3, 4, 5)));
        assertNodeEquals(new ListNode<>(2, 1),
            reverseList(new ListNode<>(1,2)));
        assertNodeEquals(new ListNode<Integer>(),
            reverseList(new ListNode<>()));
        assertNodeEquals(null, reverseList(null));
    }

    public ListNode<Integer> reverseList(ListNode<Integer> head) {
        // 2024/3/5  NO.1 要练思路，两种解法
        // 2024/3/10 NO.2 第一种做出来了，第二种错了
        // 2024/3/11 NO.3
        // 2024/3/17-20 NO.4-5 两种都做出来了
        // 2024/4/16 NO.6 迭代法能做出来了，递归法做错了，需要反复练习
        // 2024/5/30 NO.7 递归做出来了

        return null;
    }

}















/*
// 方法1：
public ListNode<Integer reverseList(ListNode<Integer head) {
    if (head == null || head.next == null)
        return head;

    ListNode<Integer> newHead = reverseList(head.next);
    head.next.next = head;
    head.next = null;
    return newHead;
}

// 方法2：
public ListNode<Integer> reverseList(ListNode<Integer> head) {
    ListNode<Integer> pre = null;
    ListNode<Integer> cur = head;
    while (cur != null) {
        ListNode next = cur.next;
        cur.next = pre;
        pre = cur;
        cur = next;
    }
    return pre;
}

// 方法3：
public ListNode<Integer> reverseList(ListNode<Integer> head) {
    ListNode<Integer> pre = null;
    while (head != null) {
        ListNode next = head.next;
        head.next = pre;
        pre = head;
        head = next;
    }
    return pre;
}
*/