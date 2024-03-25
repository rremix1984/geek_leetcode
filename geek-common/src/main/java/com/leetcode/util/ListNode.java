package com.leetcode.util;

import lombok.ToString;

import java.util.Arrays;

/**
 * listNode
 *
 * @author wangxiaozhe
 */
@ToString
public class ListNode {
    public int val;
    public ListNode next;

    public static int toInt(ListNode node, int defaultValue) {
        if (node == null) {
            return defaultValue;
        }
        return node.val;
    }

    public static int toInt(ListNode node) {
        if (node == null) {
            return 0;
        }
        return node.val;
    }

    public static ListNode newCycle(int... vals) {
        int max = vals.length - 1;
        ListNode head = new ListNode(Arrays.copyOf(vals, vals.length - 1));
        ListNode cur = head;
        ListNode pre = null;
        for (int i = 0; i < vals.length - 1; i++) {
            if (i == vals.length - 2) {
                cur.next = pre;
            } else {
                if (cur.val == vals[max]) {
                    pre = cur;
                }
                cur = cur.next;
            }
        }
        return head;
    }

    public static ListNode reverse(ListNode head) {
        if (head == null)
            return null;
        ListNode pre = null;
        ListNode cur = head;
        while (cur != null) {
            ListNode next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        return pre;
    }

    @SuppressWarnings("all")
    public ListNode next(ListNode next) {
        this.next = next;
        return this;
    }

    public ListNode() {

    }

    public static void assertNodeEquals(ListNode node, int... arr) {
        assert new ListNode(arr).equals(node);
    }

    public ListNode(int... vals) {
        if (vals != null) {
            this.val = vals[0];
            ListNode cur = this;
            for (int i = 1; i < vals.length; i++) {
                cur.next = new ListNode(vals[i]);
                cur = cur.next;
            }
        }
    }

    public ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }

    public boolean equals(ListNode listNode) {
        ListNode p1 = this;
        ListNode p2 = listNode;
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
        ListNode cur = this;
        StringBuilder sb = new StringBuilder();
        while (cur.next != null) {
            sb.append(cur.val).append(" -> ");
            cur = cur.next;
        }
        return sb.append(cur.val).append(" -> null").toString();
    }

    public static int getListNodeLength(ListNode head) {
        int length = 0;
        while (head != null) {
            length++;
            head = head.next;
        }
        return length;
    }

}