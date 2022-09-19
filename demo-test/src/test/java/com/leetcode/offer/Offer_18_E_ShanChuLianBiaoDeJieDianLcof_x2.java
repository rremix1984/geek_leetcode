/**
 * copyright 2022/1/19
 */
package com.leetcode.offer;

import com.leetcode.util.ListNode;
import org.junit.Test;
import static com.leetcode.util.LogUtil.info;

/**
    (简单)
    剑指 Offer 18. 删除链表的节点
        给定单向链表的头指针和一个要删除的节点的值，定义一个函数删除该节点。
        返回删除后的链表的头节点。
        注意：此题对比原题有改动
    示例 1:
        输入: head = {4, 5, 1, 9}, val = 5
        输出: [4, 1, 9]
        解释: 给定你链表中值为 5 的第二个节点，那么在调用了你的函数之后，该链表应变为 4 -> 1 -> 9.
    示例 2:
        输入: head = {4, 5, 1, 9}, val = 1
        输出: [4, 5, 9]
        解释: 给定你链表中值为 1 的第三个节点，那么在调用了你的函数之后，该链表应变为 4 -> 5 -> 9.
*/
public class Offer_18_E_ShanChuLianBiaoDeJieDianLcof_x2 {

    @Test
    public void test() {
        assert new ListNode(4, 1, 9).equals(
                deleteNode(new ListNode(4, 5, 1, 9), 5));
        assert new ListNode(4, 5, 9).equals(
                deleteNode(new ListNode(4, 5, 1, 9), 1));
        assert new ListNode(5, -99).equals(
                deleteNode(new ListNode(-3, 5, -99), -3));
    }

    public ListNode deleteNode(ListNode head, int val) {
        return null;
    }

}




















/**
// 方法2：单指针
public ListNode deleteNode(ListNode head, int val) {
    if (head == null)
        return null;

    if (head.val == val)
        return head.next;

    ListNode cur = head;
    while (cur.next != null && cur.next.val != val)
        cur = cur.next;

    if (cur.next != null)
        cur.next = cur.next.next;

    return head;
}

// 方法1：双指针
public ListNode deleteNode(ListNode head, int val) {
    // 如果头节点就是要删除的节点，则返回头节点的下一个节点
    if (head.val == val)
        return head.next;

    // 双指针，初始化
    ListNode pre = head, cur = head.next;
    while (cur != null && cur.val != val) {
        pre = cur;
        cur = cur.next;
    }

    if (cur != null)
        pre.next = cur.next;

    return head;
}


// 方法3：双指针（最优解）
public ListNode deleteNode(ListNode head, int val) {
    ListNode dummy = new ListNode(-1);
    dummy.next = head;

    ListNode pre = dummy;
    while (pre.next != null)
        if (pre.next.val != val)
            pre = pre.next;
        else
            pre.next = pre.next.next;

    return dummy.next;
}
*/