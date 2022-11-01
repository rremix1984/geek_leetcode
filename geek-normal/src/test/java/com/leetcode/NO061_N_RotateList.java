/**
 * copyright 2022/1/19
 */
package com.leetcode;

import com.leetcode.util.ListNode;
import org.junit.Test;

/**
    (中等)
    61. 旋转链表
        给你一个链表的头节点 head ，旋转链表，将链表每个节点向右移动 k 个位置。
    示例 1：
        输入：head = {1, 2, 3, 4, 5}, k = 2
        输出：[4, 5, 1, 2, 3]
    示例 2：
        输入：head = {0, 1, 2}, k = 4
        输出：[2, 0, 1]
*/
public class NO061_N_RotateList {

    @Test
    public void test() {
        assert new ListNode(4, 5, 1, 2, 3).equals(rotateRight(new ListNode(1, 2, 3, 4, 5), 2));
        assert new ListNode(2, 0, 1).equals(rotateRight(new ListNode(0, 1, 2), 4));
    }

    public ListNode rotateRight(ListNode head, int k) {
        return null;
    }

}

















/**
public ListNode rotateRight(ListNode head, int k) {
    if (k == 0 || head == null || head.next == null)
        return head;

    int n = 1;
    ListNode iter = head;
    while (iter.next != null) {
        iter = iter.next;
        n++;
    }

    int add = n - k % n;
    if (add == n)
        return head;

    // 先把链表形成【环】
    iter.next = head;

    while (add-- > 0)
        iter = iter.next;

    // 新的头节点 ret
    ListNode ret = iter.next;

    // 把链表还原为无【环】
    iter.next = null;
    return ret;
}
*/