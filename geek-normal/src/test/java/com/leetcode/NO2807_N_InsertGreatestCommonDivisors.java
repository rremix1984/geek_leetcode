/**
 * copyright [2021] [young]
 */
package com.leetcode;

import com.leetcode.util.ListNode;
import org.junit.Test;
import static com.leetcode.util.ListNode.assertNodeEquals;
import static com.leetcode.util.MathUtils.gcd;

/**
    [LISTNODE] |||
    (中等)
    NO.2807 在链表中插入最大公约数
        给你一个链表的头 head ，每个结点包含一个整数值。在相邻结点之间，请你
    插入一个新的结点，结点值为这两个相邻结点值的 最大公约数 。请你返回插入之后
    的链表。两个数的 最大公约数 是可以被两个数字整除的最大正整数。
    示例 1：
        输入：head = [18, 6, 10, 3]
        输出：[18, 6, 6, 2, 10, 1, 3]
        解释：第一幅图是一开始的链表，第二幅图是插入新结点后的图（蓝色结点为新插入结点）。
            - 18 和 6 的最大公约数为 6 ，插入第一和第二个结点之间。
            - 6 和 10 的最大公约数为 2 ，插入第二和第三个结点之间。
            - 10 和 3 的最大公约数为 1 ，插入第三和第四个结点之间。
            所有相邻结点之间都插入完毕，返回链表。
    示例 2：
        输入：head = [7]
        输出：[7]
        解释：第一幅图是一开始的链表，第二幅图是插入新结点后的图（蓝色结点为新插入结点）。
        没有相邻结点，所以返回初始链表。
    提示：
        链表中结点数目在[1, 5000]之间。
        1 <= Node.val <= 1000
    Related Topics:链表,数学,数论
*/
public class NO2807_N_InsertGreatestCommonDivisors {

    @Test
    public void test() {
        assertNodeEquals(insertGreatestCommonDivisors(
                new ListNode(18, 6, 10, 3)),
                18, 6, 6, 2, 10, 1, 3);
        assertNodeEquals(insertGreatestCommonDivisors(
                new ListNode(7)),
                7);
    }

    public ListNode insertGreatestCommonDivisors(ListNode head) {
        // 2024/3/26 NO.1 没思路，gcd() 方法早忘了
        // 2024/3/27 NO.2 没做出来，有一点思路
        // 2024/3/31 NO.3 没做出来，有思路，但不对

        return head;
    }

}















/*
// 方法1：
public ListNode insertGreatestCommonDivisors(ListNode head) {
    ListNode cur = head;
    // 因为要在下一个节点前面插入，所以只要要有下一个节点
    while(cur.next != null) {
        int val = gcd(cur.val, cur.next.val);
        cur.next = new ListNode(val, cur.next);
        // 一次跳过3个
        cur = cur.next.next;
    }
    return head;
}

// 取最大公约数的方法, (a-b)互换，(b)模(a), 返回的是分子(b)
private int gcd(int a, int b) {
    while (a != 0) {
        int temp = a;
        a = b % a;
        b = temp;
    }
    return b;
}
*/