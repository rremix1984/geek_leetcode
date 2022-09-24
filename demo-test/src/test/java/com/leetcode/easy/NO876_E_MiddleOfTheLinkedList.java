/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import com.leetcode.util.ListNode;
import com.leetcode.util.TreeNode;
import org.junit.Test;
import static com.leetcode.util.LogUtil.info;

/**
    876. 链表的中间结点
        给定一个头结点为 head 的非空单链表，返回链表的中间结点。
        如果有两个中间结点，则返回第二个中间结点。
    示例 1：
        输入：[1, 2, 3, 4, 5]
        输出：此列表中的结点 3 (序列化形式：[3,4,5])
        返回的结点值为 3 。 (测评系统对该结点序列化表述是 [3,4,5])。
        注意，我们返回了一个 ListNode 类型的对象 ans，这样：
        ans.val = 3, ans.next.val = 4, ans.next.next.val = 5, 以及 ans.next.next.next = NULL.
    示例 2：
        输入：[1, 2, 3, 4, 5, 6]
        输出：此列表中的结点 4 (序列化形式：[4,5,6])
        由于该列表有两个中间结点，值分别为 3 和 4，我们返回第二个结点。
*/
public class NO876_E_MiddleOfTheLinkedList {

    @Test
    public void test() {
        assert 3 == middleNode(new ListNode(1,2,3,4,5)).val;
        assert 4 == middleNode(new ListNode(1,2,3,4,5,6)).val;
    }

    public ListNode middleNode(ListNode head) {
        return null;
    }

}

















/**
// 方法1：
public ListNode middleNode(ListNode head) {
    if (head == null)
        return null;
    ListNode fast = head, slow = head;
    while (fast != null && fast.next != null) {
        fast = fast.next.next;
        slow = slow.next;
    }
    return slow;
}
*/