/**
 * copyright 2022/1/19
 */
package com.leetcode.offer;

import com.leetcode.util.ListNode;
import org.junit.Test;
import java.util.Stack;
import static org.junit.Assert.assertArrayEquals;

/**
    (简单)
    剑指 Offer 06. 从尾到头打印链表
        输入一个链表的头节点，从尾到头反过来返回每个节点的值（用数组返回）。
    示例 1：
        输入：head = [1, 3, 2]
        输出：[2, 3, 1]
*/
public class Offer_06_E_ReversePrint_x2 {

    @Test
    public void test() {
        assertArrayEquals(new int[]{2, 3, 1}, reversePrint(new ListNode(1, 3, 2)));
        assertArrayEquals(new int[]{5, 4, 3, 2, 1}, reversePrint(new ListNode(1, 2, 3, 4, 5)));
        assertArrayEquals(new int[]{1}, reversePrint(new ListNode(1)));
    }

    public int[] reversePrint(ListNode head) {
        return null;
    }

}




















/**
// 方法1：
public int[] reversePrint(ListNode head) {
    Stack<ListNode> stack = new Stack<>();
    ListNode temp = head;
    while (temp != null) {
        stack.push(temp);
        temp = temp.next;
    }

    int size = stack.size();
    int[] print = new int[size];
    for (int i = 0; i < size; i++)
        print[i] = stack.pop().val;

    return print;
}
*/