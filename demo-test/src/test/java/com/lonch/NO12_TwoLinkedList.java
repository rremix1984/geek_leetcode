package com.lonch;

import com.lonch.util.Node;
import lombok.Getter;
import lombok.Setter;

/**
    1. 首先了解、单链表、双链表概念，了解二叉树、完全二叉树和满二叉树的概念
    2. 写一个双链表，用泛型
    3. 增加一个子节点改为三链表
    4. 手写一个方法，可以传入参数N，然后可以创建深度为N的完全二叉树
    5. 传入参数3，创建一个深度为3的二叉树，然后本地运行debug，面试官会看树的结构来确认创建对错
    6. 给一个创建一个 N=4 的树，并赋值
    这是最终的树
    4  最好用递归
    规律就是，左子树是父节点的2n倍率，右子树是2N+1。
 */
public class NO12_TwoLinkedList {

    public static void main(String[] args) {
        Node<Integer> node = cTree(4, 1);
        Node.printTree(node);
    }

    private static Node<Integer> cTree(int n, int i) {
        if (n == 0)
            return null;

        Node<Integer> node = new Node<>(i);
        Node<Integer> left = cTree(n - 1,2 * i);
        if (left != null)
            left.parent = node;

        Node<Integer> right = cTree(n - 1,2 *  i + 1);
        if (right != null)
            right.parent = node;
        node.left = left;
        node.right = right;
        return node;
    }

}


