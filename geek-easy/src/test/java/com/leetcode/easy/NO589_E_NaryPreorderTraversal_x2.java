/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import com.leetcode.util.Node;
import org.junit.Assert;
import org.junit.Test;
import java.util.*;
import static com.leetcode.util.LogUtil.info;
import static com.leetcode.util.MathUtils.getArray;
import static org.junit.Assert.assertEquals;

/**
    （简单）
    589. N 叉树的前序遍历
    给定一个 n 叉树的根节点  root ，返回 其节点值的 前序遍历。
    n 叉树 在输入中按层序遍历进行序列化表示，每组子节点由空值 null 分隔（请参见示例）。
    示例 1：
        输入：root = [1, null, 3, 2, 4, null, 5, 6]
        输出：[1, 3, 5, 6, 2, 4]
*/
public class NO589_E_NaryPreorderTraversal_x2 {

    @Test
    public void test() {
        Assert.assertEquals(getArray(1, 3, 5, 6, 2, 4),
            preorder(new Node(1,
                new Node(3,
        new Node(5), new Node(6)), new Node(2), new Node(4))));
    }

    List<Integer> res = new ArrayList<>();

    public List<Integer> preorder(Node root) {
        return res;
    }

}












/**
// 方案1
List<Integer> res = new ArrayList<>();
public List<Integer> preorder(Node root) {
    if(root==null)
        return new ArrayList();
    res.add(root.val);
    if (root.children!=null) {
        for (Node child : root.children)
            preorder(child);
    }
    return res;
}

// 方案2
public List<Integer> preorder(Node root) {
    List<Integer> res = new ArrayList<>();
    if (root == null)
        return res;
    Stack<Node> stack = new Stack<>();
    stack.push(root);
    while (!stack.isEmpty()) {
        Node node = stack.pop();
        res.add(node.val);
        for (int i = node.children.size() - 1; i >= 0; --i) {
            stack.push(node.children.get(i));
        }
    }
    return res;
}
*/