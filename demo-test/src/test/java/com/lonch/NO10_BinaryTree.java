/**
 * @copyright @wxz
 */
package com.lonch;

import com.lonch.util.Node;
import org.junit.Test;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;
import static com.lonch.util.Node.printTree;
import static java.lang.System.out;

/**
    [TREENODE] |||||||
    (中等)
    NO.10 先定义 Node 节点，创建 n 阶满二叉树，数据值按顺序 A - Z 循环赋值
    public class Node<E> {
        E data;
        Node<E> parent;
        Node<E> left;
        Node<E> right;
    }
*/
@SuppressWarnings("all")
public class NO10_BinaryTree {

    @Test
    public void test() {
        // 2024/4/10 NO.1 没思路，看答案做出来了
        // 2024/4/11 NO.2 没做对，思路对了
        // 2024/4/12 NO.3 一遍过
        // 2024/4/14 NO.4 没做对，还需要练习
        // 2024/4/15-16-18 NO.5-6-7 一遍过

    }
}
















/*
// 创建 n 阶满二叉树，增加currentIndex参数
public Node<Character> cTree(int depth, int cur) {
    if (depth == 0)
        return null;

    Node<Character> node = new Node<>((char) ('A' + cur % 26));
    node.left = cTree(depth - 1, 2 * cur + 1);
    if (node.left != null)
        node.left.parent = node;

    node.right = cTree(depth - 1, 2 * cur + 2);
    if (node.right != null)
        node.right.parent = node;

    return node;
}

public static void printTree(Node<Character> root) {
    if (root == null)
        return;

    Queue<Node<Character>> queue = new LinkedList<>();
    queue.offer(root);

    while (!queue.isEmpty()) {
        int size = queue.size();
        while (size > 0) {
            Node<Character> node = queue.poll();
            out.print(node.data + " ");
            if (node.left != null)
                queue.offer(node.left);

            if (node.right != null)
                queue.offer(node.right);

            if (--size == 0)
                out.println();
        }
    }
    out.println();
}
*/