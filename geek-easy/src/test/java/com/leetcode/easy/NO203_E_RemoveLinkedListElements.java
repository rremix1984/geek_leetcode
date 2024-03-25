/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import com.leetcode.util.ListNode;
import org.junit.Test;

import static com.leetcode.util.SystemUtil.printListNode;
import static org.junit.Assert.assertNull;

/**
    [LISTNODE] |
    (简单)
    NO.203. 移除链表元素
        给你一个链表的头节点 head 和一个整数 val ，请你删除链表中所有
    满足 Node.val == val 的节点，并返回 新的头节点 。
    示例 1：
        输入：head = [1, 2, 6, 3, 4, 5, 6], val = 6
        输出：[1,2,3,4,5]
    示例 2：
        输入：head = [], val = 1
        输出：[]
    示例 3：
        输入：head = [7, 7, 7, 7], val = 7
        输出：[]
*/
public class NO203_E_RemoveLinkedListElements {

    @Test
    public void test() {
        assert new ListNode(1, 2, 3, 4, 5).equals(
                removeElements(new ListNode(1, 2, 6, 3, 4, 5, 6),6));
        assert new ListNode().equals(
                removeElements(new ListNode(),1));
        assertNull(removeElements(new ListNode(7, 7, 7, 7),7));
    }

    public ListNode removeElements(ListNode head, int val) {
        // 2024/3/25 NO.1 没做对
        ListNode dummy = new ListNode(-1);

        return dummy.next;
    }

}

















/*
// 方法1：双指针
public ListNode removeElements(ListNode head, int val) {
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

// 方法2：递归
public ListNode removeElements(ListNode head, int val) {
    if (head == null)
        return null;

    head.next = removeElements(head.next, val);
    if (head.val == val)
        return head.next;
    else
        return head;
}
*/