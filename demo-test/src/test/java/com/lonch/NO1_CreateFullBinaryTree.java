package com.lonch;

import com.lonch.util.TreeNode;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * 写一个根据传入的整数n，生成深度为n的满二插树。用泛型。
 */
public class NO1_CreateFullBinaryTree<T> {

    @Test
    public void test() {
        NO1_CreateFullBinaryTree<Integer> t = new NO1_CreateFullBinaryTree<>();
        TreeNode<Integer> root = t.createFullBinaryTree(3,1);
        assertEquals("[1, [1, [1, [1, [1], [1, [1, [1]], [1, [1, [1, [1], [1, [1, [1]]]", root.toString());
    }

    // 创建深度为n的满二叉树
    public TreeNode createFullBinaryTree(int depth, T t) {
        // 2024/3/29 NO.1

        return null;
    }

}




















/*
// 解决方案1：
public TreeNode<T> createFullBinaryTree(int depth, T value) {
    // 基准情况
    if (depth < 0)
        return null;

    // 创建当前节点
    TreeNode<T> node = new TreeNode<>(value);
    // 递归创建左右子树
    node.left = createFullBinaryTree(depth - 1, value);
    node.right = createFullBinaryTree(depth - 1, value);
    return node;
}

class TreeNode<T> {
    T value;
    TreeNode<T> left;
    TreeNode<T> right;

    public TreeNode(T value) {
        this.value = value;
        this.left = null;
        this.right = null;
    }

    @Override
    public String toString() {
        return "[" + val + ((left==null&&right==null)?"":", " + left + ", "+ right +"]") ;
    }

}
*/