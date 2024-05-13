/**
 * @copyright @wxz
 */
package com.lonch;

import com.lonch.util.Node;
import lombok.val;
import org.junit.Test;
import java.util.*;
import static com.lonch.util.Node.*;
import static java.lang.System.out;
import static java.lang.System.arraycopy;
import static java.util.Comparator.comparingInt;
import static org.junit.Assert.assertEquals;

/**
    [TREENODE] |||||||||||||
    (中等)
    NO.11 创建的树复制成三个份，根用 parent 彼此连接，
        从任意节点开始，遍历全部，输出遍历结果要求:
        1）不能 new 新的内存空间，
        2）不能使用全局变量，
        3）不能改动树结构，
        4）不能修改节点结构和值
        5）只能从给定的节点开始变遍历，而且不能使用任何中间介质做排重或存储.
         《也就是不能使用 HashSet 做判断》
           _______________________
          |                      |
          V                      |
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
        // 2024/4/10 NO.1 没做出来
        // 2024/4/11 NO.2 没做出来，思路对
        // 2024/4/12、13、14、15、16、18
        //           NO.3、4、5、6、7、8 一遍过
        // 2024/4/30 NO.9 一遍过
        // 2024/5/7、8、10
        //           NO.10-11-12 一遍过，dijkstra算法看懂了，更简单
        // 2024/5/11 NO-13 Dijkstra算法没做出来，没真的理解
        // 2024/5/12 NO-14 Dijkstra 算法做错了，要多练习，
        //           print方法也要练，很可能要考
        // 2024/5/13 NO-15 一遍过
        Node<Character> root1 = cTree(3, 0);
        Node<Character> root2 = root1.copyAndParent();
        Node<Character> root3 = root2.copyAndParent();
        root3.parent = root1;
        print(root1);
        StringBuilder sb = new StringBuilder();
        // 方法1：剪枝法
        // dfs(root1.right.left, sb);

        // 方法2：dijkstra 算法
        dijkstra(root1.right.left, sb);
        out.println(sb);
    }

    private static void dijkstra(Node<Character> root,
                                 StringBuilder sb) {
        if (root == null)
            return;

        Queue<Node<Character>> queue
                = new PriorityQueue<>(comparingInt(n -> n.dist));
        root.dist = 0;
        queue.add(root);

        while (!queue.isEmpty()) {
            Node<Character> cur = queue.poll();
            sb.append(cur.data + " ");
            cur.getNeighbors().forEach(
                next -> {
                    update(queue, cur, next);
                }
            );
        }
    }

    private static void update(Queue<Node<Character>> queue,
                               Node<Character> node, Node<Character> next) {
        // 假设所有边的权重为 1
        int dist = node.dist + 1;
        if (dist < next.dist) {
            next.dist = dist;
            queue.add(next);
        }
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
    print(root1);
}

// 方法2：不使用 HashSet 去重， 或者 visited 标志位
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
    // cur.data = val;
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

// 方法2：Dijkstra算法，由于数据结构是一个有向无环图，
// 任意2个节点之间有且仅有一条最短路径，所以可以做到路径唯一。
private static void dijkstra(Node<Character> root,
                             StringBuilder sb) {
    if (root == null)
        return;

    Queue<Node<Character>> queue
            = new PriorityQueue<>(comparingInt(n -> n.dist));
    root.dist = 0;
    queue.add(root);

    while (!queue.isEmpty()) {
        Node<Character> cur = queue.poll();
        sb.append(cur.data + " ");
        cur.getNeighbors().forEach(
            next -> {
                update(queue, cur, next);
            }
        );
    }
}

private static void update(Queue<Node<Character>> queue,
                           Node<Character> node, Node<Character> next) {
    // 假设所有边的权重为 1
    int dist = node.dist + 1;
    if (dist < next.dist) {
        next.dist = dist;
        queue.add(next);
    }
}
*/