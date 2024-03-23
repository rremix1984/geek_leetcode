/**
 * copyright 2022/1/19
 */
package com.leetcode.undo;

import com.leetcode.util.ListNode;
import org.junit.Test;

import java.util.Iterator;

import static com.leetcode.util.SystemUtil.print;

/**
    [LINKEDLIST] |||||
    (中等)
    NO.61 旋转链表
        给你一个链表的头节点head，旋转链表，
        将链表每个节点向右移动k个位置。
    示例 1：
        输入：head = {1, 2, 3, 4, 5}, k = 2
        输出：[4, 5, 1, 2, 3]
    示例 2：
        输入：head = {0, 1, 2}, k = 4
        输出：[2, 0, 1]
    参考官方题解思路：闭合为环
    记给定链表的长度为n，到当向右移动的次数 k ≥ n 时，仅需要向右移
    动 k % n 次即可。因为每 n 次移动都会让链表变为原状。
    这样新链表的最后一个节点为原链表的第 n - (k mod n）节点。
    首先计算出链表的长度n，并找到该链表的末尾节点，将其与头节点相连。
    这样就得到了闭合为环的链表。然后我们找到新链表的最后一个节点，
    将当前闭合为环的链表断开，即可得到结果。
*/
public class NO061_N_RotateList {

    @Test
    public void test() {
        assert new ListNode(4, 5, 1, 2, 3).equals(
                rotateRight(new ListNode(1, 2, 3, 4, 5), 2));
        assert new ListNode(2, 0, 1).equals(
                rotateRight(new ListNode(0, 1, 2), 4));
        assert new ListNode(0, 1, 2).equals(
                rotateRight(new ListNode(0, 1, 2), 3));
    }

    public ListNode rotateRight(ListNode head, int k) {
        // 2024/3/12 NO.1
        // 2024/3/18 NO.2 压根没思路。先成环、再拆环
        // 2024/3/20 NO.3 做不出来，虽然不难，但是想的不够
        // 2024/3/21 NO.4 有点明白了
        // 2024/3/23 NO.5【闭合成环】做不出来，只能说有印象
        return null;
    }

}

















/*
// 方法1：
public ListNode rotateRight(ListNode head, int k) {
    if (k == 0 || head == null || head.next == null)
        return head;

    int n = 1;
    ListNode iter = head;
    while (iter.next != null) {
        iter = iter.next;
        n++;
    }

    int add = n - k % n;
    if (add == n)
        return head;

    // 先把链表形成【环】
    iter.next = head;

    // 找到【新】头节点的上一个节点位置
    // 新链表的最后一个节点为原链表的第 n - k % n 节点。
    while (add-- > 0)
        iter = iter.next;

    // 新的头节点 ret
    ListNode ret = iter.next;

    // 把链表还原为无【环】
    iter.next = null;
    return ret;
}
*/