/**
 * copyright 2022/1/19
 */
package com.offer.easy;

import com.leetcode.util.ListNode;
import org.junit.Assert;
import org.junit.Test;

import static com.leetcode.util.SystemUtil.printListNode;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
    [LISTNODE] ||
    (简单)
    剑指 Offer 52. 两个链表的第一个公共节点
        输入两个链表，找出它们的第一个公共节点。
        如下面的两个链表：
        在节点 c1 开始相交。
    示例 1：
        输入：intersectVal = 8, listA = [4,1,8,4,5], listB = [5,0,1,8,4,5], skipA = 2, skipB = 3
        输出：Reference of the node with value = 8
        输入解释：相交节点的值为 8 （注意，如果两个列表相交则不能为 0）。从各自的表头开始算起，
            链表 A 为 [4,1,8,4,5]，链表 B 为 [5,0,1,8,4,5]。在 A 中，相交节点前有 2 个节点；在 B 中，相交节点前有 3 个节点。
    示例 2：
        输入：intersectVal = 2, listA = [0,9,1,2,4], listB = [3,2,4], skipA = 3, skipB = 1
        输出：Reference of the node with value = 2
        输入解释：相交节点的值为 2 （注意，如果两个列表相交则不能为 0）。从各自的表头开始算起，
            链表 A 为 [0,9,1,2,4]，链表 B 为 [3,2,4]。在 A 中，相交节点前有 3 个节点；在 B 中，相交节点前有 1 个节点。
    示例 3：
        输入：intersectVal = 0, listA = [2,6,4], listB = [1,5], skipA = 3, skipB = 2
        输出：null
        输入解释：从各自的表头开始算起，链表 A 为 [2,6,4]，链表 B 为 [1,5]。由于这两个链表不相交，
            所以 intersectVal 必须为 0，而 skipA 和 skipB 可以是任意值。
        解释：这两个链表不相交，因此返回 null。
*/
public class Offer_052_E_GetIntersectionNode {

    @Test
    public void test() {
        assertNull(getIntersectionNode(
                new ListNode(2, 6, 4),
                new ListNode(1, 5)));
        ListNode eight = new ListNode(8, 4, 5);
        ListNode two = new ListNode(2, 4);
        assertEquals(eight, getIntersectionNode(
                new ListNode(4, 1).next(eight),
                new ListNode(5, 6, 1).next(eight)));
        assertEquals(two, getIntersectionNode(
                new ListNode(1, 9, 1).next(two),
                new ListNode(3).next(two)));

    }

    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        // 2024/3/27 NO.1 很熟悉的题，但做错了，需要练
        // 2024/3/28 NO.2
        return headA;
    }

}


















/*
// 方法1：
public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
    if (headA == null || headB == null)
        return null;

    ListNode pA = headA;
    ListNode pB = headB;
    while (pA != pB) {
        pA = pA == null ? headB : pA.next;
        pB = pB == null ? headA : pB.next;
    }
    return pA;
}
*/