/**
 * @copyright lonch
 */
package com.lonch;

import com.lonch.util.LinkedTable;
import org.junit.Test;
import java.util.Deque;
import java.util.LinkedList;
import static com.lonch.util.LinkedTable.printLinkedTable;

/**
    [LINKEDTABLE] |
    (简单)
    NO.7 双向链表里添加第三个指针，变成三向链表。
 */
public class NO8_LinkedTable {

    @Test
    public void test() {
        int[] arr = new int[15];
        for (int i = 0; i < arr.length; i++)
            arr[i] = i + 1;

        LinkedTable<Integer> fullTree = createFullTree(3, arr);
        printLinkedTable(fullTree);
    }

    public LinkedTable<Integer> createFullTree(int depth, int[] arr) {
        // 2024/4/8 NO.1 没思路，看答案看懂了
        if (depth <= 0)
            return null;

        // TODO
        LinkedTable<Integer> table = new LinkedTable<>();
        setParent(table, depth - 1);
        setVal(table, arr);
        return table;
    }

    public void setVal(LinkedTable<Integer> node, int[] value) {
        Deque<LinkedTable<Integer>> queue = new LinkedList<>();
        queue.offer(node);
        int i = 0;
        while (!queue.isEmpty() && i < value.length) {
            LinkedTable<Integer> cur = queue.poll();
            cur.setValue(value[i]);
            if (cur.left != null)
                queue.offer(cur.left);

            if (cur.right != null)
                queue.offer(cur.right);

            i++;
        }
    }

    public static void setParent(LinkedTable<Integer> parent, int depth) {
        if (depth <= 0)
            return;

        LinkedTable<Integer> left = new LinkedTable<>();
        LinkedTable<Integer> right = new LinkedTable<>();

        left.setParent(parent);
        right.setParent(parent);

        parent.left = left;
        parent.right = right;

        setParent(parent.left, depth - 1);
        setParent(parent.right, depth - 1);
    }

    /*static class LinkedTable<T> {
        private T value;
        private LinkedTable<T> father;
        private LinkedTable<T> leftChild;
        private LinkedTable<T> rightChild;
    }*/

}