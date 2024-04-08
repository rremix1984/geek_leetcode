/**
 * @copyright 2020 lonch
 */
package com.lonch;

import com.lonch.util.DoubleLinked;
import org.junit.Test;
import static java.lang.Math.pow;

/**
    [DOUBLELINKED] |
    (简单)
    NO.7 双向链表的定义，范型定义。
    根据二叉树高度，生成满二叉树
 */
public class NO7_DoubleLinked {

    @Test
    public void test() {
        int height = 4;
        int length = (int) pow(2, height) - 1;
        String[] arr = new String[length];
        for (int i = 0; i < length; i++)
            arr[i] = "" + i;

        DoubleLinked<String> res = cLink(arr, 0);
        setParents(res, null);
        res.printDoubleLinked();
    }

    public DoubleLinked<String> cLink(String[] arr, int idx) {
        // 2024/4/8 NO.1
        if (idx >= arr.length || idx < 0)
            return null;

        DoubleLinked<String> root = new DoubleLinked<>(arr[idx]);
        DoubleLinked<String> left = cLink(arr, 2 * idx + 1);
        DoubleLinked<String> right = cLink(arr, 2 * idx + 2);
        root.setLeft(left);
        root.setRight(right);
        return root;
    }

    public void setParents(DoubleLinked<String> node, DoubleLinked<String> parent) {
        if (node == null)
            return;
        node.setParent(parent);
        setParents(node.getLeft(), node);
        setParents(node.getRight(), node);
    }

}

















/*
// 设置 各个子的 data
public DoubleLinked<String> createDoubleLinked(String[] arr, int index) {
    if (index < arr.length && index >= 0) {
        DoubleLinked<String> root = new DoubleLinked<>();
        root.setData(arr[index]);
        root.setLeft(createDoubleLinked(arr, 2 * index + 1));
        root.setRight(createDoubleLinked(arr, 2 * index + 2));
        return root;
    } else {
        return null;
    }
}


// 填充父节点
public void setParents(DoubleLinked<String> node, DoubleLinked<String> parent) {
    if (node != null) {
        node.setParent(parent);
        if (node.getLeft() != null)
            setParents(node.getLeft(), node);

        if (node.getRight() != null)
            setParents(node.getRight(), node);
    }
}
*/