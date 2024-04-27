/**
 * @copyright wxz
 */
package com.lonch.util;

import lombok.AllArgsConstructor;
import lombok.Data;

import static java.lang.System.out;

@SuppressWarnings("unused")
@Data
public class TernaryTreeNode<T> {

    public T value;

    public TernaryTreeNode<T> left;

    public TernaryTreeNode<T> middle;

    public TernaryTreeNode<T> right;

    public TernaryTreeNode(T value) {
        this.value = value;
        this.left = null;
        this.middle = null;
        this.right = null;
    }

    public static <T> void preOrder(TernaryTreeNode<T> root, StringBuilder sb) {
        if (root == null)
            return;

        sb.append(root.value);
        preOrder(root.left, sb);
        preOrder(root.right, sb);
    }

    // 打印三叉树的方法
    public static <T> void printTernaryTree(TernaryTreeNode<T> node) {
        if (node == null)
            return;

        // 打印当前节点值
        out.print(node.value + " ");

        // 递归打印左子树
        printTernaryTree(node.left);

        // 递归打印中子树
        printTernaryTree(node.middle);

        // 递归打印右子树
        printTernaryTree(node.right);
    }


}

