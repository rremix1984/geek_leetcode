/**
 * @copyright @wxz
 */
package com.lonch;

import com.lonch.util.Node;
import org.junit.Test;
import java.util.*;
import static com.lonch.util.Node.*;
import static java.lang.System.out;

/**
    [TREENODE] |
    (中等)
    NO.11 创建的树复制成三个份，根用 parent彼此连接，
        从任意节点开始，遍历全部，输出遍历结果要求:
        1）不能 new 新的内存空间，
        2）不能使用全局变量，
        3）不能改动树结构，
        4）不能修改节点结构和值
          ________________________
         v                       |
         1 --------> 2 --------> 3
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

        // TODO 复制 3 棵树
        Node<Character> root2 = copy(root1);
        Node<Character> root3 = copy(root2);
        root1.parent = root3;
        root3.parent = root2;
        root2.parent = root1;

        // TODO 从任意节点开始遍历整棵树
        traval(root1.left.right.left);
    }

    public void traval(Node<?> node) {
        // TODO 2024/4/10 NO.1 从任意节点开始遍历树
        // TODO 2024/4/11 NO.2 还是不会做，能看懂

    }

    public static <E> Node<E> copy(Node<E> root) {
        if (root == null)
            return null;

        // TODO 2024/4/10 NO.1 没做出来
        return null;
    }

}

















/*
public static void traversal(Node<Character> root) {
    if (root == null)
        return;

    Deque<Node<Character>> queue = new LinkedList<>();
    queue.add(root);

    // 添加哨兵节点标记不同的树
    Node<Character> next = root.parent;
    while (next != root) {
        queue.offer(next);
        next = next.parent;
    }

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
    if (root == null)
        return null;

    // 这里是复制节点的地方，但由于限制，我们不执行实际的复制
    Node<E> node = new Node<>(root.data); // 这里违反了不使用 new 的要求

    node.left = copy(root.left);
    if (node.left != null)
        node.left.parent = node; // 设置父节点

    node.right = copy(root.right);
    if (node.right != null)
        node.right.parent = node; // 设置父节点

    return node;
}

public void traverseFrom(Node<?> node) {
    dfs(node, new HashSet<Node<?>>());
}

private void dfs(Node<?> node, Set<Node<?>> visit) {
    if (node == null || visit.contains(node))
        return;

    visit.add(node);
    out.print(node.data);

    dfs(node.left, visit);
    dfs(node.right, visit);
    dfs(node.parent, visit);
}

*/