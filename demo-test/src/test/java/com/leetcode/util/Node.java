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
public class Node {

    // val
    public int val;

    // children
    public List<Node> children;

    public Node(int val) {
        this.val = val;
        this.children = null;
    }

    public Node(int... vals) {
        this.val = vals[0];
        this.children = new ArrayList<>();
        for (int i=1;i<vals.length;i++) {
            int t = vals[i];
            this.children.add(new Node(t));
        }
    }

    public Node(int val, Node... nodes) {
        this.val = val;
        this.children = Arrays.asList(nodes);
    }

    public Node(int val, List<Node> children) {
        this.val = val;
        this.children = children;
    }
}
