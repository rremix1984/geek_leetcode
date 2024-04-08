/**
 * @copyright 2024
 */
package com.lonch;

import com.lonch.util.TreeNode;
import org.junit.Test;
import sun.reflect.generics.tree.Tree;

import static org.junit.Assert.assertEquals;

/**
    [ARRAY] |
    (简单)
    NO.1 写一个根据传入的整数n，生成深度为 n 的满二插树（用泛型）
*/
@SuppressWarnings("all")
public class NO1_CreateFullBinaryTree {

    @Test
    public void test() {
        TreeNode<Integer> root = cTree(3);
        assertEquals("[1, [1, [1, [1, [1], [1, [1, [1]], [1," +
                " [1, [1, [1], [1, [1, [1]]]", root.toString());
    }

    private TreeNode<Integer> cTree(int depth) {
        // 2024/3/29 NO.1
        // 2024/4/7  NO.2 没思路，看答案做出来了
        // 2024/4/8  NO.3 没思路，能做出来了
        // TODO
        return null;
    }

    /*
    class TreeNode<T> {
        T value;
        TreeNode<T> left;
        TreeNode<T> right;

        public TreeNode(T value) {
            this.value = value;
            this.left = null;
            this.right = null;
        }
    }
    */

}




















/*
// 解决方案1：
public TreeNode<T> cTree(int depth, T value) {
    // 基准情况
    if (depth < 0)
        return null;

    // 创建当前节点
    TreeNode<T> node = new TreeNode<>(value);
    // 递归创建左右子树
    node.left = cTree(depth - 1, value);
    node.right = cTree(depth - 1, value);
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