/**
 * copyright(c) 2021 wzc
 */
package com.interval;

import com.leetcode.util.ListNode;
import org.junit.Test;
import static com.leetcode.util.ListNode.newCycle;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
    [LISTNODE] |
    (中等)
    Interval.02.08 环路检测
    给定一个链表，如果它是有环链表，实现一个算法返回环路的开头节点。若环不存在，
    请返回 null。如果链表中有某个节点，可以通过连续跟踪 next 指针再次到达，则
    链表中存在环。 为了表示给定链表中的环，我们使用整数 pos 来表示链表尾连接到
    链表中的位置（索引从 0 开始）。 如果 pos 是 -1，则在该链表中没有环。
    注意：pos 不作为参数进行传递，仅仅是为了标识链表的实际情况。
    示例 1：
        输入：head = [3,2,0,-4], pos = 1
        输出：tail connects to node index 1
        解释：链表中有一个环，其尾部连接到第二个节点。
    示例 2：
        输入：head = [1,2], pos = 0
        输出：tail connects to node index 0
        解释：链表中有一个环，其尾部连接到第一个节点。
    示例 3：
        输入：head = [1], pos = -1
        输出：no cycle
        解释：链表中没有环。
        进阶：
        你是否可以不用额外空间解决此题？
    Related Topics:哈希表,链表,双指针
*/
public class Interval_02_08_N_DetectCycle {

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
        // 2024/3/27 NO.1
        ListNode slow = head, fast = head;
        return null;
    }

}















/*
// 方法1：
public ListNode detectCycle(ListNode head) {
    ListNode slow = head, fast = head;
    while (fast != null && fast.next != null) {
        //快慢指针，快指针每次走两步，慢指针每次走一步
        fast = fast.next.next;
        slow = slow.next;
        //先判断是否有环，
        if (slow == fast) {
            //确定有环之后才能找环的入口
            while (head != slow) {
                //两相遇指针，一个从头结点开始，
                //一个从相遇点开始每次走一步，直到
                //再次相遇为止
                head = head.next;
                slow = slow.next;
            }
            return slow;
        }
    }
    return null;
}
*/