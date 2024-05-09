/**
 * copyright 2020-2024
 */
package com.lonch.util;

import lombok.*;
import org.junit.Test;
import static java.lang.System.out;

/**
 * TernaryTreeNode 三叉树
 * @author wangxiaozhe
 */
@Setter
@Getter
@SuppressWarnings("unused")
@NoArgsConstructor
@RequiredArgsConstructor
public class TernaryTreeNode<T> {

    @NonNull public T value;

    public TernaryTreeNode<T> left, middle, right;

    public static <T> void preOrder(TernaryTreeNode<T> root, StringBuilder sb) {
        if (root == null)
            return;

        sb.append(root.value);
        preOrder(root.left, sb);
        preOrder(root.right, sb);
    }

    public static TernaryTreeNode<Integer> cTree(int depth, int val) {
        if (depth == 0)
            return null;

        TernaryTreeNode<Integer> node = new TernaryTreeNode<>(val);
        node.left   = cTree(depth - 1, 3 * val + 1);
        node.middle = cTree(depth - 1, 3 * val + 2);
        node.right  = cTree(depth - 1, 3 * val + 3);
        return node;
    }

    // 打印三叉树的方法
    public static <T> void printTernaryTree(TernaryTreeNode<T> node) {
        if (node == null)
            return;

        out.print(node.value + " ");
        printTernaryTree(node.left);
        printTernaryTree(node.middle);
        printTernaryTree(node.right);
    }

    public static void main(String[] args) {
        TernaryTreeNode<Integer> root = cTree(4, 0);
        printTernaryTree(root);
    }

}

