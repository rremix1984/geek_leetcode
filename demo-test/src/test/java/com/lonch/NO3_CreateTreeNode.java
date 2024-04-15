/**
 * @copyright 2020 lonch
 */
package com.lonch;

import com.lonch.util.Node;
import com.lonch.util.TreeNode;
import org.junit.Test;

import static com.lonch.util.Node.printTree;
import static java.lang.Math.pow;
import static org.junit.Assert.assertEquals;

/**
    [TREENODE] |||
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
        Node node = null;
        assertEquals("[0, " +
                        "[1, [3, [7, [8], [4, [9, [10]], " +
                        "[2, [5, [11, [12], [6, [13, [14]]]",
                node.toString());
        // 2024/4/8  NO.1 没思路，能做出来
        // 2024/4/9  NO.2 没思路，看答案做出来了，题不难
        // 2024/4/14 NO.3 做出来了，有点瑕疵

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