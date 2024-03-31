package com.interval;

import com.leetcode.util.ListNode;
import org.junit.Test;

/**
    [LISTNODE] ||
    (简单)
    Interval.02.02 返回倒数第 K 个节点
    实现一种算法，找出单向链表中倒数第 k 个节点。返回该节点的值。
    注意：本题相对原题稍作改动
    示例：
        输入： 1 -> 2 -> 3 -> 4 -> 5 和 k = 2
        输出： 4
        说明：
        给定的 k 保证是有效的。
    Related Topics:链表,双指针
*/
public class Interval_02_02_E_KthToLast {

    @Test
    public void test() {
        assert 4 == kthToLast(
            new ListNode(1, 2, 3, 4, 5), 2);
    }

    public int kthToLast(ListNode head, int k) {
        // 2024/3/29 NO.1 双指针做出来了
        // 2024/3/31 NO.2 一遍过，双指针
        ListNode fast = head;

        return fast.val;
    }

}















/*
// 方法1：
public int kthToLast(ListNode head, int k) {
    ListNode q = head;
    ListNode l = head;

    for (int i = 0; i < k; i++)
        q = q.next;

    while (q != null) {
        q = q.next;
        l = l.next;
    }
    return l.val;
}
*/