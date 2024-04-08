package com.lonch.util;

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

    public void setData(E data) {
        this.data = data;
    }

    public DoubleLinked<E> getParent() {
        return parent;
    }

    public void setParent(DoubleLinked<E> parent) {
        this.parent = parent;
    }

    public DoubleLinked<E> getLeft() {
        return left;
    }

    public void setLeft(DoubleLinked<E> left) {
        this.left = left;
    }

    public DoubleLinked<E> getRight() {
        return right;
    }

    public void setRight(DoubleLinked<E> right) {
        this.right = right;
    }

    public void printDoubleLinked(DoubleLinked<E> head) {
        printDoubleLinkedHelper(head, "");
    }

    public void printDoubleLinked() {
        printDoubleLinkedHelper(this, "");
    }

    private void printDoubleLinkedHelper(DoubleLinked<E> node, String prefix) {
        if (node == null) {
            return;
        }

        System.out.println(prefix + "├── " + node.data);

        if (node.left != null) {
            printDoubleLinkedHelper(node.left, prefix + "│   ");
        }

        if (node.right != null) {
            printDoubleLinkedHelper(node.right, prefix + "    ");
        }
    }

}
