/**
 * @copyright 2020 lonch
 */
package com.lonch;

import com.lonch.util.TreeNode;
import org.junit.Test;
import static java.lang.Math.pow;
import static org.junit.Assert.assertEquals;

/**
    [TREENODE] |
    (简单)
    NO.3 指定深度（depth）从 1 到 n 层序遍历赋值
                            0

                    /                   \
                1                           2
          /         \                   /       \
        3            4               5            6
     /   \         /    \         /    \        /   \
   7      8       9      10      11     12     13     14
 */
@SuppressWarnings("all")
public class NO3_CreateTreeNode {

    @Test
    public void test() {
        int depth = 4;
        assertEquals("[0, " +
                        "[1, [3, [7, [8], [4, [9, [10]], " +
                        "[2, [5, [11, [12], [6, [13, [14]]]",
                cTree(depth, 0).toString());
    }

    public TreeNode<Integer> cTree(int depth, int index) {
        // 2024/4/8 NO.1 没思路，能做出来
        // TODO
        return null;
    }

}























/*
public TreeNode<Integer> cTree(int depth, int index) {
    if (index > pow(2, depth) - 2 || index < 0)
        return null;

    TreeNode<Integer> root = new TreeNode<>(index);
    root.setLeft(cTree(depth, 2 * index + 1));
    root.setRight(cTree(depth, 2 * index + 2));
    return root;
}
*/