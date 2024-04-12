/**
 * copyright(c) 2021 wutao.com
 */
package com.interval;

import com.leetcode.util.ListNode;
import org.junit.Test;

/**
    [LISTNODE] |
    (中等)
    Interval.02.04 分割链表
    给你一个链表的头节点 head 和一个特定值 x，请你对链表进行分隔，
    使得所有 小于 x 的节点都出现在 大于或等于 x 的节点之前。
    你不需要 保留 每个分区中各节点的初始相对位置。
    示例 1：
        输入：head = [1, 4, 3, 2, 5, 2], x = 3
        输出：[1, 2, 2, 4, 3, 5]
    示例 2：
        输入：head = [2, 1], x = 2
        输出：[1, 2]
    提示：
        链表中节点的数目在范围 [0, 200] 内
        -100 <= Node.val <= 100
        -200 <= x <= 200
    Related Topics:链表,双指针
*/
public class Interval_02_04_N_Partition {

    @Test
    public void test() {
        assert new ListNode<>(1, 2, 2, 4, 3, 5).equals(
                partition(new ListNode<>(1, 4, 3, 2, 5, 2), 3));
        assert new ListNode<>(1, 2).equals(
                partition(new ListNode<>(2, 1), 2));
    }

    public ListNode<Integer> partition(ListNode<Integer> head, int x) {
        ListNode<Integer> dummy = new ListNode<>(0, head);

        return dummy.next;
    }

}
















/*
// 方法1：
public ListNode partition(ListNode head, int x) {
    // 新建两个链表
    ListNode smlDummy = new ListNode(0);
    ListNode bigDummy = new ListNode(0);
    // 遍历链表
    ListNode sml = smlDummy;
    ListNode big = bigDummy;
    while (head != null) {
        // 将 < x 的节点加入 sml 节点后
        if (head.val < x) {
            sml.next = head;
            sml = sml.next;
            // 将 >= x 的节点加入 big 节点后
        } else {
            big.next = head;
            big = big.next;
        }
        head = head.next;
    }
    // 拼接两链表
    sml.next = bigDummy.next;
    big.next = null;
    return smlDummy.next;
}

// 方法2：
public ListNode partition(ListNode head, int x) {
    ListNode dummy = new ListNode(0, head);
    ListNode p = dummy.next;
    ListNode t = dummy;
    while (p != null) {
        //p本来就是头节点无需移动
        if (p.val < x && p != head) {
            t.next = p.next;
            p.next = dummy.next;
            dummy.next = p;
            p = t.next;
            continue;
        }
        t = p;
        p = p.next;
    }
    return dummy.next;
}
*/