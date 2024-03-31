/**
 * copyright ©2019-2020 smalle
 */
package com.lcr;

import com.leetcode.util.ListNode;
import org.junit.Test;
import static com.leetcode.util.ListNode.reverse;
import static com.leetcode.util.SystemUtil.printListNode;

/**
    [ARRAY] |||
    (中等,经典题)
    LCR.026.重排链表
    给定一个单链表 L 的头节点 head ，单链表 L 表示为：
    L(0) → L(1) → … → L(n-1) → L(n)
    请将其重新排列后变为：
    L(0) → L(n) → L(1) → L(n-1) → L(2) → L(n-2) → …
    不能只是单纯的改变节点内部的值，而是需要实际的进行节点交换。
    示例 1:
        输入: head = [1, 2, 3, 4]
        输出: [1, 4, 2, 3]
    示例 2:
        输入: head = [1, 2, 3, 4, 5]
        输出: [1, 5, 2, 4, 3]
    提示：
        链表的长度范围为 [1, 5 * 10 ^ 4]
        1 <= node.val <= 1000
    Related Topics:栈,递归,链表,双指针
*/
public class LCR_026_N_ReorderList {

    @Test
    public void test() {
        ListNode src = new ListNode(1, 2, 3, 4);
        reorderList(src);
        assert src.equals(new ListNode(1, 4, 2, 3));

        ListNode src1 = new ListNode(1, 2, 3, 4, 5);
        reorderList(src1);
        assert src1.equals(new ListNode(1, 5, 2, 4, 3));
    }

    public void reorderList(ListNode head) {
        // 2024/3/27 NO.1 快慢指针，没做出来
        // 2024/3/28 NO.2 没做出来，有思路
        // 2024/3/31 NO.3 没做出来，有思路

    }

}













/*
// 方法1：
public void reorderList(ListNode head) {
    ListNode fast = head;
    ListNode slow = head;
    while (fast.next != null
        && fast.next.next != null) {
        slow = slow.next;//慢指针找到中间的节点
        fast = fast.next.next;//快指针直接到末尾节点
    }

    // 从 slow位置反转，也就是反转后半部分
    ListNode node = reverse(slow);

    // 这里是将后半部分翻转。
    // 此时快指针指向原链表的最后一位元素
//                           ___<____________
//                    ___<__/_________       \
//             ___<__/______|____     \      |
//            /      |      |    \    |      |
//    head    n0     n1     n2    n3   n4   slow   null
//    [1]    [2]    [3]    [4]   [7]  [6]   [5]     |
//     \______|____>_|______|____/    |      |      |
//            \______|__>___|________/       |      |
//                   \____>_|_______________/       |
//                          \___>____________>_____/
    ListNode cur = head;
    while (cur != null) {//再重新进行连接
        ListNode next = cur.next;//保留当前位置的下一位
        cur.next = node;//后半部分的最后一位
        cur = node;
        node = next;
    }
}
*/