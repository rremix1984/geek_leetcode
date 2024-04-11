package com.leetcode.util;

import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 多叉树
 *
 * @author wangxiaozhe
 */
@SuppressWarnings("all")
@NoArgsConstructor
public class Node<E> {

    // val
    public E val;

    // children
    public List<Node<E>> children;

    public Node(E val) {
        this.val = val;
        this.children = new ArrayList<>();
    }

    public Node(E... vals) {
        this.val = vals[0];
        this.children = new ArrayList<>();
        for (int i = 1; i < vals.length; i++) {
            E t = vals[i];
            this.children.add(new Node(t));
        }
    }

    public Node(E val, Node... nodes) {
        this.val = val;
        this.children = Arrays.asList(nodes);
    }

    public Node(E val, List<Node<E>> children) {
        this.val = val;
        this.children = children;
    }
}
