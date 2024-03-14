/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import com.leetcode.util.ListNode;
import org.junit.Assert;
import org.junit.Test;
import static com.leetcode.util.LogUtil.info;
import static org.junit.Assert.assertEquals;

/**
    [LISTNODE] |||
    (简单)
    160. 相交链表
        给你两个单链表的头节点headA和headB，请你找出并返回两个单链表相交的起始节点。
        如果两个链表不存在相交节点，返回null。
        图示两个链表在节点 c1 开始相交：
        题目数据保证整个链式结构中不存在环。
        注意，函数返回结果后，链表必须保持其原始结构。
        自定义评测：
            评测系统 的输入如下（你设计的程序 不适用 此输入）：
            intersectVal - 相交的起始节点的值。如果不存在相交节点，这一值为 0
            listA - 第一个链表
            listB - 第二个链表
            skipA - 在 listA 中（从头节点开始）跳到交叉节点的节点数
            skipB - 在 listB 中（从头节点开始）跳到交叉节点的节点数
        评测系统将根据这些输入创建链式数据结构，并将两个头节点 headA 和 headB 传递给你的程序。
        如果程序能够正确返回相交节点，那么你的解决方案将被 视作正确答案 。
    示例 1：
        输入：intersectVal = 8, listA = [4,1,8,4,5], listB = [5,6,1,8,4,5], skipA = 2, skipB = 3
        输出：Intersected at '8' -> 4 -> 5
        解释：相交节点的值为 8 （注意，如果两个链表相交则不能为 0）。
             从各自的表头开始算起，链表 A 为 [4,1,8,4,5]，链表 B 为 [5,6,1,8,4,5]。
             在 A 中，相交节点前有 2 个节点；在 B 中，相交节点前有 3 个节点。
             — 请注意相交节点的值不为 1，因为在链表 A 和链表 B 之中值为 1 的节点 (A 中第二个节点和 B 中第三个节点) 是不同的节点。换句话说，它们在内存中指向两个不同的位置，而链表 A 和链表 B 中值为 8 的节点 (A 中第三个节点，B 中第四个节点) 在内存中指向相同的位置。
    示例 2：
        输入：intersectVal = 2, listA = [1,9,1,2,4], listB = [3,2,4], skipA = 3, skipB = 1
        输出：Intersected at '2'
        解释：相交节点的值为 2 （注意，如果两个链表相交则不能为 0）。
             从各自的表头开始算起，链表 A 为 [1,9,1,2,4]，链表 B 为 [3,2,4]。
             在 A 中，相交节点前有 3 个节点；在 B 中，相交节点前有 1 个节点。
    示例 3：
        输入：intersectVal = 0, listA = [2,6,4], listB = [1,5], skipA = 3, skipB = 2
        输出：null
        解释：从各自的表头开始算起，链表 A 为 [2,6,4]，链表 B 为 [1,5]。
             由于这两个链表不相交，所以 intersectVal 必须为 0，而 skipA 和 skipB 可以是任意值。
             这两个链表不相交，因此返回 null 。
*/
public class NO160_E_IntersectionOfTwoLinkedLists {

    @Test
    public void test() {
        ListNode eight = new ListNode(8, 4, 5);
        ListNode two = new ListNode(2, 4);

        Assert.assertEquals(eight, getIntersectionNode(
                new ListNode(4, 1).next(eight),
                new ListNode(5, 6, 1).next(eight)));

        Assert.assertEquals(two, getIntersectionNode(
                new ListNode(1, 9, 1).next(two),
                new ListNode(3).next(two)));
    }


    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        // 2024/3/6  NO.1
        // 2024/3/12 NO.2
        // 2024/3/14 NO.3
        return null;
    }

}



















/*
// 方法1：双指针法
public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
    // 定义两个指针, 第一轮让两个到达末尾的节点指向另一个链表的头部,
    // 最后如果相遇则为交点(在第一轮移动中恰好抹除了长度差)
    // 两个指针等于移动了相同的距离, 有交点就返回, 无交点就是各走了两条指针的长度
    if(headA == null || headB == null)
        return null;

    ListNode pA = headA;
    ListNode pB = headB;

    // 在这里第一轮体现在pA和pB第一次到达尾部会移向另一链表的表头,
    // 而第二轮体现在如果pA或pB相交就返回交点, 不相交最后就是null==null
    while(pA != pB) {
        pA = pA == null ? headB : pA.next;
        pB = pB == null ? headA : pB.next;
    }
    return pA;
}

// 方法2：
public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
    int l1 = 0;
    int l2 = 0;
    int diff = 0;
    ListNode head1 = headA;
    ListNode head2 = headB;
    while (head1 != null) {
        l1++;
        head1 = head1.next;
    }
    while (head2 != null) {
        l2++;
        head2 = head2.next;
    }
    if (l1 < l2) {
        head1 = headB;
        head2 = headA;
        diff = l2 - l1;
    } else {
        head1 = headA;
        head2 = headB;
        diff = l1 - l2;
    }
    for (int i = 0; i < diff; i++) {
        head1 = head1.next;
    }
    while (head1 != null && head2 != null) {
        if (head1 == head2)
            return head1;
        head1 = head1.next;
        head2 = head2.next;
    }
    return null;
}
*/