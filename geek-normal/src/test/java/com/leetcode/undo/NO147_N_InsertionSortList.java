/**
 * copyright 2022/1/19
 */
package com.leetcode.undo;

import com.leetcode.util.ListNode;
import org.junit.Test;

/**
    (中等)
    147. 对链表进行插入排序
        给定单个链表的头 head ，使用 插入排序 对链表进行排序，并返回 排序后链表的头 。
        插入排序 算法的步骤:
        插入排序是迭代的，每次只移动一个元素，直到所有元素可以形成一个有序的输出列表。
        每次迭代中，插入排序只从输入数据中移除一个待排序的元素，找到它在序列中适当的位置，并将其插入。
        重复直到所有输入数据插入完为止。
        下面是插入排序算法的一个图形示例。部分排序的列表(黑色)最初只包含列表中的第一个元素。每次迭代时，从输入数据中删除一个元素(红色)，并就地插入已排序的列表中。
        对链表进行插入排序。
    示例 1：
        输入: head = {4, 2, 1, 3}
        输出: {1, 2, 3, 4}
    示例 2：
        输入: head = {-1, 5, 3, 4, 0}
        输出: {-1, 0, 3, 4, 5}
*/
public class NO147_N_InsertionSortList {

    @Test
    public void test() {
        assert new ListNode(1, 2, 3, 4).equals(
                insertionSortList(new ListNode(4, 2, 1, 3)));
        assert new ListNode(-1, 0, 3, 4, 5).equals(
                insertionSortList(new ListNode(-1, 5, 3, 4, 0)));
    }

    public ListNode insertionSortList(ListNode head) {
        return null;
    }

}






















/**
// 方法1：插入法排序
public ListNode insertionSortList(ListNode head) {
    if (head == null)
        return head;

    ListNode dummy = new ListNode(-1);
    dummy.next = head;

    // 排好顺序的部分 sorted
    ListNode sorted = head;

    // 从前向后便利，当前指针
    ListNode curr = head.next;
    while (curr != null) {
        if (sorted.val <= curr.val) {
            sorted = sorted.next;
        } else {
            // 插入指针 prev
            ListNode prev = dummy;
            while (prev.next.val <= curr.val)
                prev = prev.next;

            sorted.next = curr.next;
            curr.next = prev.next;
            prev.next = curr;
        }
        curr = sorted.next;
    }
    return dummy.next;
}
*/