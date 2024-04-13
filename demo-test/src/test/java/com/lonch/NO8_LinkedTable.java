/**
 * @copyright lonch
 */
package com.lonch;

import com.lonch.util.LinkedTable;
import org.junit.Test;
import java.util.Deque;
import java.util.LinkedList;
import static java.lang.Math.pow;

/**
    [LINKEDTABLE] |
    (简单)
    NO.7 双向链表里添加第三个指针，变成三向链表。
 */
@SuppressWarnings("all")
public class NO8_LinkedTable {

    @Test
    public void test() {
        createFullTree(3).printLinkedTable();
    }

    public LinkedTable<Integer> createFullTree(int depth) {
        // 2024/4/8 NO.1 没思路，看答案看懂了
        // 2024/4/9 NO.2 有思路
        if (depth <= 0)
            return null;

        // TODO 能做出来的 4月18日面试，加油吧💪🏻

        return null;
    }

}

















/*
public LinkedTable<Integer> createFullTree(int depth) {
    if (depth <= 0)
        return null;

    int start = (int) pow(2, depth);
    LinkedTable<Integer> root = new LinkedTable<>();
    setParent(root, depth - 1);
    setVal(root, start);
    return root;
}

public void setVal(LinkedTable<Integer> root, int start) {
    Deque<LinkedTable<Integer>> queue = new LinkedList<>();
    queue.offer(root);
    int i = 1;
    while (!queue.isEmpty() && i < start) {
        LinkedTable<Integer> node = queue.poll();
        node.setValue(i++);

        if (node.left != null)
            queue.offer(node.left);

        if (node.right != null)
            queue.offer(node.right);
    }
}

public static void setParent(LinkedTable<Integer> root, int depth) {
    if (depth <= 0)
        return;

    LinkedTable<Integer> left = new LinkedTable<>();
    LinkedTable<Integer> right = new LinkedTable<>();

    left.setParent(root);
    right.setParent(root);

    root.left = left;
    root.right = right;

    setParent(root.left, depth - 1);
    setParent(root.right, depth - 1);
}
*/