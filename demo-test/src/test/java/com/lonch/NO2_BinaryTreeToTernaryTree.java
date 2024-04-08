/**
 * copyright 2020-2025
 */
package com.lonch;

import com.lonch.util.TernaryTreeNode;
import com.lonch.util.TreeNode;
import org.junit.Test;

import static com.lonch.util.TernaryTreeNode.printTernaryTree;
import static com.lonch.util.TreeNode.cTree;
import static org.junit.Assert.assertEquals;

/**
    [TREENODE] |
    (简单)
    NO.2 定义泛型二叉树节点，然后二叉转三叉。

 */
@SuppressWarnings("all")
public class NO2_BinaryTreeToTernaryTree {

    @Test
    public void test() {
        TreeNode<Integer> node = cTree(4, 1);
        // 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1
        printTernaryTree(convert(node));
    }

    public static <T> TernaryTreeNode<T> convert(TreeNode<T> root) {
        // 2024/4/7 NO.1 没思路，看答案看懂了
        // 2024/4/8 NO.2
        if (root == null)
            return null;

        // TODO
        return null;
    }

//    static class TernaryTreeNode<T> {
//
//    }

}
















/*

public static <T> TernaryTreeNode<T> convert(TreeNode<T> root) {
    if (root == null)
        return null;

    // 创建当前节点的三叉树节点
    TernaryTreeNode<T> node = new TernaryTreeNode<>(root.val);

    // 递归转换左子树
    node.left = convert(root.left);

    // 递归转换右子树
    node.right = convert(root.right);

    // 因为是从二叉树转换，所以中间节点始终为null
    node.middle = null;
    return node;
}

static class TernaryTreeNode<T> {
    public T value;
    TernaryTreeNode<T> left;
    TernaryTreeNode<T> middle;
    TernaryTreeNode<T> right;

    public TernaryTreeNode(T value) {
        this.value = value;
        this.left = null;
        this.middle = null;
        this.right = null;
    }
}
*/