/**
 * copyright 2022/1/19
 */
package com.leetcode.undo;

import com.leetcode.Node;
import org.junit.Test;
import java.util.LinkedList;
import java.util.Queue;

/**
    (中等)
    116. 填充每个节点的下一个右侧节点指针
        给定一个 完美二叉树 ，其所有叶子节点都在同一层，每个父节点都有两个子节点。二叉树定义如下：
            struct Node {
                int val;
                Node *left;
                Node *right;
                Node *next;
            }
        填充它的每个 next 指针，让这个指针指向其下一个右侧节点。如果找不到下一个右侧节点，则将 next 指针设置为 NULL。
        初始状态下，所有 next 指针都被设置为 NULL。
    示例 1：
        输入：root = [1, 2, 3, 4, 5, 6, 7]
        输出：[1, #, 2, 3, #, 4, 5, 6, 7, #]
        解释：给定二叉树如图 A 所示，你的函数应该填充它的每个 next 指针，以指向其下一个右侧节点，如图 B 所示。序列化的输出按层序遍历排列，同一层节点由 next 指针连接，'#' 标志着每一层的结束。
    示例 2:
        输入：root = []
        输出：[]
    提示：
        树中节点的数量在 [0,  212 - 1] 范围内
        -1000 <= node.val <= 1000
*/
@SuppressWarnings("all")
public class NO116_N_Connect {

    @Test
    public void test() {
        Node v7 = new Node(7);
        Node v6 = new Node(6);
        Node v5 = new Node(5);
        Node v4 = new Node(4);
        Node v3 = new Node(3, v6, v7);
        Node v2 = new Node(2, v4, v5);
        Node v1 = new Node(1, v2, v3);
        Node res = connect(v1);
        assert res.next == null;
        assert res.left.next == v3;
        assert res.right.next == null;
        assert res.left.left.next == v5;
        assert res.left.right.next == v6;
        assert res.right.left.next == v7;
        assert res.right.right.next == null;
    }

    public Node connect(Node root) {
        if (root == null)
            return root;

        // 初始化队列同时将第一层节点加入队列中，即根节点
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        // 外层的 while 循环迭代的是层数
        while (!queue.isEmpty()) {
            // 记录当前队列大小
            int size = queue.size();
            // 遍历这一层的所有节点
            for (int i = 0; i < size; i++) {
                // 从队首取出元素
                Node node = queue.poll();

                // 连接
                if (i < size - 1)
                    node.next = queue.peek();

                // 拓展下一层节点
                if (node.left != null)
                    queue.add(node.left);

                if (node.right != null)
                    queue.add(node.right);
            }
        }
        // 返回根节点
        return root;
    }

}