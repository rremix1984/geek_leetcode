/**
 * copyright @leetcode.cn
 */
package com.leetcode.hard;

import com.leetcode.util.ListNode;
import static com.leetcode.util.ListNode.reverse;
import static com.leetcode.util.SystemUtil.printListNode;

import org.junit.Test;

/**
    [LISTNODE] |
    (中等)
    NO.025 K 个一组翻转链表
     给你链表的头节点 head ，每 k 个节点一组进行翻转，请你返回修改后的链表。
    k 是一个正整数，它的值小于或等于链表的长度。如果节点总数不是 k 的整数倍，
    那么请将最后剩余的节点保持原有顺序。
    你不能只是单纯的改变节点内部的值，而是需要实际进行节点交换。
    示例 1：
        输入：head = [1, 2, 3, 4, 5], k = 2
        输出：[2, 1, 4, 3, 5]
    示例 2：
        输入：head = [1, 2, 3, 4, 5], k = 3
        输出：[3, 2, 1, 4, 5]
    提示：
        链表中的节点数目为 n
        1 <= k <= n <= 5000
        0 <= Node.val <= 1000
        进阶：你可以设计一个只用 O(1) 额外内存空间的算法解决此问题吗？
    Related Topics:递归,链表
*/
public class NO025_H_ReverseKGroup {

    @Test
    public void test() {
        assert new ListNode<>(2, 1, 4, 3, 5).equals(
                reverseKGroup(new ListNode<>(1, 2, 3, 4, 5), 2));
        assert new ListNode<>(3, 2, 1, 4, 5).equals(
                reverseKGroup(new ListNode<>(1, 2, 3, 4, 5), 3));
        assert new ListNode<>(2,1,4,3,5).equals(
                reverseKGroup(new ListNode<>(1,2,3,4,5), 2));
    }

    public ListNode<Integer> reverseKGroup(ListNode<Integer> head, int k) {
        // 2024/3/31 NO.1 没思路，不会做。看懂答案了
        // 2024/4/2  NO.2
        if (head == null || head.next == null)
            return head;

        return null;
    }

}



















/*
public ListNode reverseKGroup(ListNode head, int k) {
    if (head == null || head.next == null)
        return head;

    ListNode tail = head;
    for (int i = 0; i < k; i++) {
        // 剩余数量小于k的话，则不需要反转。
        if (tail == null)
            return head;
        tail = tail.next;
    }
    // 反转前 k 个元素
    ListNode newHead = reverse(head, tail);

    // 下一轮的开始的地方就是tail
    head.next = reverseKGroup(tail, k);
    return newHead;
}

//左闭又开区间
private ListNode reverse(ListNode head, ListNode tail) {
    ListNode pre = null;
    while (head != tail) {
        ListNode next = head.next;
        head.next = pre;
        pre = head;
        head = next;
    }
    return pre;
}
*/