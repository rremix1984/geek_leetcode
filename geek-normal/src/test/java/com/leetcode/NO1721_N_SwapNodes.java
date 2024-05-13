/**
 * copyright 2020-04-09
 */
package com.leetcode;

import com.leetcode.util.ListNode;
import org.junit.Test;
import static com.leetcode.util.ListNode.assertNodeEquals;
import static com.leetcode.util.SystemUtil.printListNode;

/**
    [LISTNODE] |
    (中等)
    NO.1721 交换链表中的节点
    给你链表的头节点 head 和一个整数 k 。
    交换 链表正数第 k 个节点和倒数第 k 个节点的值后，返回链表的头节点
    （链表 从 1 开始索引）。
    示例 1：
        输入：head = [1, 2, 3, 4, 5], k = 2
        输出：[1, 4, 3, 2, 5]
    示例 2：
        输入：head = [7, 9, 6, 6, 7, 8, 3, 0, 9, 5], k = 5
        输出：[7, 9, 6, 6, 8, 7, 3, 0, 9, 5]
    示例 3：
        输入：head = [1], k = 1
        输出：[1]
    示例 4：
        输入：head = [1, 2], k = 1
        输出：[2, 1]
    示例 5：
        输入：head = [1, 2, 3], k = 2
        输出：[1, 2, 3]
    提示：
        链表中节点的数目是 n
        1 <= k <= n <= 105
        0 <= Node.val <= 100
    Related Topics:链表,双指针
*/
@SuppressWarnings("all")
public class NO1721_N_SwapNodes {

    @Test
    public void test() {
        assertNodeEquals(swapNodes(new ListNode<>(1, 2, 3, 4, 5), 2),
                1, 4, 3, 2, 5);
        assertNodeEquals(swapNodes(new ListNode<>(7, 9, 6, 6, 7, 8, 3, 0, 9, 5), 5),
                7, 9, 6, 6, 8, 7, 3, 0, 9, 5);
        assertNodeEquals(swapNodes(new ListNode<>(1), 1),
                1);
        assertNodeEquals(swapNodes(new ListNode<>(1, 2), 1), 2, 1);
        assertNodeEquals(swapNodes(new ListNode<>(1, 2, 3), 2),
                1, 2, 3);
    }

    public ListNode<Integer> swapNodes(ListNode<Integer> head, int k) {
        // 2024/3/30 NO.1 没做出来，看懂答案了，不容易
        ListNode<Integer> dummy = new ListNode<>(0);
        dummy.next = head;

        ListNode<Integer> left = dummy;
        ListNode<Integer> right = dummy;

        return dummy.next;
    }

    public void swap(ListNode<Integer> left, ListNode<Integer> right) {
        // TODO

    }

}














/*
// 方法1：
public ListNode swapNodes(ListNode head, int k) {
    ListNode dummyHead = new ListNode(0);
    dummyHead.next = head;

    // 指向左边待交换节点的前置节点
    ListNode left = dummyHead;

    // 指向右边待交换节点的前置节点
    ListNode right = dummyHead;

    while (head != null) {
        head = head.next;
        k--;
        if (k > 0) {
            // 左指针移向第 k-1 位
            left = left.next;
            continue;
        }
        if (k < 0)
            // 右指针开始移向第 len-k-1 位
            right = right.next;
    }
    if (right.next == left)
        // 两个节点相邻，且right在left前面，需要交换下位置
        swap(right, left);
    else
        swap(left, right);
    return dummyHead.next;
}

// 交换链表中两个节点的位置
public void swap(ListNode left, ListNode right) {

//           ————————>————————
//          |                 |
//         left     right <- tmp     null
//                      |______>______|

    if (left.next == right) {
        ListNode tmp = right.next;
        left.next.next = tmp.next;
        tmp.next = right;
        left.next = tmp;

//                      _____________(1)_ > __________
//                     |       ____<___ _(2)__        |
//                     |      |               |       |
//  dummy ->  left    tmp     n0  -> right    n1     n2 -> null
//             |       |______<_(4)_____|     |
//             |_________________(3)___ > ____|

    } else {
        ListNode tmp = left.next.next;
        left.next.next = right.next.next;
        right.next.next = tmp;
        tmp = left.next;
        left.next = right.next;
        right.next = tmp;
    }
}
*/