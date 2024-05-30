/**
 * copyright 2022/1/19
 */
package com.offer.easy;

import com.leetcode.util.ListNode;
import org.junit.Test;
import java.util.HashMap;
import java.util.Map;
import static com.leetcode.util.ListNode.assertNodeEquals;
import static com.leetcode.util.ListNode.reverse;

/**
    [LISTNODE] ||||||
    (简单)
    (做了很多次，感觉已经拿捏了)【其实不是，迭代法总是肌肉记忆，需要隔一段时间再来看看】
    剑指 Offer 24. 反转链表
        定义一个函数，输入一个链表的头节点，反转该链表并输出反转后链表的头节点。
    示例:
        输入: 1 -> 2 -> 3 -> 4 -> 5 -> NULL
        输出: 5 -> 4 -> 3 -> 2 -> 1 -> NULL
*/
@SuppressWarnings("all")
public class Offer_024_E_ReverseList {

    @Test
    public void test() {
        assertNodeEquals(new ListNode<>(5, 4, 3, 2, 1),
                reverseList(new ListNode<>(1, 2, 3, 4, 5)));
        assertNodeEquals(new ListNode<>(3, 4, 5, 1, 2),
                reverseList(new ListNode<>(2, 1, 5, 4, 3)));
        assertNodeEquals(null,
                reverseList(null));
    }

    public ListNode<Integer> reverseList(ListNode<Integer> head) {
        // 2024/3/16 NO.1
        // 2024/3/23 NO.2 一遍过
        // 2024/3/25 NO.3 一遍过
        // 2024/5/20 NO.4 一段时间不做，必出错，要化【定式】为【棋力】
        // 2024/5/21 NO.5 递归一遍过，迭代没做对
        // 2024/5/30 NO.6 递归一遍过，迭代没做对，迭代考验的是思考能力，不会就真不会

        return null;
    }

}

















/*
// 方法1：递归
public ListNode reverseList(ListNode head) {
    if (head == null || head.next == null)
        return head;

    ListNode newNode = reverseList(head.next);
    head.next.next = head;
    head.next = null;
    return newNode;
}

// 方法2：迭代法
public ListNode reverseList(ListNode head) {
    if (head == null)
        return head;

    ListNode pre = null;
    while (head != null) {
        ListNode next = head.next;
        head.next = pre;
        pre = head;
        head = next;
    }
    return pre;
}
*/