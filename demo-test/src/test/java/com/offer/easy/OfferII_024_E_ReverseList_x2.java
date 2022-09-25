/**
 * copyright 2022/1/19
 */
package com.offer.easy;

import com.leetcode.util.ListNode;
import org.junit.Test;

/**
    (简单)
    剑指 Offer II 024. 反转链表
        给定单链表的头节点 head ，请反转链表，并返回反转后的链表的头节点。
    示例 1：
        输入：head = {1, 2, 3, 4, 5}
        输出：{5, 4, 3, 2, 1}
    示例 2：
        输入：head = {1, 2}
        输出：{2, 1}
    示例 3：
        输入：head = {}
        输出：{}
*/
public class OfferII_024_E_ReverseList_x2 {


    @Test
    public void test() {
        assert new ListNode(5, 4, 3, 2, 1).equals(
            reverseList(new ListNode(1, 2, 3, 4, 5)));
        assert new ListNode(2, 1).equals(
            reverseList(new ListNode(1, 2)));
        assert new ListNode(1).equals(
                reverseList(new ListNode(1)));
        assert new ListNode().equals(
                reverseList(new ListNode()));
    }

    public ListNode reverseList(ListNode head) {
        return null;
    }

}















/**
// 方法1：递归法
public ListNode reverseList(ListNode head) {
    if (head == null || head.next == null)
        return head;

    ListNode node = reverseList(head.next);
    head.next.next = head;
    head.next = null;
    return node;
}


// 方法2：迭代法
public ListNode reverseList(ListNode head) {
    if (head == null || head.next == null)
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