/**
 * @copyright @wxz
 */
package com.lonch;

import com.lonch.util.Node;
import org.junit.Test;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import static com.lonch.util.Node.cTree;
//import static com.lonch.util.Node.copy;
//import static com.lonch.util.Node.travel;
import static com.lonch.util.Node.printTree;
import static java.lang.System.arraycopy;
import static java.lang.System.out;
import static org.junit.Assert.assertEquals;

/**
    [TREENODE] |||||
    (中等)
    NO.11 创建的树复制成三个份，根用 parent彼此连接，
        从任意节点开始，遍历全部，输出遍历结果要求:
        1）不能 new 新的内存空间，
        2）不能使用全局变量，
        3）不能改动树结构，
        4）不能修改节点结构和值
           ________________________
          |                       |
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
        Node<Character> root1 = cTree(4, 0);
        // 2024/4/10 NO.1 没做出来
        // 2024/4/11 NO.2 没做出来，思路对
        // 2024/4/12 NO.3 一遍过
        // 2024/4/13 NO.4 一遍过
        // 2024/4/14 NO.5 一遍过

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