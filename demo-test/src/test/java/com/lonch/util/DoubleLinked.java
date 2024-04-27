/**
 * @copyright wxz
 */
package com.lonch.util;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static java.lang.System.out;

@SuppressWarnings("unused")
@Setter
@Getter
@NoArgsConstructor
public class DoubleLinked<E> {

    public E data;

    public DoubleLinked<E> parent;

    public DoubleLinked<E> left;

    public DoubleLinked<E> right;

    public DoubleLinked(E data) {
        this.data = data;
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

        out.println(prefix + "├── " + node.data);

        if (node.left != null)
            printDoubleLinkedHelper(node.left, prefix + "│   ");

        if (node.right != null)
            printDoubleLinkedHelper(node.right, prefix + "    ");
    }

}
