/**
 * copyright 2022/1/19
 */
package com.leetcode.util;

import java.util.List;

public class TreeNodeUtil {

    public static void inorder(TreeNode node, List<Integer> res) {
        if (node == null)
            return;

        inorder(node.left, res);
        res.add(node.val);
        inorder(node.right, res);
    }

}
