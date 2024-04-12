/**
 * copyright 2022/1/19
 */
package com.offer.easy;

import com.leetcode.util.ListNode;
import org.junit.Test;
import static org.junit.Assert.assertNull;

/**
    (简单)
    剑指 Offer II 023. 两个链表的第一个重合节点
        给定两个单链表的头节点 headA 和 headB ，请找出并返回两个单链表相交的起始节点。如果两个链表没有交点，返回 null 。
        图示两个链表在节点 c1 开始相交：题目数据 保证 整个链式结构中不存在环。
        注意，函数返回结果后，链表必须 保持其原始结构 。
    示例 1：
        输入：intersectVal = 8, listA = [4, 1, 8, 4, 5], listB = [5, 0, 1, 8, 4, 5], skipA = 2, skipB = 3
        输出：Intersected at '8'
        解释：相交节点的值为 8 （注意，如果两个链表相交则不能为 0）。
            从各自的表头开始算起，链表 A 为 [4, 1, 8, 4, 5]，链表 B 为 [5, 0, 1, 8, 4, 5]。
            在 A 中，相交节点前有 2 个节点；在 B 中，相交节点前有 3 个节点。
    示例 2：
        输入：intersectVal = 2, listA = [0, 9, 1, 2, 4], listB = [3, 2, 4], skipA = 3, skipB = 1
        输出：Intersected at '2'
        解释：相交节点的值为 2 （注意，如果两个链表相交则不能为 0）。
            从各自的表头开始算起，链表 A 为 [0, 9, 1, 2, 4]，链表 B 为 [3, 2, 4]。
            在 A 中，相交节点前有 3 个节点；在 B 中，相交节点前有 1 个节点。
    示例 3：
        输入：intersectVal = 0, listA = [2, 6, 4], listB = [1, 5], skipA = 3, skipB = 2
        输出：null
        解释：从各自的表头开始算起，链表 A 为 [2, 6, 4]，链表 B 为 [1, 5]。
            由于这两个链表不相交，所以 intersectVal 必须为 0，而 skipA 和 skipB 可以是任意值。
            这两个链表不相交，因此返回 null 。
*/
public class OfferII_023_E_GetIntersectionNode_x2 {

    @Test
    public void test() {
        ListNode<Integer> eight = new ListNode<>(8, 4, 5);
        assert eight.equals(getIntersectionNode(new ListNode<>(4, 1).next(eight), new ListNode<>(5, 0, 1).next(eight)));
        ListNode<Integer> two = new ListNode<>(2, 4);
        assert two.equals(getIntersectionNode(new ListNode<>(0, 9, 1).next(two), new ListNode<>(3).next(two)));
        assertNull(getIntersectionNode(new ListNode<>(2, 6, 4), new ListNode<>(1, 5)));
    }

    public ListNode<Integer> getIntersectionNode(ListNode<Integer> headA, ListNode<Integer> headB) {
        return null;
    }

}
















/**
// 方法1：双指针法
public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
    ListNode p1 = headA;
    ListNode p2 = headB;
    while (p1 != p2) {
        p1 = p1 != null ? p1.next : headB;
        p2 = p2 != null ? p2.next : headA;
    }
    return p1;
}
*/