package com;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class Node {

    public int val;
    public Node next;

    public Node() {}

    public Node(int _val) {
        val = _val;
    }

    public Node(int _val, Node _next) {
        val = _val;
        next = _next;
    }

    public static Node cNode(int i, int i1, int i2) {
        Node n3 = new Node(i2);
        Node n = new Node(i, new Node(i1, n3));
        n3.next = n;
        return n;
    }

    public static Node cNode(int i) {
        Node n = new Node(i);
        n.next = n;
        return n;
    }

    public static String printNode(Node node) {
        StringBuilder sb = new StringBuilder();
        Set<Integer> set = new HashSet<>();
        while (node != null) {
            if (set.add(node.val)) {
                sb.append(node.val + "\t");
                node = node.next;
            } else {
                break;
            }
        }
        return sb.toString();
    }

    public Node detectCycle(Node head) {
        Node fast = head;
        Node slow = head;
        while (slow != null && fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            // 快慢指针的处理只能判断是否有环,并不能判断出在哪里出现的环
            if (fast == slow) {
                //循环链表的head  如果head和slow中的某一个节点出现相同
                //代表在链表的该条直线上,出现了环
                while (head != slow) {
                    head = head.next;
                    slow = slow.next;
                }
                return slow;
            }
        }
        return null;
    }

}