/**
 * copyright 2022/1/19
 */
package com.leetcode.undo;

import com.leetcode.Node;
import org.junit.Assert;
import org.junit.Test;

import static com.leetcode.Node.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
    [LISTNODE]
    (中等)
    NO.117. 填充每个节点的下一个右侧节点指针 II
        给定一个二叉树
        struct Node {
            int val;
            Node *left;
            Node *right;
            Node *next;
        }
        填充它的每个 next 指针，让这个指针指向其下一个右侧节点。如果找不到下一个右侧节点，则将 next 指针设置为 NULL。
        初始状态下，所有 next 指针都被设置为 NULL。
    进阶：
        你只能使用常量级额外空间。
        使用递归解题也符合要求，本题中递归程序占用的栈空间不算做额外的空间复杂度。
    示例：
        输入：root = [1, 2, 3, 4, 5, null, 7]
        输出：[1, #, 2, 3, #, 4, 5, 7, #]
        解释：给定二叉树如图 A 所示，你的函数应该填充它的每个 next 指针，以指向其下一个右侧节点，如图 B 所示。序列化输出按层序遍历顺序（由 next 指针连接），'#' 表示每层的末尾。
    提示：
        树中的节点数小于 6000
        -100 <= node.val <= 100
*/
@SuppressWarnings("all")
public class NO117_N_Connect {

    @Test
    public void test() {
        Node res = connect(
            cTree(1, 2, 3, 4, 5, null, 7));
        printTree(res);
        assertNull(res.next);
        assertNextNode(res.left, res.right);
        assertNull(res.right.next);
        assertNextNode(res.left.left, res.left.right);
        assertNextNode(res.left.right, res.right.right);
        assert 3 == res.left.next.val;
        assert 5 == res.left.left.next.val;
        assert 7 == res.left.right.next.val;
    }

    public Node connect(Node root) {
        // 2024/3/27 NO.1 不会做
        if (root == null)
            return root;

        return root;
    }

}















/*
// 方法1：
public Node connect(Node root) {
    if (root == null)
        return root;

    //cur我们可以把它看做是每一层的链表
    Node cur = root;
    while (cur != null) {
        //遍历当前层的时候，为了方便操作在下一
        //层前面添加一个哑结点（注意这里是访问
        //当前层的节点，然后把下一层的节点串起来）
        Node dummy = new Node(0);

        //pre表示访下一层节点的前一个节点
        Node pre = dummy;

        //然后开始遍历当前层的链表
        while (cur != null) {
            if (cur.left != null) {
                //如果当前节点的左子节点不为空，就让pre节点
                //的next指向他，也就是把它串起来
                pre.next = cur.left;
                //然后再更新pre
                pre = pre.next;
            }

            //同理参照左子树
            if (cur.right != null) {
                pre.next = cur.right;
                pre = pre.next;
            }

            //继续访问这一行的下一个节点
            cur = cur.next;
        }

        //把下一层串联成一个链表之后，让他赋值给cur，
        //后续继续循环，直到cur为空为止
        cur = dummy.next;
    }
    return root;
}
*/