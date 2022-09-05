/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import com.leetcode.util.ListNode;
import org.junit.Test;
import static com.leetcode.util.LogUtil.info;

/**
    (简单)
    21. 合并两个有序链表
        将两个升序链表合并为一个新的升序链表并返回。
        新链表是通过拼接给定的两个链表的所有节点组成的。
    示例 1：
        输入：l1 = {1, 2, 4}, l2 = {1, 3, 4}
        输出：[1, 1, 2, 3, 4, 4]
    示例 2：
        输入：l1 = {}, l2 = {}
        输出：[]
    示例 3：
        输入：l1 = {}, l2 = {0}
        输出：[0]
*/
public class NO21_E_MergeTwoSortedLists {

    @Test
    public void test() {
        info(mergeTwoLists(
            new ListNode(1, 2, 4), new ListNode(1, 3, 4)));//[1, 1, 2, 3, 4, 4]
        info(mergeTwoLists(
            new ListNode(),new ListNode()));//[]
        info(mergeTwoLists(
            new ListNode(),new ListNode(0)));//[0]
    }

    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        if (list1 == null)
            return list2;
        if (list2 == null)
            return list1;
        ListNode dummy = new ListNode(-1);
        ListNode cur = dummy;
        return dummy.next;
    }

}

















/**
public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
    if (l1 == null)
        return l2;

    if (l2 == null)
        return l1;

    ListNode dummy = new ListNode(-1);
    ListNode cur = dummy;
    while (l1 != null && l2 != null) {
        if (l1.val < l2.val) {
            cur.next = new ListNode(l1.val);
            l1 = l1.next;
        } else {
            cur.next = new ListNode(l2.val);
            l2 = l2.next;
        }
        cur = cur.next;
    }
    cur.next = l1 == null ? l2 : l1;
    return dummy.next;
}
*/