/**
 * copyright 2022/1/19
 */
package com.leetcode.normal;

import com.leetcode.util.ListNode;
import org.junit.Test;

/**
    (中等)
    86. 分隔链表
        给你一个链表的头节点head和一个特定值x，请你对链表进行分隔，
        使得所有小于x的节点都出现在大于或等于x的节点之前。
        你应当保留两个分区中每个节点的初始相对位置。
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
*/
public class NO086_N_Partition_x2 {

    @Test
    public void test() {
        assert new ListNode(1, 2, 2, 4, 3, 5).equals(
                partition(new ListNode(1, 4, 3, 2, 5, 2), 3));
        assert new ListNode(1, 2).equals(
                partition(new ListNode(2, 1), 2));
    }

    public ListNode partition(ListNode head, int x) {
        return null;
    }

}
















/**
// 方法1：
public ListNode partition(ListNode head, int x) {
    ListNode small = new ListNode(0);
    ListNode smallHead = small;

    ListNode large = new ListNode(0);
    ListNode largeHead = large;

    while (head != null) {
        if (head.val < x) {
            small.next = head;
            small = small.next;
        } else {
            large.next = head;
            large = large.next;
        }
        head = head.next;
    }
    large.next = null;
    small.next = largeHead.next;
    return smallHead.next;
}
*/