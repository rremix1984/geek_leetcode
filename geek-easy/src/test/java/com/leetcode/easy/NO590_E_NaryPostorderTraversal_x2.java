/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import com.leetcode.util.Node;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import static com.leetcode.util.LogUtil.info;
import static com.leetcode.util.MathUtils.getArray;
import static org.junit.Assert.assertEquals;

/**
    （简单）
    590. N 叉树的后序遍历
    给定一个 n 叉树的根节点 root ，返回 其节点值的 后序遍历 。
    n 叉树 在输入中按层序遍历进行序列化表示，每组子节点由空值 null 分隔（请参见示例）。
    示例 1：
        输入：root = [1, null, 3, 2, 4, null, 5, 6]
        输出：[5, 6, 3, 2, 4, 1]
*/
public class NO590_E_NaryPostorderTraversal_x2 {

    @Test
    public void test() {
        Assert.assertEquals(getArray(5, 6, 3, 2, 4, 1),
            postorder(new Node(1,
                new Node(3,
                        new Node(5), new Node(6)), new Node(2), new Node(4))));
    }

    public List<Integer> postorder(Node root) {
        List<Integer> res = new ArrayList<>();
        return res;
    }

}

















/**
// 方法1：
public List<Integer> postorder(Node root) {
    List<Integer> res = new ArrayList<>();
    if (root == null)
        return res;

    Stack<Node> stack = new Stack<>();
    stack.push(root);

    while (!stack.isEmpty()) {
        Node node = stack.pop();
        res.add(0, node.val);
        for (Node item : node.children)
            stack.push(item);
    }
    return res;
}
*/