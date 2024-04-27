package com.interview.common;

import lombok.Getter;
import lombok.Setter;

import static java.lang.System.out;

@Setter
@Getter
public class Node<T> {

    private final T value;
    private Node<T> next;

    public Node(T value) {
        this.value = value;
        this.next = null;
    }

    public static <T> void printLinkedList(Node<T> head) {
        while (head != null) {
            out.print(head.getValue());
            out.print(" ");
            head = head.getNext();
        }
        out.println();
    }

}
