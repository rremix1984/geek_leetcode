package com.interview.oop.linkedlist;

import static java.lang.System.out;

public class Tester {

    public static void main(String[] args) {
        LinkedList<Integer> list = LinkedList.newEmptyList();
        for (int i = 0; i < 100; i++) {
            list.add(i);
        }

        for (Integer value : list) {
            out.println(value);
        }

        LinkedList<String> stringList = LinkedList.newEmptyList();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 100; i++) {
            sb.append("a");
            stringList.add(sb.toString());
        }

        for (String value : stringList) {
            out.println(value);
        }
    }
}
