/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import com.leetcode.util.ListNode;
import org.junit.Test;

/**
    (简单)
    1290. 二进制链表转整数
        给你一个单链表的引用结点 head。链表中每个结点的值不是 0 就是 1。已知此链表是一个整数数字的二进制表示形式。
        请你返回该链表所表示数字的 十进制值 。
    示例 1：
        输入：head = {1, 0, 1}
        输出：5
        解释：二进制数 (101) 转化为十进制数 (5)
    示例 2：
        输入：head = {0}
        输出：0
    示例 3：
        输入：head = {1}
        输出：1
    示例 4：
        输入：head = {1, 0, 0, 1, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0}
        输出：18880
    示例 5：
        输入：head = {0, 0}
        输出：0
*/
public class NO1290_E_GetDecimalValue_x2 {

    @Test
    public void test() {
        assert 5 == getDecimalValue(new ListNode(1, 0, 1));
        assert 0 == getDecimalValue(new ListNode(0));
        assert 1 == getDecimalValue(new ListNode(1));
        assert 18880 == getDecimalValue(new ListNode(1, 0, 0, 1, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0));
        assert 0 == getDecimalValue(new ListNode(0, 0));
    }

    public int getDecimalValue(ListNode head) {
        int ans = 0;
        return ans;
    }

}














/**
// 方法1：
public int getDecimalValue(ListNode head) {
    ListNode cur = head;
    int ans = 0;
    while (cur != null) {
        ans = ans * 2 + cur.val;
        cur = cur.next;
    }
    return ans;
}
*/