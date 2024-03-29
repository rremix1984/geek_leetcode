/**
 * copyright 2022/1/19
 */
package com.leetcode.normal;

import com.leetcode.util.ListNode;
import org.junit.Test;

import static com.leetcode.util.ListNode.newCycle;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
    [LISTNODE] |
    (中等)
    NO.142. 环形链表 II
        给定一个链表的头节点  head ，返回链表开始入环的第一个节点。 如果链表无环，
        则返回 null。如果链表中有某个节点，可以通过连续跟踪 next 指针再次到达，
        则链表中存在环。 为了表示给定链表中的环，评测系统内部使用整数 pos 来表示
        链表尾连接到链表中的位置（索引从 0 开始）。如果 pos 是 -1，则在该链表中没
        有环。注意：pos 不作为参数进行传递，仅仅是为了标识链表的实际情况。不允许修改 链表。
    示例 1：
        输入：head = [3, 2, 0, -4], pos = 1
        输出：返回索引为 1 的链表节点
        解释：链表中有一个环，其尾部连接到第二个节点。
    示例 2：
        输入：head = [1, 2], pos = 0
        输出：返回索引为 0 的链表节点
        解释：链表中有一个环，其尾部连接到第一个节点。
    示例 3：
        输入：head = [1], pos = -1
        输出：返回 null
        解释：链表中没有环。
*/
public class NO142_N_LinkedListCycleII {

    @Test
    public void test() {
        assertEquals(5,
                detectCycle(newCycle(3, 2, 0, -4, 5, 6, 5)).val);
        assertEquals(2,
                detectCycle(newCycle(3, 2, 0, -4, 2)).val);
        assertEquals(1,
                detectCycle(newCycle(1, 2, 1)).val);
        assertNull(
                detectCycle(newCycle(1, -1)));
    }

    public ListNode detectCycle(ListNode head) {
        // 2024/3/27 NO.1 没做出来 快慢指针
        return null;
    }

}





















/*
// 方法1：
// 快慢指针一起跑，
// 第一次相遇是【有环】，
// 第二次从头（head）跑，相遇是交点
public ListNode detectCycle(ListNode head) {
    ListNode fast = head;
    ListNode slow = head;
    while (slow != null && fast != null && fast.next != null) {
        fast = fast.next.next;
        slow = slow.next;
        // 快慢指针的处理只能判断是否有环,并不能判断出在哪里出现的环
        if (fast == slow) {
            //循环链表的head  如果head和slow中的某一个节点出现相同
            //代表在链表的该条直线上,出现了环
            while (head != slow) {
                head = head.next;
                slow = slow.next;
            }
            return slow;
        }
    }
    return null;
}
*/