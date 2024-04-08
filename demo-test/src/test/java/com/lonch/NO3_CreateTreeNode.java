/**
 * @copyright 2020 lonch
 */
package com.lonch;

import com.lonch.util.TreeNode;
import org.junit.Test;
import static java.lang.Math.pow;
import static org.junit.Assert.assertEquals;

/**
  NO.3 指定深度（depth）从1到n 层序遍历赋值
                            0

                    /                   \
                1                           2
          /         \                   /       \
        3            4               5            6
     /   \         /    \         /    \        /   \
   7      8       9      10      11     12     13     14

 */
public class NO3_CreateTreeNode<E> {

    @Test
    public void test() {
        int depth = 4;
        Integer[] arr = new Integer[(int) (pow(2, depth) - 1)];
        for (int i = 0; i < arr.length; i++)
            arr[i] = i;

        assertEquals("[0, " +
                        "[1, [3, [7, [8], [4, [9, [10]], " +
                        "[2, [5, [11, [12], [6, [13, [14]]]",
                cTree(arr, 0).toString());
    }

    public TreeNode<Integer> cTree(Integer[] arr, int index) {
        if (index > arr.length - 1 || index < 0)
            return null;

        TreeNode<Integer> root = new TreeNode<>(arr[index]);
        root.setLeft(cTree(arr, 2 * index + 1));
        root.setRight(cTree(arr, 2 * index + 2));
        return root;
    }

}























/*
public TreeNode<E> cTree(E[] arr, int index) {
    if (index > arr.length - 1 || index < 0)
        return null;

    TreeNode<E> root = new TreeNode<>();
    root.setValue(arr[index]);
    root.setLeft(cTree(arr, 2 * index + 1));
    root.setRight(cTree(arr, 2 * index + 2));
    return root;
}
*/