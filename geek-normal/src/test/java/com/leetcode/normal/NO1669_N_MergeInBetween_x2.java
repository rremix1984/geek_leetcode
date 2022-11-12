/**
 * copyright 2022/1/19
 */
package com.leetcode.normal;

import com.leetcode.util.ListNode;
import org.junit.Test;

/**
    (中等)
    1669. 合并两个链表
        给你两个链表 list1 和 list2 ，它们包含的元素分别为 n 个和 m 个。
        请你将 list1 中下标从 a 到 b 的全部节点都删除，并将list2 接在被删除节点的位置。
        下图中蓝色边和节点展示了操作后的结果：
        请你返回结果链表的头指针。
    示例 1：
        输入：list1 = [0,1,2,3,4,5], a = 3, b = 4, list2 = [1000000,1000001,1000002]
        输出：[0,1,2,1000000,1000001,1000002,5]
        解释：我们删除 list1 中下标为 3 和 4 的两个节点，并将 list2 接在该位置。上图中蓝色的边和节点为答案链表。
    示例 2：
        输入：list1 = [0,1,2,3,4,5,6], a = 2, b = 5, list2 = [1000000,1000001,1000002,1000003,1000004]
        输出：[0,1,1000000,1000001,1000002,1000003,1000004,6]
        解释：上图中蓝色的边和节点为答案链表。
    提示：
        3 <= list1.length <= 104
        1 <= a <= b < list1.length - 1
        1 <= list2.length <= 104
*/
public class NO1669_N_MergeInBetween_x2 {

    @Test
    public void test() {
        assert new ListNode(0, 1, 2, 1000000, 1000001, 1000002, 5).equals(
            mergeInBetween(new ListNode(0, 1, 2, 3, 4, 5), 3, 4,
                           new ListNode(1000000, 1000001, 1000002)));
        assert new ListNode(0, 1, 1000000, 1000001, 1000002, 1000003, 1000004, 6).equals(
            mergeInBetween(new ListNode(0, 1, 2, 3, 4, 5, 6), 2, 5,
                           new ListNode(1000000, 1000001, 1000002, 1000003, 1000004)));
    }

    public ListNode mergeInBetween(ListNode list1, int a, int b, ListNode list2) {
        return list1;
    }

}













/**
// 方法1：
public ListNode mergeInBetween(ListNode list1, int a, int b, ListNode list2) {
    ListNode p1 = list1;
    ListNode p2 = list2;
    ListNode pre = null;
    ListNode suf = null;
    int pos = 0;
    while (p1 != null) {
        // 找到a索引前的节点pre
        if (pos == a - 1)
            pre = p1;

        // 找到b索引后的节点suf
        if (pos == b)
            suf = p1.next;

        p1 = p1.next;
        pos++;
    }
    // 将list2拼接在pre后
    pre.next = list2;

    // 找到list2最后一个节点
    while (p2.next != null)
        p2 = p2.next;

    // 将suf拼接在list2最后一个节点后
    p2.next = suf;
    return list1;
}
*/