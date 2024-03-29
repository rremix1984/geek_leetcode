package com.lonch.util;

import java.util.LinkedList;
import java.util.Queue;

public class LinkedTable<T> {
    private T value;
    private LinkedTable father;
    private LinkedTable leftChild;
    private LinkedTable rightChild;

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public LinkedTable getFather() {
        return father;
    }

    public LinkedTable setFather(LinkedTable father) {
        this.father = father;
        return this;
    }

    public LinkedTable getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(LinkedTable leftChild) {
        this.leftChild = leftChild;
    }

    public LinkedTable getRightChild() {
        return rightChild;
    }

    public void setRightChild(LinkedTable rightChild) {
        this.rightChild = rightChild;
    }

    public static LinkedTable createFullTree(int depth) {
        if (depth <= 0) {
            return null;
        }
        LinkedTable linkedTable = new LinkedTable();
// todo, set CHIldren
        setChildren(linkedTable, depth - 1);
        return linkedTable;
    }

    public static void setValue(LinkedTable node, int[] value) {
        Queue<LinkedTable> queue = new LinkedList<>();
        queue.offer(node);
        int i = 0;
        while (!queue.isEmpty() && i < value.length) {
            LinkedTable currentNode = queue.poll();
            currentNode.setValue(value[i++]);
            if (currentNode.leftChild != null) {
                queue.offer(currentNode.leftChild);
            }
            if (currentNode.rightChild != null) {
                queue.offer(currentNode.rightChild);
            }
        }
    }

    public static void setChildren(LinkedTable father, int depth) {
        if (depth > 0) {
            father.leftChild = new LinkedTable().setFather(father);
            father.rightChild = new LinkedTable().setFather(father);
            setChildren(father.leftChild, depth - 1);
            setChildren(father.rightChild, depth - 1);
        }
    }

    public static void main(String[] args) {
        int[] arr = new int[15];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i + 1;
        }
        LinkedTable fullTree = createFullTree(3);
        setValue(fullTree, arr);
        System.out.println(fullTree);
    }
}