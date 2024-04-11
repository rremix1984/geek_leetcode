/**
 * copyright 2022/1/19
 */
package com.leetcode.util;

import java.util.List;

public class TreeNodeUtil {

    public static <E> void inorder(TreeNode<E> node, List<E> res) {
        if (node == null)
            return;

        inorder(node.left, res);
        res.add(node.val);
        inorder(node.right, res);
    }

}
