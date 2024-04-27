/**
 * copyright 2022/1/19
 */
package com.leetcode.util;

import java.util.PriorityQueue;

import static java.lang.System.out;

public class Test {

    public static void main(String[] args) {
        PriorityQueue pq = new PriorityQueue();
        pq.add(0);
        pq.add(1);
        pq.add(2);
        pq.add(3);
        pq.add(5);
        pq.add(6);
        pq.add(9);
        pq.add(8);
        pq.add(7);
        pq.add(4);
        while(!pq.isEmpty()) {
            out.println(pq.remove());
        }
    }

}
