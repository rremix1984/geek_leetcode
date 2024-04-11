/**
 * @copyright wxz
 */
package com.leetcode.util;

import java.util.Arrays;

/**
 * listNode
 *
 * @author wangxiaozhe
 */
@SuppressWarnings("unused")
public class ListNode<E> {
    public E val;
    public ListNode<E> next;

    public static <E> E toInt(ListNode<E> node, E defaultValue) {
        if (node == null)
            return defaultValue;
        return node.val;
    }

    public static int toInt(ListNode<Integer> node) {
        if (node == null) {
            return 0;
        }
        return node.val;
    }

    @SafeVarargs
    public static <E> ListNode<E> newCycle(E... vals) {
        int max = vals.length - 1;
        ListNode<E> head = new ListNode<>(Arrays.copyOf(vals, vals.length - 1));
        ListNode<E> cur = head;
        ListNode<E> pre = null;
        for (int i = 0; i < vals.length - 1; i++) {
            if (cur == null)
                continue;

            if (i == vals.length - 2) {
                cur.next = pre;
            } else {
                if (cur.val == vals[max])
                    pre = cur;
                cur = cur.next;
            }
        }
        return head;
    }

    public static <E> ListNode<E> reverse(ListNode<E> head) {
        ListNode<E> pre = null;
        ListNode<E> cur = head;
        while (cur != null) {
            ListNode<E> next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        return pre;
    }

    public static <E> ListNode<E> reverse(ListNode<E> head, ListNode<E> tail) {
        ListNode<E> pre = null;
        while (head != tail) {
            ListNode<E> next = head.next;
            head.next = pre;
            pre = head;
            head = next;
        }
        return pre;
    }

    @SuppressWarnings("all")
    public ListNode<E> next(ListNode<E> next) {
        this.next = next;
        return this;
    }

    public ListNode() {

    }

    public static <E> void assertNodeEquals(ListNode<E> node, int... arr) {
        assert new ListNode<>(arr).equals(node);
    }

    @SafeVarargs
    public ListNode(E... vals) {
        if (vals != null) {
            this.val = vals[0];
            ListNode<E> cur = this;
            for (int i = 1; i < vals.length; i++) {
                cur.next = new ListNode<>(vals[i]);
                cur = cur.next;
            }
        }
    }

    public ListNode(E val, ListNode<E> next) {
        this.val = val;
        this.next = next;
    }

    public boolean equals(ListNode<E> listNode) {
        ListNode<E> p1 = this;
        ListNode<E> p2 = listNode;
        if (p2 == null)
            return false;

        while (p1 != null && p2 != null && p1.val == p2.val) {
            p1 = p1.next;
            p2 = p2.next;
        }
        return p1 == null && p2 == null;
    }

    @Override
    public String toString() {
        ListNode<E> cur = this;
        StringBuilder sb = new StringBuilder();
        while (cur.next != null) {
            sb.append(cur.val).append(" -> ");
            cur = cur.next;
        }
        return sb.append(cur.val).append(" -> null").toString();
    }

    public static <E> int getListNodeLength(ListNode<E> head) {
        int length = 0;
        while (head != null) {
            length++;
            head = head.next;
        }
        return length;
    }

}