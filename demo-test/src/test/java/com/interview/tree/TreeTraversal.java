package com.interview.tree;

import static java.lang.System.out;

public class TreeTraversal {

    public void preOrder(TreeNode root) {
        if (root == null) {
            return;
        }
        out.print(root.getValue());
        preOrder(root.getLeft());
        preOrder(root.getRight());
    }

    public void inOrder(TreeNode root) {
        if (root == null) {
            return;
        }
        inOrder(root.getLeft());
        out.print(root.getValue());
        inOrder(root.getRight());
    }

    public void postOrder(TreeNode root) {
        if (root == null) {
            return;
        }
        postOrder(root.getLeft());
        postOrder(root.getRight());
        out.print(root.getValue());
    }

    public String postOrder(String preOrder, String inOrder) {
        if (preOrder.isEmpty()) {
            return "";
        }

        char rootValue = preOrder.charAt(0);
        int rootIndex = inOrder.indexOf(rootValue);

        return
                postOrder(
                        preOrder.substring(1, 1 + rootIndex),
                        inOrder.substring(0, rootIndex)) +
                        postOrder(
                                preOrder.substring(1 + rootIndex),
                                inOrder.substring(1 + rootIndex)) +
                        rootValue;
    }

    public static void main(String[] args) {
        TreeCreator creator = new TreeCreator();
        TreeTraversal traversal = new TreeTraversal();

        out.println("Sample tree traversal");
        out.println("=====");
        TreeNode sampleTree = creator.createSampleTree();
        traversal.preOrder(sampleTree);
        out.println();
        traversal.inOrder(sampleTree);
        out.println();
        traversal.postOrder(sampleTree);
        out.println();

        out.println("=====");
        out.println("Creating tree from preOrder and inOrder");
        out.println("=====");
        TreeNode tree = creator.createTree("ABDEGCF", "DBGEACF");
        traversal.postOrder(tree);
        out.println();
        traversal.postOrder(creator.createTree("", ""));
        out.println();
        traversal.postOrder(creator.createTree("A", "A"));
        out.println();
        traversal.postOrder(creator.createTree("AB", "BA"));
        out.println();

        out.println("=====");
        out.println("Generating postOrder directly");
        out.println("=====");
        out.println(
                traversal.postOrder("ABDEGCF", "DBGEACF"));
        out.println(
                traversal.postOrder("", ""));
        out.println(
                traversal.postOrder("A", "A"));
        out.println(
                traversal.postOrder("AB", "BA"));
    }
}
