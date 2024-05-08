/**
 * @copyright @wxz
 */
package com.lonch;

import com.lonch.util.Node;
import lombok.val;
import org.junit.Test;
import java.util.*;
//import static com.lonch.util.Node.copy;
//import static com.lonch.util.Node.travel;
import static com.lonch.util.Node.*;
import static java.lang.System.arraycopy;
import static java.lang.System.out;
import static org.junit.Assert.assertEquals;

/**
    [TREENODE] ||||||||||
    (中等)
    NO.11 创建的树复制成三个份，根用 parent 彼此连接，
        从任意节点开始，遍历全部，输出遍历结果要求:
        1）不能 new 新的内存空间，
        2）不能使用全局变量，
        3）不能改动树结构，
        4）不能修改节点结构和值
        5）只能从给定的节点开始变遍历，而且不能使用任何中间介质做排重或存储.
           《也就是不能使用 HashSet 做判断》
           ______________________
          |                      |
         V                       |
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
        Node<Character> root1 = cTree(3, 0);
        // 2024/4/10 NO.1 没做出来
        // 2024/4/11 NO.2 没做出来，思路对
        // 2024/4/12-13-14-15-16-18 NO.3-4-5-6-7-8 一遍过
        // 2024/4/30 NO.9 一遍过
        // 2024/5/7-8  NO.10-11 一遍过
        // 假设这里有三棵树的根节点，并且它们通过parent相互连接
        Node<Character> root2 = copys(root1, 26);
        Node<Character> root3 = copys(root2, 26);
        root1.parent = root2;
        root2.parent = root3;
        root3.parent = root1;
        printTree(root1);
        val list = new ArrayList<Node<Character>>();
        StringBuilder sb = new StringBuilder();
        travels(root1.left.right, sb);
        out.println(sb);
    }

    private void travels(Node<Character> cur, StringBuilder sb) {
        if (cur == null || cur.data == '*')
            return;

        sb.append(cur.data + " ");

        // 将当前节点的数据重置为特殊值来标记已访问
        char val = cur.data;
        cur.data = '*';

        travels(cur.left, sb);
        travels(cur.right, sb);
        travels(cur.parent, sb);

        // 在退出节点时，将其数据恢复为原始值
        cur.data = val;
    }

    private Node<Character> copys(Node<Character> root1, int delta) {
        if (root1 == null)
            return null;

        Node<Character> node = new Node<>((char) (root1.data + delta));

        node.left = copys(root1.left, delta);
        if (node.left != null)
            node.left.parent = node;

        node.right = copys(root1.right, delta);
        if (node.right != null)
            node.right.parent = node;

        return node;
    }

}

















/*
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

public String travel(Node<?> node) {
    if (node == null)
        return "";

    StringBuilder sb = new StringBuilder();
    dfs(node, new HashSet<>(), sb);
    return sb.toString();
}

private void dfs(Node node, HashSet visit, StringBuilder sb) {
    if (node == null || visit.contains(node))
        return;

    sb.append(node.data);
    visit.add(node);

    dfs(node.left, visit, sb);
    dfs(node.right, visit, sb);
    dfs(node.parent, visit, sb);
}

private void levelOrderTraversal(Node root, StringBuilder sb) {
    if (root == null)
        return;

    Queue<Node> queue = new LinkedList<>();
    queue.add(root);

    HashSet<Node> visited = new HashSet<>();
    visited.add(root);

    while (!queue.isEmpty()) {
        Node currentNode = queue.poll();
        sb.append(currentNode.data + " ");

        if (currentNode.left != null && !visited.contains(currentNode.left)) {
            queue.add(currentNode.left);
            visited.add(currentNode.left);
        }

        // 添加父节点的检查，避免无限循环
        if (currentNode.parent != null && !visited.contains(currentNode.parent)) {
            queue.add(currentNode.parent);
            visited.add(currentNode.parent);
        }

        if (currentNode.right != null && !visited.contains(currentNode.right)) {
            queue.add(currentNode.right);
            visited.add(currentNode.right);
        }
    }
}

@Test
public void test() {
    // 假设这里有三棵树的根节点，并且它们通过parent相互连接
    Node<Character> root1 = cTree(4, 0);

    // 复制 3 棵树
    Node<Character> root2 = copy(root1);
    Node<Character> root3 = copy(root2);
    root1.parent = root3;
    root3.parent = root2;
    root2.parent = root1;

    // 从任意节点开始遍历整棵树
    String res = travel(root1.left.right.left);
    out.println(res);
    assertEquals("JEKBDHIACFLMGNOABDHIEJKCFLMGNOABDHIEJKCFLMGNO", res);
    printTree(root1);
}
*/