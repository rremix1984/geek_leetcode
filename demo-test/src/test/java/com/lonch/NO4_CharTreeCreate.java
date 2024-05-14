/**
 * @copyright lonch
 */
package com.lonch;

import com.lonch.util.Node;
import com.lonch.util.TreeNode;
import org.junit.Test;
import java.util.*;
import static com.lonch.util.Node.print;
import static com.lonch.util.TreeNode.*;
import static java.lang.System.out;

/**
    [TREE] |||||||
    (简单)
    NO.4 指定深度（depth）的满二叉树赋值，从a到z循环赋值。
 */
@SuppressWarnings("all")
public class NO4_CharTreeCreate {

    @Test
    public void test() {
        List<Character> res2 = new ArrayList<>();
        // levelOrder(cTree(6, 0), res2);
        // 2024/4/7  NO.1 没思路，能看懂
        // 2024/4/8  NO.2 还是没思路，能看懂。层序遍历算法
        // 2024/4/9  NO.3 思路还是不太清晰，看答案做出来了
        // 2024/4/14-15-18 NO.4-5-6 一遍过
        // 2024/5/6  NO.7 一遍过
        // 2024/5/7  NO.8

    }

}



















/*
public TreeNode<Character> cTree(int depth, int cur) {
    if (depth <= 0)
        return null;

    Queue<TreeNode<Character>> queue = new LinkedList<>();
    TreeNode<Character> root = new TreeNode<>(nextChar(cur++));
    queue.offer(root);
    while (!queue.isEmpty() && depth > 1) {
        int size = queue.size(); // 当前层的节点数
        while (size > 0) {
            TreeNode<Character> node = queue.poll();
            node.left = new TreeNode<>(nextChar(cur++));
            node.right = new TreeNode<>(nextChar(cur++));
            queue.offer(node.left);
            queue.offer(node.right);
            size--;
        }
        depth--;
    }
    return root;
}

private char nextChar(int c) {
    return (char) ('a' + (c % 26));
}

// 方法2：
public TreeNode<Character> cTree(int depth, int cur) {
    if (depth == 0)
        return null;

    TreeNode<Character> node = new TreeNode<>((char)('A' + (cur % 26)));
    node.left = cTree(depth - 1, 2 * cur + 1);
    node.right = cTree(depth - 1, 2 * cur + 2);
    return node;
}
*/