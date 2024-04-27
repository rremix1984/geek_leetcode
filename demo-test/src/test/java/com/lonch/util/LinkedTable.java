package com.lonch.util;

import lombok.Getter;
import lombok.Setter;
import java.util.LinkedList;
import java.util.Queue;

import static java.lang.System.out;

@Setter
@Getter
@SuppressWarnings("unused")
public class LinkedTable<T> {

    public T value;
    public LinkedTable<T> parent;
    public LinkedTable<T> left;
    public LinkedTable<T> right;

    public LinkedTable() {
    }

    public LinkedTable<T> setParent(LinkedTable<T> parent) {
        this.parent = parent;
        return this;
    }

    public static void printLinkedTable(LinkedTable<Integer> root) {
        printLinkedTableHelper(root, "", true);
    }

    public void printLinkedTable() {
        printLinkedTableHelper( "", true);
    }


    private static void printLinkedTableHelper(LinkedTable<Integer> node, String prefix, boolean isTail) {
        if (node == null)
            return;

        if (node.getParent() != null) {
            String pointer = isTail ? "└── " : "├── ";
            out.println(prefix + pointer + node.getValue());
        } else
            out.println(node.getValue());

        if (node.getRight() != null)
            printLinkedTableHelper(node.getRight(), prefix + (isTail ? "    " : "│   "), false);

        if (node.getLeft() != null)
            printLinkedTableHelper(node.getLeft(), prefix + (isTail ? "    " : "│   "), true);
    }

    private void printLinkedTableHelper(String prefix, boolean isTail) {
        if (getParent() != null) {
            String pointer = isTail ? "└── " : "├── ";
            out.println(prefix + pointer + getValue());
        } else {
            out.println(getValue());
        }

        if (getRight() != null)
            printLinkedTableHelper(prefix + (isTail ? "    " : "│   "), false);

        if (getLeft() != null)
            printLinkedTableHelper(prefix + (isTail ? "    " : "│   "), true);
    }

    private void printNodeValue(LinkedTable<Integer> node, int depth) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < depth; i++)
            sb.append("  "); // 缩进两个空格

        sb.append(node.getValue());
        out.println(sb);
    }
}