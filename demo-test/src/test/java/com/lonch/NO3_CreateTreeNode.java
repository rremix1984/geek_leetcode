package com.lonch;

import com.lonch.util.TreeNode;
import org.junit.Test;

import static java.lang.Math.pow;
import static org.junit.Assert.assertEquals;

public class NO3_CreateTreeNode<E> {

    @Test
    public void test() {
        String[] arr = new String[(int) (pow(2, 4) - 1)];
        for (int i = 0; i < arr.length; i++)
            arr[i] = i + "";

        assertEquals("[0, [1, [3, [7, [8], [4, [9, [10]], " +
                        "[2, [5, [11, [12], [6, [13, [14]]]",
                new NO3_CreateTreeNode<>().cTree(arr, 0).toString());
    }

    public TreeNode cTree(String[] arr, int index) {

        return null;
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