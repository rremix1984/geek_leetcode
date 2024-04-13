package com.lonch;

import com.lonch.util.Node;

import static com.lonch.util.Node.printTree;

public class NO15_TreeNodeTest {

    public static void main(String[] args) {
        Node<Integer> head = new Node<>();
        Integer headVal = 1;
        Node<Integer> root1 = genRecursiveTree(head, 4, headVal); // root
        printTree(root1);
        Node<Integer> root2 = genRecursiveTree(head, 4, headVal); // root
        printTree(root2);
        Node<Integer> root3 = genRecursiveTree(head, 4, headVal); // root
        Node<Integer> root4 = genRecursiveTree(head, 4, headVal); // root
        if (root1 != null) {
            root1.parent = root3;
        }
        if (root2 != null) {
            root2.parent = root1;
        }
        if (root3 != null) {
            root3.parent = root4;
        }
        if (root4 != null) {
            root4.parent = root2;
        }
        Node<Integer> start = null;
        if (root1 != null) {
            start = root1.right.left.right;
        }
        recursivePrint(start);
    }

    //满二叉树
    public static Node<Integer> genRecursiveTree(Node<Integer> father, int size, Integer val){
        //先构造一颗正常的二叉树，然后遍历二叉树，将father节点给子节点写进去
        if (size == 0)
            return null;

        Node<Integer> cur = new Node<>();
        if (father.data != null)
            cur.parent = father;

        cur.data = val;
        //左子的值为父亲x2， 右子的值为父亲x2+1
        cur.left = genRecursiveTree(cur, size - 1, val * 2);
        cur.right = genRecursiveTree(cur, size - 1, val * 2 + 1);
        return cur;
    }

    //先沿着当前点把自己的所有子节点遍历完
    //然后向上一直走，一直找到本树的root（成环：说明是根节点）
    //沿途遇到的节点都采用向下遍历的方法把沿途节点的子节点都遍历到
    public static void recursiveChild(Node<Integer> node){
        //起始节点是任意一个节点 不能重复遍历
        if (node == null)
            return;

        recursiveChild(node.left);
        recursiveChild(node.right);
        System.out.print(node.data + " ");
    }

    //向上遍历
    public static void recursivePrint(Node<Integer> node){
        recursiveChild(node);
        while(node.parent != null && node.parent.parent != null ){
            Node<Integer> father = node.parent;
            if(node == father.right){
                System.out.print(father.data + " ");
                recursiveChild(father.left);
            }
            if(node == father.left){
                System.out.print(father.data + " ");
                recursiveChild(father.right);
            }
            if(node != father.left && node != father.right){ //说明本颗树遍历完成
                System.out.println();
                recursiveChild(father);
                node.parent = null;
            }
            node = father;
        }
    }

}
