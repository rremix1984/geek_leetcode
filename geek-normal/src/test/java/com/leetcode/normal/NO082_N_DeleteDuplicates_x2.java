/**
 * copyright 2022/1/19
 */
package com.leetcode.normal;

import com.leetcode.util.ListNode;
import org.junit.Test;

/**
    (中等)
    82. 删除排序链表中的重复元素 II
        给定一个已排序的链表的头head，删除原始链表中所有重复数字的节点，
        只留下不同的数字。返回已排序的链表。
    示例 1：
        输入：head = [1, 2, 3, 3, 4, 4, 5]
        输出：[1, 2, 5]
    示例 2：
        输入：head = [1, 1, 1, 2, 3]
        输出：[2, 3]
    提示：
        链表中节点数目在范围 [0, 300] 内
        -100 <= Node.val <= 100
        题目数据保证链表已经按升序 排列
*/
public class NO082_N_DeleteDuplicates_x2 {

    @Test
    public void test() {
        assert new ListNode(1, 2, 5).equals(
                deleteDuplicates(new ListNode(1, 2, 3, 3, 4, 4, 5)));
        assert new ListNode(2, 3).equals(
                deleteDuplicates(new ListNode(1, 1, 1, 2, 3)));
    }

    public ListNode deleteDuplicates(ListNode head) {
        return null;
    }

}














/**
// 方法1：
public ListNode deleteDuplicates(ListNode head) {
    if (head == null)
        return null;

    ListNode dummy = new ListNode();
    dummy.next = head;
    ListNode cur = dummy;
    while (cur.next != null && cur.next.next != null)
        if (cur.next.val != cur.next.next.val) {
            cur = cur.next;
        } else {
            int tmp = cur.next.val;
            while (cur.next != null && cur.next.val == tmp)
                cur.next = cur.next.next;
        }

    return dummy.next;
}
*/