package com.lonch.util;

public class TernaryTreeNode<T> {
    public T value;
    public TernaryTreeNode<T> left;
    public TernaryTreeNode<T> middle;
    public TernaryTreeNode<T> right;

    public TernaryTreeNode(T value) {
        this.value = value;
        this.left = null;
        this.middle = null;
        this.right = null;
    }

}

