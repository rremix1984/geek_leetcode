/**
 * @copyright @wxz
 */
package com.lonch;

import com.lonch.util.Node;
import org.junit.Test;
import java.util.LinkedList;
import java.util.Queue;
import static com.lonch.util.Node.cTree;

/*

          ______________________
         v                      |
         1   -->     2    -->   3
       /   \       /   \       /   \
      4     5     6     7     8     9
     / \   / \   / \   / \   / \   / \
    10 11 12 13 14 15 16 17 18 19 20 21

 */
@SuppressWarnings("all")
public class NO11_BinaryTreeTraversal {

    @Test
    public void test() {
        // 假设这里有三棵树的根节点，并且它们通过parent相互连接
        Node<Character> root1 = cTree(4, 0);

        // TODO 复制一棵树
        Node<Character> root2 = copy(root1);
        Node<Character> root3 = copy(root2);
        root1.parent = root3;
        root3.parent = root2;
        root2.parent = root1;

        // TODO 遍历所有的树
        traversal(root1);
    }

    public void traversal(Node<Character> root) {
        // 2024/4/10 没思路，看答案了
        // TODO 遍历树
        if (root == null)
            return;

        Queue<Node<Character>> queue = new LinkedList<>();
        queue.add(root);

        // 添加哨兵节点标记不同的树
        if (root.parent != null)
            queue.add(root.parent);

        if (root.parent != null && root.parent.parent != null)
            queue.add(root.parent.parent);

        while (!queue.isEmpty()) {
            // 当前层的节点数量
            int size = queue.size();
            while (size > 0) {
                Node<Character> node = queue.poll();
                System.out.print(node.data + " ");

                if (node.left != null)
                    queue.add(node.left);

                if (node.right != null)
                    queue.add(node.right);

                size--;
            }
            System.out.println();
        }
    }

    public <E> Node<E> copy(Node<E> root) {
        // TODO 复制树
        if (root == null)
            return null;

        Node<E> node = new Node<>(root.data);

        node.left = copy(root.left);
        if(node.left != null)
            node.left.parent = node;

        node.right = copy(root.right);
        if(node.right != null)
            node.right.parent = node;

        return node;
    }

}

















/*
public static void traversal(Node<Character> root) {
    if (root == null)
        return;

    Queue<Node<Character>> queue = new LinkedList<>();
    queue.add(root);

    // 添加哨兵节点标记不同的树
    if (root.parent != null)
        queue.add(root.parent);

    if (root.parent != null && root.parent.parent != null)
        queue.add(root.parent.parent);

    while (!queue.isEmpty()) {
        // 当前层的节点数量
        int size = queue.size();
        while (size > 0) {
            Node<Character> node = queue.poll();
            System.out.print(node.data + " ");

            if (node.left != null)
                queue.add(node.left);

            if (node.right != null)
                queue.add(node.right);

            size--;
        }
        System.out.println(); // 每层遍历结束后换行
    }
}

public static <E> Node<E> copy(Node<E> root) {
    if (root == null) {
        return null;
    }
    // 这里是复制节点的地方，但由于限制，我们不执行实际的复制
    Node<E> node = new Node<>(root.data); // 这里违反了不使用 new 的要求

    node.left = copy(root.left);
    if(node.left != null)
        node.left.parent = node; // 设置父节点

    node.right = copy(root.right);
    if(node.right != null)
        node.right.parent = node; // 设置父节点

    return node;
}
*/