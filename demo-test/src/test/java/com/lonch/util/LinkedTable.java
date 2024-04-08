package com.lonch.util;

import com.lonch.NO8_LinkedTable;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.Queue;

public class LinkedTable<T> {
    public T value;
    public LinkedTable<T> parent;
    public LinkedTable<T> left;
    public LinkedTable<T> right;

    public LinkedTable() {

    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public LinkedTable<T> getParent() {
        return parent;
    }

    public LinkedTable<T> setParent(LinkedTable<T> parent) {
        this.parent = parent;
        return this;
    }

    public LinkedTable<T> getLeft() {
        return left;
    }

    public void setLeft(LinkedTable<T> left) {
        this.left = left;
    }

    public LinkedTable<T> getRight() {
        return right;
    }

    public void setRight(LinkedTable<T> right) {
        this.right = right;
    }

    public static void printLinkedTable(LinkedTable<Integer> root) {
        printLinkedTableHelper(root, "", true);
    }

    public void printLinkedTable() {
        printLinkedTableHelper( "", true);
    }


    private static void printLinkedTableHelper(LinkedTable<Integer> node, String prefix, boolean isTail) {
        if (node == null) {
            return;
        }

        if (node.getParent() != null) {
            String pointer = isTail ? "└── " : "├── ";
            System.out.println(prefix + pointer + node.getValue());
        } else {
            System.out.println(node.getValue());
        }

        if (node.getRight() != null) {
            printLinkedTableHelper(node.getRight(), prefix + (isTail ? "    " : "│   "), false);
        }

        if (node.getLeft() != null) {
            printLinkedTableHelper(node.getLeft(), prefix + (isTail ? "    " : "│   "), true);
        }
    }

    private void printLinkedTableHelper(String prefix, boolean isTail) {
        if (getParent() != null) {
            String pointer = isTail ? "└── " : "├── ";
            System.out.println(prefix + pointer + getValue());
        } else {
            System.out.println(getValue());
        }

        if (getRight() != null) {
            printLinkedTableHelper(prefix + (isTail ? "    " : "│   "), false);
        }

        if (getLeft() != null) {
            printLinkedTableHelper(prefix + (isTail ? "    " : "│   "), true);
        }
    }

    private void printNodeValue(LinkedTable<Integer> node, int depth) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            sb.append("  "); // 缩进两个空格
        }
        sb.append(node.getValue());
        System.out.println(sb);
    }
}