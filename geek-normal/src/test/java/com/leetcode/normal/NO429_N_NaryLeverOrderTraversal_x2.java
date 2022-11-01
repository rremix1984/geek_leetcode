/**
 * copyright 2022/1/19
 */
package com.leetcode.normal;

import com.leetcode.util.Node;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.leetcode.util.MathUtils.getArray;
import static org.junit.Assert.assertEquals;

/**
    (中等)
    429. N 叉树的层序遍历
    给定一个 N 叉树，返回其节点值的层序遍历。（即从左到右，逐层遍历）。
    树的序列化输入是用层序遍历，每组子节点都由 null 值分隔（参见示例）。

    示例 1：
        输入：root = [1,null,3,2,4,null,5,6]
        输出：[[1],[3,2,4],[5,6]]
*/
public class NO429_N_NaryLeverOrderTraversal_x2 {

    @Test
    public void test() {
        assertEquals(getArray(new int[][]{{1}, {3, 2, 4}, {5, 6}}),
            levelOrder(new Node(1,
                    new Node(3,
            new Node(5), new Node(6)), new Node(2), new Node(4))));//[[1], [3, 2, 4], [5, 6]]
    }

    public List<List<Integer>> levelOrder(Node root) {
        List<List<Integer>> ans = new ArrayList<>();
        return ans;
    }

}














/**
// 方法1：
public List<List<Integer>> levelOrder(Node root) {
    List<List<Integer>> ans = new ArrayList<>();
    if (root == null)
        return ans;

    Deque<Node> queue = new LinkedList<>();
    queue.offer(root);
    while (!queue.isEmpty()) {
        List<Integer> level = new ArrayList<>();
        int cnt = queue.size();
        for (int i = 0; i < cnt; i++) {
            Node cur = queue.poll();
            level.add(cur.val);
            for (Node child : cur.children)
                queue.offer(child);
        }
        ans.add(level);
    }
    return ans;
}
*/