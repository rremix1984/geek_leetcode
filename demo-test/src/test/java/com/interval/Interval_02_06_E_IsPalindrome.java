/**
 * copyright(c) 2021.  Lcp-Keeper
 */
package com.interval;

import com.leetcode.util.ListNode;
import org.junit.Test;
import static com.leetcode.util.ListNode.reverse;
import static com.leetcode.util.SystemUtil.printListNode;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
    [LISTNODE] |
    (简单)
    Interval.02.06 回文链表
    编写一个函数，检查输入的链表是否是回文的。
    示例 1：
        输入： 1->2
        输出： false
    示例 2：
        输入： 1->2->2->1
        输出： true
        进阶：
        你能否用 O(n) 时间复杂度和 O(1) 空间复杂度解决此题？
    Related Topics:栈,递归,链表,双指针
*/
public class Interval_02_06_E_IsPalindrome {

    @Test
    public void test() {
        assertFalse(
                isPalindrome(new ListNode(1, 2)));
        assertTrue(
                isPalindrome(new ListNode(1, 2, 2, 1)));
    }

    public boolean isPalindrome(ListNode head) {
        // 2024/3/28 NO.1 快慢指针法
        // 2024/3/29 NO.2 没做出来，但是思路对了
        // 2024/3/30 NO.3 一遍过
        ListNode slow = head;
        ListNode fast = head;

        return true;
    }

}















/*
// 方法1：
public boolean isPalindrome(ListNode head) {
    ListNode slow = head;
    ListNode fast = head;
    ListNode prev = null;
    while (fast != null && fast.next != null) {
        ListNode cur = slow;
        slow = slow.next;
        fast = fast.next.next;
        cur.next = prev;
        prev = cur;
    }

    if (fast != null) {
        // 链表个数为奇数
        slow = slow.next;
    }

    // 判断pre和slow是否相等
    while (slow != null) {
        if (slow.val != prev.val)
            return false;

        slow = slow.next;
        prev = prev.next;
    }
    return true;
}
*/