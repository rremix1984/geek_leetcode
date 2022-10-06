/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import com.leetcode.util.ListNode;
import org.junit.Test;
import static com.leetcode.util.LogUtil.info;

/**
    (简单)
    83. 删除排序链表中的重复元素
        给定一个已排序的链表的头head，删除所有重复的元素，使每个元素只出现一次 。
        返回 已排序的链表 。
    示例 1：
        输入：head = {1, 1, 2}
        输出：[1, 2]
    示例 2：
        输入：head = {1, 1, 2, 3, 3}
        输出：[1, 2, 3]
*/
public class NO083_E_RemoveDuplicatesFromSortedList_x2 {

    @Test
    public void test() {
        assert new ListNode(1, 2).equals(deleteDuplicates(new ListNode(1, 1, 2)));// [1, 2]
        assert new ListNode(1, 2, 3).equals(deleteDuplicates(new ListNode(1, 1, 2, 3, 3)));// [1, 2, 3]
    }

    public ListNode deleteDuplicates(ListNode head) {
        return head;
    }
}



















/**
// 方法1：
public ListNode deleteDuplicates(ListNode head) {
    ListNode cur = head;
    while (cur != null && cur.next != null)
        if (cur.val == cur.next.val)
            cur.next = cur.next.next;
        else
            cur = cur.next;
    return head;
}
*/