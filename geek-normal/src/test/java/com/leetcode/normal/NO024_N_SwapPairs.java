/**
 * copyright 2022/1/19
 */
package com.leetcode.normal;

import com.leetcode.util.ListNode;
import org.junit.Test;

import static com.leetcode.util.ListNode.assertNodeEquals;

/**
    [LISTNODE] |
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
@SuppressWarnings("all")
public class NO024_N_SwapPairs {

    @Test
    public void test() {
        assertNodeEquals(
                swapPairs(new ListNode<>(1, 2, 3, 4)), 2, 1, 4, 3);
//        assertNodeEquals(
//                swapPairs(new ListNode<>()), new ListNode<>());
//        assertNodeEquals(
//                swapPairs(new ListNode<>(1)), 1);
    }

    public ListNode<Integer> swapPairs(ListNode<Integer> head) {
        // 2024/5/20 NO.1 递归能做出来了，一天做一道新题吧，多了做不完，反而会反噬自

        return null;
    }

}














/*
// 方法1：递归解法
public ListNode swapPairs(ListNode head) {
    if (head == null || head.next == null)
        return head;
//
//      _<(2)__
//     |       |       ______________________
//    \|/      |      |     【以下是递归部分】   |
//   head     next    | n1 ---> n2 ---> null |
//     |      (0)     |______________________|
//     |              /|\
//     |_______>_______|
//          (1)
//
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