package com.lonch;

import com.lonch.util.TernaryTreeNode;
import com.lonch.util.TreeNode;

public class NO2_BinaryTreeToTernaryTree {

    public static <T> TernaryTreeNode<T> convert(TreeNode<T> root) {
        if (root == null)
            return null;

        // 创建当前节点的三叉树节点
        TernaryTreeNode<T> currentNode = new TernaryTreeNode<>(root.val);
        // 递归转换左子树
        currentNode.left = convert(root.left);
        // 递归转换右子树
        currentNode.right = convert(root.right);
        // 因为是从二叉树转换，所以中间节点始终为null
        currentNode.middle = null;

        return currentNode;
    }

}
