/**
 * copyright 2022/1/19
 */
package com.leetcode.offer;

import com.leetcode.util.ListNode;
import org.junit.Test;

/**
    (简单)
    剑指 Offer 25. 合并两个排序的链表
        输入两个递增排序的链表，合并这两个链表并使新链表中的节点仍然是递增排序的。
    示例1：
        输入：1->2->4, 1->3->4
        输出：1->1->2->3->4->4
*/
public class Offer25_E_MergeTwoLists_x2 {

    @Test
    public void test() {
        assert new ListNode(1, 1, 2, 3, 4, 4).equals(
            mergeTwoLists(new ListNode(1, 2, 4),
                          new ListNode(1, 3, 4)));
    }

    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        return null;
    }

}



















/**
// 方法1：
public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
    if (l1 == null)
        return l2;

    if (l2 == null)
        return l1;

    ListNode p1 = l1;
    ListNode p2 = l2;
    ListNode dummy = new ListNode(-1);
    ListNode cur = dummy;
    while (p1 != null && p2 != null) {
        if (p1.val < p2.val) {
            cur.next = p1;
            p1 = p1.next;
        } else{
            cur.next = p2;
            p2 = p2.next;
        }
        cur = cur.next;
    }
    cur.next = p1 == null ? p2 : p1;
    return dummy.next;
}


// 方法1：递归法
public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
    if (l1 == null)
        return l2;

    if (l2 == null)
        return l1;

    if (l1.val < l2.val) {
        l1.next = mergeTwoLists(l1.next, l2);
        return l1;
    } else {
        l2.next = mergeTwoLists(l1, l2.next);
        return l2;
    }
}
*/