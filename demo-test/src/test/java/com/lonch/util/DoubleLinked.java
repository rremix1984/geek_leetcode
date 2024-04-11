/**
 * @copyright wxz
 */
package com.lonch.util;

import lombok.Setter;

@SuppressWarnings("unused")
@Setter
public class DoubleLinked<E> {

    public E data;

    public DoubleLinked<E> parent;

    public DoubleLinked<E> left;

    public DoubleLinked<E> right;
    // 4 个属性get set 代码省略

    public DoubleLinked() {

    }

    public DoubleLinked(E data) {
        this.data = data;
    }

    public E getData() {
        return data;
    }

    public DoubleLinked<E> getParent() {
        return parent;
    }

    public DoubleLinked<E> getLeft() {
        return left;
    }

    public DoubleLinked<E> getRight() {
        return right;
    }

    public void printDoubleLinked(DoubleLinked<E> head) {
        printDoubleLinkedHelper(head, "");
    }

    public void printDoubleLinked() {
        printDoubleLinkedHelper(this, "");
    }

    private void printDoubleLinkedHelper(DoubleLinked<E> node, String prefix) {
        if (node == null)
            return;

        System.out.println(prefix + "├── " + node.data);

        if (node.left != null)
            printDoubleLinkedHelper(node.left, prefix + "│   ");

        if (node.right != null)
            printDoubleLinkedHelper(node.right, prefix + "    ");
    }

}
