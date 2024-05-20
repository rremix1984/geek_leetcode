/**
 * copyright 2022/1/19
 */
package com.offer.easy;

import com.leetcode.util.ListNode;
import org.junit.Test;

/**
    (简单)
    剑指 Offer II 027. 回文链表
        给定一个链表的 头节点 head ，请判断其是否为回文链表。
        如果一个链表是回文，那么链表节点序列从前往后看和从后往前看是相同的。
    示例 1：
        输入: head = [1, 2, 3, 3, 2, 1]
        输出: true
    示例 2：
        输入: head = [1, 2]
        输出: false
*/
@SuppressWarnings("all")
public class OfferII_027_E_IsPalindrome {

    @Test
    public void test() {
        assert  isPalindrome(new ListNode<>(1, 2, 3, 3, 2, 1));
        assert !isPalindrome(new ListNode<>(1, 2));
    }

    public boolean isPalindrome(ListNode<Integer> head) {
        return true;
    }

}

















/**
// 方法1：双指针
public boolean isPalindrome(ListNode head) {
    ListNode fast = head;
    ListNode slow = head;
    while (fast != null && fast.next != null) {
        fast = fast.next.next;
        slow = slow.next;
    }

    if (fast != null)
        slow = slow.next;
    fast = head;
    slow = ListNode.reverse(slow);
    while (slow != null) {
        if (slow.val != fast.val)
            return false;
        slow = slow.next;
        fast = fast.next;
    }
    return true;
}
*/