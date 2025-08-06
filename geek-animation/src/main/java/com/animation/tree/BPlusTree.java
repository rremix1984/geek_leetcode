package com.animation.tree;

import java.util.ArrayList;
import java.util.List;

public class BPlusTree<K extends Comparable<K>, V> {

    protected Node<K> root;
    protected int order; // B+树的阶数

    public BPlusTree(int order) {
        if (order < 3) {
            throw new IllegalArgumentException("Order must be at least 3.");
        }
        this.order = order;
        this.root = new LeafNode<>();
    }

    // 插入操作
    public void insert(K key, V value) {
        LeafNode<K, V> leaf = findLeafNode(key);
        leaf.insert(key, value);

        if (leaf.isFull(order)) {
            splitLeafNode(leaf);
        }
    }

    // 查找值
    public V search(K key) {
        LeafNode<K, V> leaf = findLeafNode(key);
        int loc = leaf.keys.indexOf(key);
        return loc != -1 ? leaf.values.get(loc) : null;
    }

    // 删除操作
    public void delete(K key) {
        LeafNode<K, V> leaf = findLeafNode(key);
        if (leaf.delete(key)) {
            // 如果需要，处理下溢
            handleUnderflow(leaf);
        }
    }

    // 查找叶子节点
    @SuppressWarnings("unchecked")
    private LeafNode<K, V> findLeafNode(K key) {
        Node<K> node = this.root;
        while (!node.isLeaf()) {
            InternalNode<K> internalNode = (InternalNode<K>) node;
            node = internalNode.getChild(key);
        }
        return (LeafNode<K, V>) node;
    }

    // 节点基类
    protected abstract static class Node<K extends Comparable<K>> {
        protected List<K> keys;
        protected Node<K> parent;

        public abstract boolean isFull(int order);

        public Node() {
            this.keys = new ArrayList<>();
        }

        public abstract boolean isLeaf();

        public abstract int size();
    }

    // 内部节点
    protected static class InternalNode<K extends Comparable<K>> extends Node<K> {
        protected List<Node<K>> children;

        public InternalNode() {
            super();
            this.children = new ArrayList<>();
        }

        public Node<K> getChild(K key) {
            int loc = 0;
            while (loc < keys.size() && key.compareTo(keys.get(loc)) >= 0) {
                loc++;
            }
            return children.get(loc);
        }

        @Override
        public boolean isLeaf() {
            return false;
        }

        @Override
        public int size() {
            return children.size();
        }

        @Override
        public boolean isFull(int order) {
            return this.children.size() > order;
        }
    }

    // 叶子节点
    protected static class LeafNode<K extends Comparable<K>, V> extends Node<K> {
        protected List<V> values;
        protected LeafNode<K, V> next;

        public LeafNode() {
            super();
            this.values = new ArrayList<>();
        }

        public void insert(K key, V value) {
            int loc = 0;
            while (loc < keys.size() && keys.get(loc).compareTo(key) < 0) {
                loc++;
            }
            keys.add(loc, key);
            values.add(loc, value);
        }

        public boolean delete(K key) {
            int loc = keys.indexOf(key);
            if (loc == -1) {
                return false;
            }
            keys.remove(loc);
            values.remove(loc);
            return true;
        }

        @Override
        public boolean isLeaf() {
            return true;
        }

        @Override
        public int size() {
            return values.size();
        }

        @Override
        public boolean isFull(int order) {
            return this.keys.size() >= order;
        }
    }

    // 分裂叶子节点
    private void splitLeafNode(LeafNode<K, V> leaf) {
        int midIndex = leaf.size() / 2;

        LeafNode<K, V> newLeaf = new LeafNode<>();
        newLeaf.keys.addAll(leaf.keys.subList(midIndex, leaf.keys.size()));
        newLeaf.values.addAll(leaf.values.subList(midIndex, leaf.values.size()));

        leaf.keys.subList(midIndex, leaf.keys.size()).clear();
        leaf.values.subList(midIndex, leaf.values.size()).clear();

        newLeaf.next = leaf.next;
        leaf.next = newLeaf;

        split(leaf, newLeaf, newLeaf.keys.get(0));
    }

    // 递归分裂
    private void split(Node<K> left, Node<K> right, K key) {
        InternalNode<K> parent = (InternalNode<K>) left.parent;

        if (parent == null) {
            parent = new InternalNode<>();
            this.root = parent;
            parent.children.add(left);
            left.parent = parent;
        }

        int loc = 0;
        while (loc < parent.keys.size() && parent.keys.get(loc).compareTo(key) < 0) {
            loc++;
        }

        parent.keys.add(loc, key);
        parent.children.add(loc + 1, right);
        right.parent = parent;

        if (parent.isFull(order)) {
            splitInternalNode(parent);
        }
    }

    // 分裂内部节点
    private void splitInternalNode(InternalNode<K> node) {
        int midIndex = node.keys.size() / 2;
        K midKey = node.keys.get(midIndex);

        InternalNode<K> newNode = new InternalNode<>();
        newNode.keys.addAll(node.keys.subList(midIndex + 1, node.keys.size()));
        newNode.children.addAll(node.children.subList(midIndex + 1, node.children.size()));

        for (Node<K> child : newNode.children) {
            child.parent = newNode;
        }

        node.keys.subList(midIndex, node.keys.size()).clear();
        node.children.subList(midIndex + 1, node.children.size()).clear();

        split(node, newNode, midKey);
    }

    private void handleUnderflow(Node<K> node) {
        // 实现下溢处理逻辑，包括合并和重新分配
    }
}