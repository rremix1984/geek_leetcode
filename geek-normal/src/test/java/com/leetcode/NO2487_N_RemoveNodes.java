package com.leetcode;

import com.leetcode.util.ListNode;
import org.junit.Test;

/**
    [LISTNODE]
    (中等)
    NO.2487 从链表中移除节点
       给你一个链表的头节点 head 。移除每个右侧有一个更大数值的节点。
    返回修改后链表的头节点 head 。
    示例 1：
        输入：head = [5, 2, 13, 3, 8]
        输出：[13, 8]
        解释：需要移除的节点是 5 ，2 和 3 。
            - 节点 13 在节点 5 右侧。
            - 节点 13 在节点 2 右侧。
            - 节点 8 在节点 3 右侧。
    示例 2：
        输入：head = [1, 1, 1, 1]
        输出：[1, 1, 1, 1]
        解释：每个节点的值都是 1 ，所以没有需要移除的节点。
    提示：
        给定列表中的节点数目在范围 [1, 105] 内
        1 <= Node.val <= 105
    Related Topics:栈,递归,链表,单调栈
*/
public class NO2487_N_RemoveNodes {

    @Test
    public void test() {
        assert new ListNode<>(13, 8).equals(
                removeNodes(new ListNode<>(5, 2, 13, 3, 8)));
        assert new ListNode<>(1, 1, 1, 1).equals(
                removeNodes(new ListNode<>(1, 1, 1, 1)));
    }

    // 递归
    public ListNode<Integer> removeNodes(ListNode<Integer> head) {
        return head;
    }

}
















/*
// 方法1：递归
public ListNode removeNodes(ListNode head) {
    if (head == null)
        return null;

    head.next = removeNodes(head.next);
    if (head.next != null && head.val < head.next.val)
        return head.next;

    return head;
}
*/