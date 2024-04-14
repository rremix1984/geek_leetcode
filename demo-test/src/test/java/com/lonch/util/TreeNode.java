/**
 * @copyright wxz
 */
package com.lonch.util;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
    根据二叉树高度，生成满二叉树
*/
@Getter
@Setter
@SuppressWarnings("unused")
public class TreeNode<E> {

    public E val;
    public TreeNode<E> left;
    public TreeNode<E> right;

    public TreeNode(E e) {
        this.val = e;
    }

    public TreeNode() {

    }

    @Override
    public String toString() {
        return "[" + val + (
                (left == null && right == null) ? "" : ", " + left + ", "+ right +"]") ;
    }

    public static <E> void preOrder(TreeNode<E> root, List<E> res) {
        if (root == null)
            return;

        res.add(root.val);
        preOrder(root.left, res);
        preOrder(root.right, res);
    }

    public static <E> void postOrder(TreeNode<E> root, List<E> res) {
        if (root == null)
            return;

        preOrder(root.left, res);
        preOrder(root.right, res);
        res.add(root.val);
    }

    public static <E> void inOrder(TreeNode<E> root, List<E> res) {
        if (root == null)
            return;

        inOrder(root.left, res);
        res.add(root.val);
        inOrder(root.right, res);
    }

    public static <E> TreeNode<E> cTree(int depth, E value) {
        // 基准情况
        if (depth < 0)
            return null;

        // 创建当前节点
        TreeNode<E> node = new TreeNode<>(value);
        // 递归创建左右子树
        node.left = cTree(depth - 1, value);
        node.right = cTree(depth - 1, value);
        return node;
    }

    public static <E> void levelOrder(TreeNode<E> root,
                                  List<E> list) {
        if (root == null)
            return;

        Deque<TreeNode<E>> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            E val;
            while (size > 0) {
                TreeNode<E> node = queue.poll();
                if (node == null)
                    continue;

                val = node.val;
                System.out.print(node.val + " ");
                if (node.left != null)
                    queue.offer(node.left);

                if (node.right != null)
                    queue.offer(node.right);

                if (--size == 0)
                    System.out.println();

                list.add(val);
            }
        }

    }

}





















/*

// 填充父节点
public void setParents(TreeNode<E> node, TreeNode<E> parent) {
    if (node != null) {
        node.setParent(parent);
        if (node.getLeft() != null)
            setParents(node.getLeft(), node);

        if (node.getRight() != null)
            setParents(node.getRight(), node);
    }
}
*/