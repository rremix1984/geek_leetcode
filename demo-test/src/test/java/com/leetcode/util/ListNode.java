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

    @SuppressWarnings("all")
    public ListNode next(ListNode next) {
        this.next = next;
        return this;
    }

    public ListNode() {
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
}