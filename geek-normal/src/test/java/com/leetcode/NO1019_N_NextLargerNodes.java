/**
 * copyright@2019/12/19 lsm
 */
package com.leetcode;

import com.leetcode.util.ListNode;
import org.junit.Test;
import java.util.Stack;
import static com.leetcode.util.ListNode.getListNodeLength;
import static com.leetcode.util.SystemUtil.printArr;
import static org.junit.Assert.assertArrayEquals;

/**
    [LISTNODE] ||
    (中等)
    NO.1019 链表中的下一个更大节点
        给定一个长度为n的链表 head 对于列表中的每个节点，查找下一个更大节点的值。
    也就是说，对于每个节点，找到它旁边的第一个节点的值，这个节点的值严格大于它的值。
    返回一个整数数组answer，其中answer[i]是第i个节点（从1开始）的下一个更大的
    节点的值。
        如果第i个节点没有下一个更大的节点，设置answer[i] = 0。
    示例 1：
        输入：head = [2, 1, 5]
        输出：[5, 5, 0]
    示例 2：
        输入：head = [2, 7, 4, 3, 5]
        输出：[7, 0, 5, 5, 0]
        提示：
            链表中节点数为 n
            1 <= n <= 10 ^ 4
            1 <= Node.val <= 10 ^ 9
    Related Topics:栈,数组,链表,单调栈
    方法一：单调栈
    思路与算法：
        找出「下一个更大的元素」是经典的可以用单调栈解决的问题。我们对链表进行
    一次遍历，同时维护一个内部值单调递减（不是严格单调递减，可以相等）的栈。栈中
    的元素对应着还没有找到下一个更大的元素的那些元素，它们在栈中的顺序与它们在链
    表中出现的顺序一致。这也解释了为什么栈中的值是单调递减的：如果有两个元素不满
    足单调递减的限制，那么后一个元素大于前一个元素，与「还没有找到下一个更大的元素」
    相矛盾。
        当我们遍历到链表中的值为 val 的节点时，只要它大于栈顶元素的值，我们就
    可以不断取出栈顶的节点，即栈顶节点的下一个更大的元素就是 val。在这之后，我们
    再将 val 放入栈顶，为其在后续的遍历中找到它的下一个更大的元素，同时也保证了栈
    的单调性。
    细节：
        当我们取出栈顶的元素时，我们是不知道它在链表中的位置的。因此在单调栈中，
    我们需要额外存储一个表示位置的变量。
*/
public class NO1019_N_NextLargerNodes {

    @Test
    public void test() {
        assertArrayEquals(new int[]{5, 5, 0},
                nextLargerNodes(new ListNode(2, 1, 5)));
        assertArrayEquals(new int[]{7, 0, 5, 5, 0},
                nextLargerNodes(new ListNode(2, 7, 4, 3, 5)));
    }

    public static int[] nextLargerNodes(ListNode head) {
        // 2024/3/16 NO.1
        // 2024/3/25 NO.2 没做出来，看懂了题目。降低难度
        int len = getListNodeLength(head);
        int[] res = new int[len];
        Stack<Integer> stack = new Stack<>();
        ListNode node = head;
        int idx = 0;
        while (node != null) {
            // TODO
            node = node.next;
            idx++;
        }

        while (!stack.isEmpty()) {
            // TODO
        }
        return res;
    }

}














/*
// 方法1：
public static int[] nextLargerNodes(ListNode head) {
    //计算链表长度
    int length = getListNodeLength(head);

    //创建长度相同长度的数组
    int[] res = new int[length];
    int index = 0;

    // 保证栈中的数据非递增
    Stack<Integer> stack = new Stack<>();
    ListNode node = head;

    // 遍历链表
    while (node != null) {
        // 把当前元素放入链表
        res[index] = node.val;

        // 遇到比栈顶元素比当前元素小，则弹出
        while (!stack.isEmpty() && res[stack.peek()] < res[index]) {
            // 弹出元素 说明当前元素是弹出元素的下一个更大节点
            int c = stack.pop();
            res[c] = res[index];
        }
        //
        stack.push(index);

        //处理下一个元素
        node = node.next;
        index++;
    }

    //遍历结束 栈不为空，栈内的元素都是右面没有比它大的元素 直接赋值为0
    while (!stack.isEmpty()) {
        int c = stack.pop();
        res[c] = 0;
    }
    return res;
}
*/