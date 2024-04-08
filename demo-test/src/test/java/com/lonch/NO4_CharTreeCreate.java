package com.lonch;

import com.lonch.util.TreeNode;
import org.junit.Test;
import sun.reflect.generics.tree.Tree;

import java.util.*;

import static com.lonch.util.TreeNode.*;
import static java.lang.Math.pow;
import static java.lang.System.out;

/**
    [TREE] ||
    (简单)
    NO.4 指定深度（depth）的满二叉树赋值，从a到z循环赋值。
 */
@SuppressWarnings("all")
public class NO4_CharTreeCreate {

    @Test
    public void test() {
        List<Character> res2 = new ArrayList<>();
        levelOrder(cTree(4), res2);
        res2.forEach(out::print);
    }

    public TreeNode<Character> cTree(int depth) {
        // 2024/4/7 NO.1 没思路，能看懂
        // 2024/4/8 NO.2 还是没思路，能看懂。层序遍历算法
        if (depth <= 0)
            return null;

        // TODO 层序遍历
        return null;
    }

}



















/*
int c = 0;
public TreeNode<Character> cTree(int depth) {
    if (depth <= 0)
        return null;

    Queue<TreeNode<Character>> queue = new LinkedList<>();
    TreeNode<Character> root = new TreeNode<>(nextChar());
    queue.offer(root);
    while (!queue.isEmpty() && depth > 1) {
        int size = queue.size(); // 当前层的节点数
        while (size > 0) {
            TreeNode<Character> node = queue.poll();
            node.left = new TreeNode<>(nextChar());
            node.right = new TreeNode<>(nextChar());
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
*/