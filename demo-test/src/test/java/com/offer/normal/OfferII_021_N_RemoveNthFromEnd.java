/**
 * copyright 2022/1/19
 */
package com.offer.normal;

import com.leetcode.util.ListNode;
import org.junit.Test;

import static com.leetcode.util.ListNode.assertNodeEquals;
import static com.leetcode.util.SystemUtil.print;

/**
    (中等)
    剑指 Offer II 021. 删除链表的倒数第 n 个结点
        给定一个链表，删除链表的倒数第 n 个结点，并且返回链表的头结点。
    示例 1：
        输入：head = [1,2,3,4,5], n = 2
        输出：[1,2,3,5]
    示例 2：
        输入：head = [1], n = 1
        输出：[]
    示例 3：
        输入：head = [1,2], n = 1
        输出：[1]
    提示：
        链表中结点的数目为 sz
        1 <= sz <= 30
        0 <= Node.val <= 100
        1 <= n <= sz
*/
@SuppressWarnings("all")
public class OfferII_021_N_RemoveNthFromEnd {

    @Test
    public void test() {
        assertNodeEquals(new ListNode<>(1, 2, 3, 5),
                removeNthFromEnd(new ListNode<>(1, 2, 3, 4, 5), 2));
        assertNodeEquals(null,
                removeNthFromEnd(new ListNode<>(1), 1));
        assertNodeEquals(new ListNode<>(1),
                removeNthFromEnd(new ListNode<>(1, 2),1));
    }

    public ListNode<Integer> removeNthFromEnd(ListNode<Integer> head, int n) {
        ListNode<Integer> dummy = new ListNode<>();
        dummy.next = head;
        ListNode<Integer> cur = dummy;

        for (int i = 1; i < getLength(head) - n + 1; i++)
            cur = cur.next;

        // 删除这个节点
        cur.next = cur.next.next;
        return dummy.next;
    }

    public int getLength(ListNode<Integer> head) {
        int len = 0;
        while (head != null) {
            head = head.next;
            len++;
        }
        return len;
    }

}
















/*
// 方法1：
public ListNode removeNthFromEnd(ListNode head, int n) {
    ListNode dummy = new ListNode();
    dummy.next = head;
    ListNode cur = dummy;

    for (int i = 1; i < getLength(head) - n + 1; i++)
        cur = cur.next;

    // 删除这个节点
    cur.next = cur.next.next;

    return dummy.next;
}

public int getLength(ListNode head) {
    int len = 0;
    while (head != null) {
        len++;
        head = head.next;
    }
    return len;
}
*/