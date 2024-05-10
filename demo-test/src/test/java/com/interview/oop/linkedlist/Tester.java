package com.interview.oop.linkedlist;

import static com.interview.oop.linkedlist.LinkedList.newEmptyList;
import static java.lang.System.out;

public class Tester {

    public static void main(String[] args) {
        LinkedList<Integer> res = newEmptyList();
        for (int i = 0; i < 100; i++) {
            res.add(i);
            out.println(i);
        }

        LinkedList<String> list = newEmptyList();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 100; i++)
            list.add(sb.append("a").toString());

        list.forEach(out::println);
    }

}
