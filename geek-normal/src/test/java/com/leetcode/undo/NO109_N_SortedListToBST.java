/**
 * copyright 2022/1/19
 */
package com.leetcode.undo;

import com.leetcode.util.ListNode;
import com.leetcode.util.TreeNode;
import org.junit.Test;

import static com.leetcode.util.LogUtil.info;
import static com.leetcode.util.MathUtils.cTree;

/**
    (中等)
    109. 有序链表转换二叉搜索树
        给定一个单链表的头节点head，其中的元素按升序排序，将其转换为高度平衡的二叉搜索树。
        本题中，一个高度平衡二叉树是指一个二叉树每个节点的左右两个子树的高度差不超过1。
    示例 1:
        输入: head = [-10,-3,0,5,9]
        输出: [0,-3,9,-10,null,5]
        解释: 一个可能的答案是[0，-3,9，-10,null,5]，它表示所示的高度平衡的二叉搜索树。
    示例 2:
        输入: head = []
        输出: []
    提示:
        head 中的节点数在[0, 2 * 104] 范围内
        -105 <= Node.val <= 105
    方法一：分治
        设当前链表的左端点为left，右端点right，包含关系为「左闭右开」，即 left 包含在链表中
    而 right 不包含在链表中。我们希望快速地找出链表的中位数节点 mid。为什么要设定「左闭右开」的关系？
    由于题目中给定的链表为单向链表，访问后继元素十分容易，但无法直接访问前驱元素。
    因此在找出链表的中位数节点 mid 之后，如果设定「左闭右开」的关系，我们就可以直接用 (left,mid)
    以及 (mid.next,right) 来表示左右子树对应的列表了。并且，初始的列表也可以用 (head,null) 方便地进行表示，
    其中 null 表示空节点。找出链表中位数节点的方法多种多样，其中较为简单的一种是「快慢指针法」。

        初始时，快指针 fast 和慢指针 slow 均指向链表的左端点 left。我们将快指针 fast 向右移动两次的同时，
    将慢指针 slow 向右移动一次，直到快指针到达边界（即快指针到达右端点或快指针的下一个节点是右端点）。
    此时，慢指针对应的元素就是中位数。在找出了中位数节点之后，我们将其作为当前根节点的元素，
    并递归地构造其左侧部分的链表对应的左子树，以及右侧部分的链表对应的右子树。
*/
public class NO109_N_SortedListToBST {

    @Test
    public void test() {
        assert cTree(0,-3,9,-10,null,5).equals(
                sortedListToBST(new ListNode(-10,-3,0,5,9)));
        assert cTree(0).equals(
                sortedListToBST(new ListNode()));
    }

    public TreeNode sortedListToBST(ListNode head) {
        return buildTree(head, null);
    }

    public TreeNode buildTree(ListNode left, ListNode right) {
        if (left == right)
            return null;

        ListNode mid = getMedian(left, right);
        TreeNode root = new TreeNode(mid.val);
        root.left = buildTree(left, mid);
        root.right = buildTree(mid.next, right);
        return root;
    }

    public ListNode getMedian(ListNode left, ListNode right) {
        ListNode fast = left;
        ListNode slow = left;
        while (fast != right && fast.next != right) {
            fast = fast.next;
            fast = fast.next;
            slow = slow.next;
        }
        return slow;
    }

}
