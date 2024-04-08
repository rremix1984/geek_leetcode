/**
 * copyright: Copyright (c) 2020-2021 fudandb(fudan-bigdata)
 */
package com.lcr;

import com.Node;
import org.junit.Test;
import static com.Node.cNode;
import static com.Node.printNode;
import static org.junit.Assert.assertEquals;

/**
    [LISTNODE] |
    (中等)
    LCR.029 循环有序列表的插入
    给定循环单调非递减列表中的一个点，写一个函数向这个列表中插入一个新元素 insertVal，
    使这个列表仍然是循环升序的。给定的可以是这个列表中任意一个顶点的指针，并不一定是这个
    列表中最小元素的指针。如果有多个满足条件的插入位置，可以选择任意一个位置插入新的值，
    插入后整个列表仍然保持有序。如果列表为空（给定的节点是 null），需要创建一个循环有
    序列表并返回这个节点。否则。请返回原先给定的节点。
    示例 1：
        输入：head = [3, 4, 1], insertVal = 2
        输出：[3, 4, 1, 2]
        解释：在上图中，有一个包含三个元素的循环有序列表，你获得值为 3 的节点的指针，
            我们需要向表中插入元素 2 。新插入的节点应该在 1 和 3 之间，插入之后，
            整个列表如上图所示，最后返回节点 3 。
    示例 2：
        输入：head = [], insertVal = 1
        输出：[1]
        解释：列表为空（给定的节点是 null），创建一个循环有序列表并返回这个节点。
    示例 3：
        输入：head = [1], insertVal = 0
        输出：[1, 0]
    提示：
        0 <= Number of Nodes <= 5 * 10^4
        -10 ^ 6 <= Node.val <= 10 ^ 6
        -10 ^ 6 <= insertVal <= 10 ^ 6
    Related Topics:链表
    插入位置有三种情况：
        1）插入值位于某两个数之间，即 cur.val <= insertVal <= cur.next.val。
        2）插入值比最大的大，即 insertVal >= cur.next >= cur.next.val。
        3）插入值比最小的小，即 cur.val >= cur.next.val >= insertVal。
    注意：
        后两种情况都是边界跳跃情况，且都满足：cur.val >= cur.next.val。
        此外，就是当head为空的情况，直接创建新节点，并将其next指向自己即可。
*/
@SuppressWarnings("all")
public class LCR_029_N_Insert {

    @Test
    public void test() {
        assertEquals("3\t4\t1\t2\t",
                printNode(insert(cNode(3, 4, 1), 2)));
        assertEquals("1\t0\t",
                printNode(insert(cNode(1), 0)));
        assertEquals("1\t",
                printNode(insert(null, 1)));

    }

    public Node insert(Node head, int insertVal) {
        // 2024/3/27 NO.1
        if (head == null) {
            head = new Node(insertVal);
            head.next = head;
            return head;
        }

        Node cur = head;
        while (cur.next != head) {
            if (cur.next.val < cur.val)
                if (cur.val <= insertVal)
                    break;
                else if (cur.next.val > insertVal)
                    break;

            if (cur.val <= insertVal && cur.next.val >= insertVal)
                break;

            cur = cur.next;
        }

        Node node = new Node(insertVal, cur.next);
        cur.next = node;
        return head;
    }

}


















/*
// 方法1：
public Node insert(Node head, int insertVal) {
    if (head == null) {
        head = new Node(insertVal);
        head.next = head;
        return head;
    }

    Node cur = head;
    while (cur.next != head) {
        if (cur.next.val < cur.val)
            if (cur.val <= insertVal)
                break;
            else if (cur.next.val > insertVal)
                break;

        if (cur.val <= insertVal && cur.next.val >= insertVal)
            break;

        cur = cur.next;
    }

    Node node = new Node(insertVal, cur.next);
    cur.next = node;
    return head;
}
*/