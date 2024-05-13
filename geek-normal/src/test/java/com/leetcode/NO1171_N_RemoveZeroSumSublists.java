/**
 * copyright: LeetCode
 */
package com.leetcode;

import com.leetcode.util.ListNode;
import org.junit.Test;
import java.util.HashMap;
import java.util.Map;
import static com.leetcode.util.ListNode.assertNodeEquals;

/**
    [LISTNODE] |
    (中等)
    NO.1171 从链表中删去总和值为 0 的连续节点
        给你一个链表的头节点 head，请你编写代码，反复删去链表中由
    【总和】值为 0 的连续节点组成的序列，直到不存在这样的序列为止。
    删除完毕后，请你返回最终结果链表的头节点。你可以返回任何满足题目
    要求的答案。
    （注意，下面示例中的所有序列，都是对 ListNode 对象序列化的表示）
    示例 1：
        输入：head = [1, 2, -3, 3, 1]
        输出：[3, 1]
        提示：答案 [1, 2, 1] 也是正确的。
    示例 2：
        输入：head = [1, 2, 3, -3, 4]
        输出：[1, 2, 4]
    示例 3：
        输入：head = [1, 2, 3, -3, -2]
        输出：[1]
    提示：
        给你的链表中可能有 1 到 1000 个节点。
        对于链表中的每个节点，节点的值：
        -1000 <= node.val <= 1000.
    Related Topics:哈希表,链表
    解决方案：
        这个过程就像是在一条路上行走，你记录下你每走一步的累积距离。
    如果你发现你在某个点的累积距离和之前某个点的累积距离相同，这意味
    着从那个点到当前点之间的“旅程”是“徒劳的”——你回到了原点。所以，
    你决定删去这部分路程，直接从之前的点跳到当前点的下一个点，继续你
    的旅程。这样，当你完成整个旅程时，你确保了你的“旅程”中没有徒劳的
    部分，每一步都是有意义的，就像在链表中移除和为零的子列表一样。

                    __     __          连续和为0的元素
              __   | |    |x|   __      相互抵消了
             | |   | |   |x|   |x|   __    ====>          __
        __   | |   | |   |x|   |x|  | |              __  | |
    ___|_|__|_|___|_|___|x|___|x|___|_|____      ___|_|__|_|____
       1 -> 3 -> 4 -> -4 -> -3 -> 2                  1 -> 2
            |_________________|
                     |
    因为是连续和为0的元素，所以抵消掉了就相当于 0
*/
@SuppressWarnings("ALL")
public class NO1171_N_RemoveZeroSumSublists {

    @Test
    public void test() {
        assertNodeEquals(removeZeroSumSublists(
                new ListNode<>(1, 2, 3, -3, -2)), 1);
        assertNodeEquals(removeZeroSumSublists(
                new ListNode<>(1, 2, -3, 3, 1)), 3, 1);
        assertNodeEquals(removeZeroSumSublists(
                new ListNode<>(1, 2, 3, -3, 4)), 1, 2, 4);
    }

    public ListNode<Integer> removeZeroSumSublists(ListNode<Integer> head) {
        // 2024/3/28 NO.1
        // 2024/3/31 NO.2 没思路，没看懂...
        // 2024/4/1  NO.3 没思路，能看懂答案
        ListNode<Integer> dummy = new ListNode<>(0);
        dummy.next = head;
        Map<Integer, ListNode<Integer>> map = new HashMap<>();
        // TODO

        return dummy.next;
    }

}
















/*
// 方法1：
public ListNode removeZeroSumSublists(ListNode head) {
    ListNode dummy = new ListNode(0);
    dummy.next = head;
    Map<Integer, ListNode> map = new HashMap<>();

    int prefix = 0;
    for (ListNode node = dummy; node != null; node = node.next) {
        prefix += node.val;
        map.put(prefix, node);
    }

    prefix = 0;
    for (ListNode node = dummy; node != null; node = node.next) {
        prefix += node.val;
        node.next = map.get(prefix).next;
    }
    return dummy.next;
}
*/