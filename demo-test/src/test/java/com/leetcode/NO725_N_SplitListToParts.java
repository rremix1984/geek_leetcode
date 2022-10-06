/**
 * copyright 2022/1/19
 */
package com.leetcode;

import com.leetcode.util.ListNode;
import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;

/**
    （中等）
    725. 分隔链表
        给你一个头结点为 head 的单链表和一个整数 k ，请你设计一个算法将链表分隔为 k 个连续的部分。
        每部分的长度应该尽可能的相等：任意两部分的长度差距不能超过 1 。这可能会导致有些部分为 null 。
        这 k 个部分应该按照在链表中出现的顺序排列，并且排在前面的部分的长度应该大于或等于排在后面的长度。
        返回一个由上述 k 部分组成的数组。
    示例 1：
        输入：head = [1,2,3], k = 5
        输出：[[1],[2],[3],[],[]]
        解释：第一个元素 output[0] 为 output[0].val = 1 ，output[0].next = null 。
             最后一个元素 output[4] 为 null ，但它作为 ListNode 的字符串表示是 [] 。
    示例 2：
        输入：head = [1,2,3,4,5,6,7,8,9,10], k = 3
        输出：[[1,2,3,4],[5,6,7],[8,9,10]]
        解释：输入被分成了几个连续的部分，并且每部分的长度相差不超过 1 。前面部分的长度大于等于后面部分的长度。
*/
public class NO725_N_SplitListToParts {

    @Test
    public void test() {
        new ListNode[]{
            new ListNode(1), new ListNode(2), new ListNode(3)}.equals(
                splitListToParts(new ListNode(1, 2, 3), 5));
        new ListNode[]{
                        new ListNode(1, 2, 3, 4), new ListNode(5, 6, 7),
            new ListNode(8, 9, 10)}.equals(
                splitListToParts(new ListNode(1, 2, 3, 4, 5, 6, 7, 8, 9, 10), 3));
    }

    public ListNode[] splitListToParts(ListNode head, int k) {
        ListNode[] res = new ListNode[k];

        int length = 0;
        ListNode cur = head;
        while (cur != null) {
            length++;
            cur = cur.next;
        }
        int div = length / k;
        int rmd = length % k;

        cur = head;
        int i = 0;
        while (cur != null && i < k) {
            res[i] = cur;
            int len = div + (i < rmd ? 1 : 0);
            for (int j = 1; j < len; j++) {
                cur = cur.next;
            }
            ListNode next = cur.next;
            cur.next = null;
            cur = next;
            i++;
        }
        return res;
    }

}
