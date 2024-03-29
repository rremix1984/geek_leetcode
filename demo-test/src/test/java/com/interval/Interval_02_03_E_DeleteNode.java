/**
 * copyright(c) 2021 wutao.com
 */
package com.interval;

import com.leetcode.util.ListNode;
import org.junit.Test;

/**
    [LISTNODE]
    (简单)
    Interval.02.03 删除中间节点
        若链表中的某个节点，既不是链表头节点，也不是链表尾节点，则称其为该链表的
    「中间节点」。假定已知链表的某一个中间节点，请实现一种算法，将该节点从链表中删
    除。例如，传入节点 c（位于单向链表 a->b->c->d->e->f 中），将其删除后，剩余
    链表为 a->b->d->e->f。
    示例：
        输入：节点 5 （位于单向链表 4 -> 5 -> 1 -> 9 中）
        输出：不返回任何数据，从链表中删除传入的节点 5，使链表变为 4->1->9
    Related Topics:链表
    解题思路:
        如果只能访问当前节点，那么该题的解题思路就是，将自己变成其他节点。
    举个例子：A->B->C->D
    如果要删掉 B 节点，那么只需要将 B 变为 C，再把 B 的指针指向 D，即可完成。
*/
public class Interval_02_03_E_DeleteNode {

    @Test
    public void test() {
        ListNode node = new ListNode(4, 5, 1, 9);
        deleteNode(node);
        assert new ListNode(4, 1, 9).equals(node);
    }

    public void deleteNode(ListNode node) {
        // 把要删除节点的下一个节点的值赋给要删除的结点
        node.val = node.next.val;
        // 然后删除下一个节点
        node.next = node.next.next;
    }

}















/*
// 方法1：
public void deleteNode(ListNode node) {
    // 把要删除节点的下一个节点的值赋给要删除的结点
    node.val = node.next.val;
    // 然后删除下一个节点
    node.next = node.next.next;
}
*/