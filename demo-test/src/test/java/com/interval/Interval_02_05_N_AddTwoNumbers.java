package com.interval;

import com.leetcode.util.ListNode;
import org.junit.Test;
/**
    [LISTNODE] |||
    (中等)
    Interval.02.05 链表求和
    给定两个用链表表示的整数，每个节点包含一个数位。
    这些数位是反向存放的，也就是个位排在链表首部。
    编写函数对这两个整数求和，并用链表形式返回结果。
    示例：
        输入：(7 -> 1 -> 6) + (5 -> 9 -> 2)，即617 + 295
        输出：2 -> 1 -> 9，即912
        进阶：思考一下，假设这些数位是正向存放的，又该如何解决呢?
    示例：
        输入：(6 -> 1 -> 7) + (2 -> 9 -> 5)，即617 + 295
        输出：9 -> 1 -> 2，即912
    Related Topics:递归,链表,数学
*/
public class Interval_02_05_N_AddTwoNumbers {

    @Test
    public void test() {
        assert new ListNode<>(7, 0, 8, 7).equals(
                addTwoNumbers(new ListNode<>(3, 4, 2, 7),
                        new ListNode<>(4, 6, 5)));
        assert new ListNode<>(7, 0, 8).equals(
                addTwoNumbers(new ListNode<>(3, 4, 2),
                        new ListNode<>(4, 6, 5)));
        assert new ListNode<>(0).equals(
                addTwoNumbers(new ListNode<>(0),
                        new ListNode<>(0)));
        assert new ListNode<>(2, 1, 9).equals(
                addTwoNumbers(new ListNode<>(7, 1, 6),
                        new ListNode<>(5, 9, 2)));
    }

    public ListNode<Integer> addTwoNumbers(ListNode<Integer> l1, ListNode<Integer> l2) {
        int carry = 0;
        ListNode<Integer> head = null;
        ListNode<Integer> tail = null;
        
        while (l1 != null || l2 != null || carry != 0) {
            int v1 = l1 != null ? l1.val : 0;
            int v2 = l2 != null ? l2.val : 0;
            int sum = v1 + v2 + carry;
            carry = sum / 10;
            
            if (head == null) {
                head = new ListNode<>(sum % 10);
                tail = head;
            } else {
                tail.next = new ListNode<>(sum % 10);
                tail = tail.next;
            }
            
            if (l1 != null) l1 = l1.next;
            if (l2 != null) l2 = l2.next;
        }

        return head;
    }

}


















/*
// 方法1：
public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
    int carry = 0;
    ListNode head = null;
    ListNode tail = null;
    while (l1 != null || l2 != null || carry != 0) {
        int v1 = l1 != null ? l1.val : 0;
        int v2 = l2 != null ? l2.val : 0;
        int value = v1 + v2 + carry;
        carry = (v1 + v2 + carry) / 10;
        if (head == null) {
            head = new ListNode(value % 10);
            tail = head;
        } else {
            tail.next = new ListNode(value % 10);
            tail = tail.next;
        }

        if (l1 != null)
            l1 = l1.next;

        if (l2 != null)
            l2 = l2.next;
    }
    return head;
}
*/