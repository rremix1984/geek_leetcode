/**
 * copyright 2022/1/19
 */
package com.leetcode.normal;

import com.leetcode.util.ListNode;
import org.junit.Test;

/**
    (中等)
    143. 重排链表
        给定一个单链表 L 的头节点 head ，单链表 L 表示为：
            L0 → L1 → … → Ln - 1 → Ln
        请将其重新排列后变为：
            L0 → Ln → L1 → Ln - 1 → L2 → Ln - 2 → …
        不能只是单纯的改变节点内部的值，而是需要实际的进行节点交换。
    示例 1：
        输入：head = {1, 2, 3, 4}
        输出：{1, 4, 2, 3}
    示例 2：
        输入：head = {1, 2, 3, 4, 5}
        输出：{1, 5, 2, 4, 3}
*/
public class NO143_N_ReorderList_x2 {

    @Test
    public void test() {
        ListNode tmp = new ListNode(1, 2, 3, 4);
        reorderList(tmp);
        assert new ListNode(1, 4, 2, 3).equals(tmp);

        ListNode tmp2 = new ListNode(1, 2, 3, 4, 5);
        reorderList(tmp2);
        assert new ListNode(1, 5, 2, 4, 3).equals(tmp2);
    }

    public void reorderList(ListNode head) {

    }

}



















/**
// 方法1：双指针法
public void reorderList(ListNode head) {
    if (head == null)
        return;

    List<ListNode> list = new ArrayList<>();
    ListNode node = head;
    while (node != null) {
        list.add(node);
        node = node.next;
    }

    int i = 0;
    int j = list.size() - 1;
    while (i < j) {
        list.get(i).next = list.get(j);
        i++;

        if (i == j)
            break;

        list.get(j).next = list.get(i);
        j--;
    }

    list.get(i).next = null;
}
*/