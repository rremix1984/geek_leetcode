/**
 * copyright 2022/1/19
 */
package com.leetcode.normal;

import com.leetcode.util.ListNode;
import org.junit.Test;
import java.util.Stack;

/**
    (中等)
    445. 两数相加 II
        给你两个 非空 链表来代表两个非负整数。数字最高位位于链表开始位置。它们的每个节点只存储一位数字。将这两数相加会返回一个新的链表。
        你可以假设除了数字 0 之外，这两个数字都不会以零开头。
    示例1：
        输入：l1 = {7, 2, 4, 3}, l2 = {5, 6, 4}
        输出：[7,8,0,7]
    示例2：
        输入：l1 = {2, 4, 3}, l2 = {5, 6, 4}
        输出：[8, 0, 7]
    示例3：
        输入：l1 = {0}, l2 = {0}
        输出：[0]
*/
public class NO445_N_AddTwoNumbersII_x2 {

    @Test
    public void test() {
        assert new ListNode(1,9,9,8).equals(addTwoNumbers(new ListNode(9,9,9), new ListNode(9,9,9)));
        assert new ListNode(7,8,0,7).equals(addTwoNumbers(new ListNode(7,2,4,3), new ListNode(5,6,4)));
        assert new ListNode(8,0,7).equals(addTwoNumbers(new ListNode(2,4,3), new ListNode(5,6,4)));
        assert new ListNode(0).equals(addTwoNumbers(new ListNode(0), new ListNode(0)));
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode ans = null;
        Stack<Integer> stack1 = new Stack<>();
        Stack<Integer> stack2 = new Stack<>();
        while (l1 != null) {
            stack1.push(l1.val);
            l1 = l1.next;
        }
        while (l2 != null) {
            stack2.push(l2.val);
            l2 = l2.next;
        }
        int carry = 0;
        while (!stack1.isEmpty() || !stack2.isEmpty() || carry != 0) {
            int a = stack1.isEmpty()? 0 : stack1.pop();
            int b = stack2.isEmpty()? 0 : stack2.pop();

            int cur = carry + a + b;
            carry = cur / 10;
            cur = cur % 10;

            ListNode curnode = new ListNode(cur);
            curnode.next = ans;
            ans = curnode;
        }
        return ans;
    }
}


















/**
public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
    ListNode ans = null;
    Deque<Integer> stack1 = new ArrayDeque<>();
    Deque<Integer> stack2 = new ArrayDeque<>();

    while (l1 != null) {
        stack1.push(l1.val);
        l1 = l1.next;
    }

    while (l2 != null) {
        stack2.push(l2.val);
        l2 = l2.next;
    }

    int carry = 0;
    while (!stack1.isEmpty() || !stack2.isEmpty() || carry != 0) {
        int a = stack1.isEmpty() ? 0 : stack1.pop();
        int b = stack2.isEmpty() ? 0 : stack2.pop();
        int cur = a + b + carry;
        carry = cur / 10;
        cur = cur % 10;
        ListNode curnode = new ListNode(cur);
        curnode.next = ans;
        ans = curnode;
    }
    return ans;
}
*/