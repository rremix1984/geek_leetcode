package com.lonch;

import com.lonch.util.TreeNode;
import org.junit.Test;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static com.lonch.util.TreeNode.inOrder;
import static com.lonch.util.TreeNode.preOrder;
import static java.lang.System.out;

/**
    [TREE] ||
    (简单)
    NO.4 指定深度的满二叉树赋值，从a到z循环赋值。
 */
public class NO4_CharTreeCreate {

    @Test
    public void test() {
        List<Character> res = new ArrayList<>();
        inOrder(cTree(5), res);
        res.forEach(out::print);
    }

    int c = 0;
    public TreeNode<Character> cTree(int depth) {
        // 2024/4/7 NO.1 没思路，能看懂
        // 2024/4/8 NO.2 还是没思路，能看懂
        if (depth <= 0)
            return null;

        Queue<TreeNode> queue = new LinkedList();
        TreeNode root = new TreeNode(nextChar());
        queue.offer(root);
        while (!queue.isEmpty() && depth > 1) {
            int size = queue.size(); // 当前层的节点数
            while (size > 0) {
                TreeNode node = queue.poll();
                node.left = new TreeNode(nextChar());
                node.right = new TreeNode(nextChar());
                queue.offer(node.left);
                queue.offer(node.right);
                size--;
            }
            depth--;
        }
        return root;
    }

    private char nextChar() {
        return (char) ('a' + (c++ % 26));
    }

}



















/*
int cur = 0;
public TreeNode<Character> cTree(int depth) {
    if (depth <= 0)
        return null;

    TreeNode<Character> node = new TreeNode<>(nextChar());
    node.left = cTree(depth - 1);
    node.right = cTree(depth - 1);
    return node;
}

private char nextChar() {
    return (char) ('a' + cur++ % 26);
}
*/