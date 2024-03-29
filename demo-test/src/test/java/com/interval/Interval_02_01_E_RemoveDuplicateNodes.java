/**
 * copyright(c) 2021-2022
 */
package com.interval;

import com.leetcode.util.ListNode;
import org.junit.Test;

/**
    [LISTNODE] |
    (简单)
    Interval.02.01 移除重复节点
    编写代码，移除【未排序】链表中的重复节点。保留最开始出现的节点。
    示例1:
        输入：[1, 2, 3, 3, 2, 1]
        输出：[1, 2, 3]
    示例2:
        输入：[1, 1, 1, 1, 2]
        输出：[1, 2]
        提示：
            链表长度在[0, 20000]范围内。
            链表元素在[0, 20000]范围内。
    进阶：
        如果不得使用临时缓冲区，该怎么解决？
    Related Topics:哈希表,链表,双指针
*/
public class Interval_02_01_E_RemoveDuplicateNodes {

    @Test
    public void test() {
        assert new ListNode(1, 2, 3).equals(
                removeDuplicateNodes(new ListNode(1, 2, 3, 3, 2, 1)));
        assert new ListNode(1, 2).equals(
                removeDuplicateNodes(new ListNode(1, 1, 1, 1, 2)));
    }

    public ListNode removeDuplicateNodes(ListNode head) {
        // 2024/3/28 NO.1 没思路，但是答案看懂了

        return head;
    }

}














/*
// 方法1：
public ListNode removeDuplicateNodes(ListNode head) {
    ListNode cur = head;
    while (cur != null) {
        ListNode temp = cur;
        while (temp.next != null) {
            if (temp.next.val == cur.val) {
                temp.next = temp.next.next;
            } else {
                temp = temp.next;
            }
        }
        cur = cur.next;
    }
    return head;
}
*/