/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import com.leetcode.util.ListNode;
import org.junit.Test;

import static com.leetcode.util.ListNode.newCycle;
import static com.leetcode.util.LogUtil.info;

/**
    [QUEUE]
    (简单)
    141. 环形链表
        给你一个链表的头节点 head ，判断链表中是否有环。
        如果链表中有某个节点，可以通过连续跟踪 next 指针再次到达，则链表中存在环。
        为了表示给定链表中的环，评测系统内部使用整数 pos 来表示链表尾连接到链表中的位置
        （索引从 0 开始）。注意：pos 不作为参数进行传递 。仅仅是为了标识链表的实际情况。
        如果链表中存在环 ，则返回 true 。 否则，返回 false 。
    示例 1：
        输入：head = [3, 2, 0, -4], pos = 1
        输出：true
        解释：链表中有一个环，其尾部连接到第二个节点。
    示例 2：
        输入：head = [1, 2], pos = 0
        输出：true
        解释：链表中有一个环，其尾部连接到第一个节点。
    示例 3：
        输入：head = [1], pos = -1
        输出：false
        解释：链表中没有环。
*/
public class NO141_E_LinkedListCycle_x3 {

    @Test
    public void test() {
        assert hasCycle(ListNode.newCycle( 3, 2, 0, -4, 2));// true
        assert hasCycle(ListNode.newCycle( 1, 2, 1));//  true
        assert !hasCycle(ListNode.newCycle(1, -1));// false
    }

    public boolean hasCycle(ListNode head) {
        return false;
    }

}
















/**
// 方法1：快慢指针法
public boolean hasCycle(ListNode head) {
    if (head == null)
        return false;
    ListNode fast = head;
    ListNode slow = head;
    while (fast.next != null && fast.next.next != null) {
        slow = slow.next;
        fast = fast.next.next;
        if (slow == fast)
            return true;
    }
    return false;
}

// 方法2：HashSet
public boolean hasCycle(ListNode head) {
    Set<ListNode> set = new HashSet<>();
    while (head != null) {
        if (set.contains(head))
            return true;
        else
            set.add(head);
        head = head.next;
    }
    return false;
}
*/