/**
 * copyright 2020-04-09
 */
package com.leetcode;

import com.leetcode.util.ListNode;
import org.junit.Test;
import static com.leetcode.util.ListNode.assertNodeEquals;
import static com.leetcode.util.SystemUtil.print;
import static com.leetcode.util.SystemUtil.printListNode;

/**
    [LISTNODE] |||
    (中等)
    NO.1721 交换链表中的节点
    给你链表的头节点 head 和一个整数 k 。
    交换链表正数第 k 个节点和倒数第 k 个节点的值后，返回链表的头节点
    （链表从 1 开始索引）。
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

    场景1：left 和 right 相邻，就会出现 right 在 left 左边的情况

    pre -->  left  -->  right -->  next --> null

    left 和 right 互换，结果就出现 right.next == left 了

    pre -->  right -->  left  --> next --> null

            ____________>_(3)__________
            |                          |
            |                   (2)   \/
    ... --> left       right <------ tmp       n1 ----> n2 ----> null
                       |             (0)       /\
                       |_______(1)__>__________|

    场景2：left 和 right 不相邻
                       __________________(1)__>______________
                      |                                     |
                      |         ___________<_(2)______      |
                      |        |(0)                   |     |
                      |       \/                      |     |
     ... --> left    tmp(3)    n0 -------> right      n1    n2 -> null
              |       |                     |        /\
              |       |_______<_(5)_________|         |
              |                                       |
              |__________________(4)________>_________|
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
        print(swapNodes(null, 2));
    }

    public ListNode<Integer> swapNodes(ListNode<Integer> head, int k) {
        // TODO 不好做但是要多练习，很经典的题
        // 2024/3/30 NO.1 没做出来，看懂答案了，不容易
        // 2024/5/17 NO.2 看懂了，找不到工作就改行呗，心态好就行
        // 2024/5/20 NO.3 找不到工作，就在家休息，看书学习，但是应该有一个好心态
        /*
            ListNode<Integer> dummy = new ListNode<>(0);
            dummy.next = head;

            ListNode<Integer> left = dummy;
            ListNode<Integer> right = dummy;

            return dummy.next;
        */
        ListNode dummy = new ListNode(0);
        dummy.next = head;

        ListNode left = dummy;
        ListNode right = dummy;
        while (head != null) {
            k--;
            if (k > 0)
                left = left.next;
            else if (k < 0)
                right = right.next;
            head = head.next;
        }

        if (right.next == left)
            swap(right, left);
        else
            swap(left, right);

        return dummy.next;
    }

    public void swap(ListNode left, ListNode right) {
        // TODO left  ->  right  ->  n0  ->  null
        if (left.next == right) {


             ListNode next = right.next;
             left.next.next = next.next;
             next.next = right;
             left.next = next;
        } else {
        // TODO CASE1:  ...  ->  left  ->  n0  ->  right  ->  n1  ->  n2  ->  null
        // TODO CASE2:  ...  ->  left  ->  n0  ->  n1  ->  right  ->  n2  ->  n3 ->  null


             ListNode next = left.next.next;
             left.next.next = right.next.next;
             right.next.next = next;
             next = left.next;
             left.next = right.next;
             right.next = next;
        }
    }

}














/*
    在交换链表节点时，将节点相邻和不相邻的情况区分开来是为了正确处理链表指针的重定向。
    相邻节点的指针操作比不相邻节点的指针操作更复杂，因此需要单独处理。
    下面是详细解释为什么要区分这两种情况以及如何处理它们。

    1. 节点相邻的情况
    情况1: left 紧挨在 right 之前
    假设链表如下所示：
        dummy -> ... ->  prev  ->【left】 ->【right】->  next  -> ...
    在这种情况下，我们需要交换 left 和 right 的位置，使得链表变为：
        dummy -> ... ->  prev  ->【right】->【left】 ->  next  -> ...

    2. 节点不相邻的情况
    当节点 left 和 right 不相邻时，假设链表如下所示：
        dummy -> ... ->【left】 ->  next  ->   ...  ->  prev  ->【right】-> ...
    在这种情况下，我们需要交换 left 和 right 的位置，使得链表变为：
        dummy -> ... ->【right】->  next  ->   ...  ->  prev  ->【left】-> ...

// 方法1：
public ListNode swapNodes(ListNode head, int k) {
    ListNode dummy = new ListNode(0);
    dummy.next = head;

    // 指向左边待交换节点的前置节点
    ListNode left = dummy;

    // 指向右边待交换节点的前置节点
    ListNode right = dummy;

    while (head != null) {
        k--;
        if (k > 0)
            // 左指针移向第 k-1 位
            left = left.next;
        else if (k < 0)
            // 右指针开始移向第 len-k-1 位
            right = right.next;
        head = head.next;
    }

    // 场景1：两个节点相邻，且right在left前面，需要交换下位置
    if (right.next == left)
        swap(right, left);
    // 场景2：两个节点不相邻
    else
        swap(left, right);

    return dummy.next;
}

    public void swap(ListNode left, ListNode right) {
//
//  场景1：left 和 right 相邻，就会出现 right 在 left 左边的情况
//
//          pre -->  left  -->  right -->  next --> null
//
//          left 和 right 互换，结果就出现 right.next == left 了
//
//          pre -->  right -->  left  --> next --> null
//
//           ____________>_(3)__________
//          |                           |
//          |                 (2)      \/
// ... --> left       right <--------- tmp       n1 ----> n2 ----> null
//                      |              (0)       /\
//                      |______>__________________|
//                           （1）
//
        if (left.next == right) {
            ListNode next = right.next;
            left.next.next = next.next;
            next.next = right;
            left.next = next;

//  场景2：left 和 right 不相邻
//
//                        ___________________(1)__>____________
//                       |                                     |
//                       |           _______<_(2)_______       |
//                       |          |                   |      |
//                       |          |                   |      |
//    ... --> left      tmp(3)      n0 ------> right    n1     n2 ----> null
//             |         |         (0)          |       |
//             |         |___________<_(5)______|       |
//             |                                        |
//             |___________________(4)_>________________|
//
//
//                        ____________(1)__>____________
//                       |                              |
//                       |           ___<_(2)___        |
//                       |          |           |       |
//                       |         \|/          |       \/
//    ... ---> left     tmp(3)    right(0)      n1      n2 ---> n3 ---> null
//             |         |          |          /|\
//             |         |__<_(5)___|           |
//             |                                |
//             |____________(4)_>_______________|
//
//
//                        _______________(2)___>_________
//                       |                               |
//                       |            ____<_(1)__        |
//                       |           |           |       |
//   ... ---> right      n0         left      tmp(3)     n2 ---> n3 ---> null
//             |         |__<________|           |       (0)
//             |          (3)                    |
//             |________________>________________|
//                         (5)
//
        } else {
            ListNode next = left.next.next;
            left.next.next = right.next.next;
            right.next.next = next;
            next = left.next;
            left.next = right.next;
            right.next = next;
        }
    }

}
*/