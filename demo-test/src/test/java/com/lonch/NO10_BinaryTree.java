/**
 * @copyright @wxz
 */
package com.lonch;

import com.lonch.util.Node;
import org.junit.Test;

/**
    [TREENODE] |
    (中等)
    NO.10 先定义 Node 节点，创建 n 阶满二叉树，数据值按顺序 A-Z 循环赋值
    public class Node<E> {
        E data;
        Node<E> parent;
        Node<E> left;
        Node<E> right;
    }
*/
public class NO10_BinaryTree {

    @Test
    public void test() {
        Node<Character> root = cTree(3, 0); // 创建3阶满二叉树
        // 这里可以添加代码来遍历和显示树的结构
        printTree(root);
    }

    public Node<Character> cTree(int depth, int cur) {
        // 2024/4/10 NO.1 没思路，能看懂
        if (depth == 0)
            return null;

        // TODO
        return null;
    }

    public void printTree(Node<Character> root) {
        // TODO
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
            System.out.print(node.data + " ");
            if (node.left != null)
                queue.offer(node.left);

            if (node.right != null)
                queue.offer(node.right);

            if (--size == 0)
                System.out.println();
        }

    }
    System.out.println();
}
*/