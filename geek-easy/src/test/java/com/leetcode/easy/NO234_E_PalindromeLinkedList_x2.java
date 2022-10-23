/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import com.leetcode.util.ListNode;
import org.junit.Test;
import static com.leetcode.util.ListNode.reverse;
import static com.leetcode.util.LogUtil.info;

/**
    (简单)
    234. 回文链表
        给你一个单链表的头节点 head ，请你判断该链表是否为回文链表。如果是，返回 true ；否则，返回 false 。
    示例 1：
        输入：head = [1,2,2,1]
        输出：true
    示例 2：
        输入：head = [1,2]
        输出：false
*/
public class NO234_E_PalindromeLinkedList_x2 {

    @Test
    public void test() {
        assert isPalindrome(new ListNode(1, 2, 2, 1));// true
        assert !isPalindrome(new ListNode(1, 2));// false
    }

    public boolean isPalindrome(ListNode head) {
        if (head == null)
            return false;

        ListNode fast = head, slow = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }

        // 如果是奇数个节点，slow向右面移一位
        if (fast != null)
            slow = slow.next;

        slow = ListNode.reverse(slow);
        fast = head;
        while (slow != null) {
            if (fast.val != slow.val)
                return false;

            slow = slow.next;
            fast = fast.next;
        }

        return true;
    }

}




















/**
// 方法1：快慢指针 + 栈（stack）法
public boolean isPalindrome(ListNode head) {
    ListNode fast = head, slow = head;
    Stack<Integer> stack = new Stack<>();
    while (fast!=null) {
        if (fast.next != null) {
            fast = fast.next.next;
            stack.push(slow.val);
        } else {
            fast = fast.next;// 偶数
        }
        slow = slow.next;
    }

    while (slow!=null) {
        if (stack.peek() != slow.val)
            return false;
        stack.pop();
        slow = slow.next;
    }
    return true;
}

// 方法2：快慢指针法
public boolean isPalindrome(ListNode head) {
    ListNode fast = head, slow = head;
    // 快指针从左到右遍历到头，慢指针正好到正中间
    while (fast != null && fast.next != null) {
        fast = fast.next.next;
        slow = slow.next;
    }

    //如果链表是奇数个节点，就中间的归到左边
    if (fast != null)
        slow = slow.next;

    slow = ListNode.reverse(slow);
    fast = head;
    while (slow != null) {
        // 有一个不相等代表不是回文链表
        if (fast.val != slow.val)
            return false;
        fast = fast.next;
        slow = slow.next;
    }
    return true;
}
*/