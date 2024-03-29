package com.lonch;

import com.lonch.util.TreeNode;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static com.lonch.util.TreeNode.preOrder;

/**
    满二叉树赋值，从a到z循环赋值。
 */
public class NO4_CharTreeCreate {

    @Test
    public void test() {
        TreeNode<Character> root = cTree(5);
        List<Character> res = new ArrayList<>();
        preOrder(root, res);
        res.stream().forEach(System.out::print);
    }

    public TreeNode<Character> cTree(int depth) {
        return null;
    }

}



















/*
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