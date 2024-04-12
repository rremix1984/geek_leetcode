/**
 * copyright 2022/1/19
 */
package com.leetcode.normal;

import com.leetcode.util.ListNode;
import org.junit.Test;

/**
    [ARRAY]
    (中等)
    24. 两两交换链表中的节点
        给你一个链表，两两交换其中相邻的节点，并返回交换后链表的头节点。
        你必须在不修改节点内部的值的情况下完成本题（即，只能进行节点交换）。
    示例 1：
        输入：head = {1, 2, 3, 4}
        输出：{2, 1, 4, 3}
    示例 2：
        输入：head = {}
        输出：{}
    示例 3：
        输入：head = {1}
        输出：{1}
*/
public class NO024_N_SwapPairs_x2 {

    @Test
    public void test() {
        assert new ListNode<>(2, 1, 4, 3).equals(swapPairs(new ListNode<>(1, 2, 3, 4)));
        assert new ListNode<>().equals(swapPairs(new ListNode<>()));
        assert new ListNode<>(1).equals(swapPairs(new ListNode<>(1)));
    }

    public ListNode<Integer> swapPairs(ListNode<Integer> head) {
        return head;
    }

}














/**
// 方法1：递归解法
public ListNode swapPairs(ListNode head) {
    if (head == null || head.next == null)
        return head;

    ListNode next = head.next;
    head.next = swapPairs(next.next);
    next.next = head;
    return next;
}


// 方法2：
public ListNode swapPairs(ListNode head) {
    ListNode pre = new ListNode(0);
    pre.next = head;
    ListNode temp = pre;
    while(temp.next != null && temp.next.next != null) {
        ListNode start = temp.next;
        ListNode end = temp.next.next;
        temp.next = end;
        start.next = end.next;
        end.next = start;
        temp = start;
    }
    return pre.next;
}
*/