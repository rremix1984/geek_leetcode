/**
 * copyright 2022/1/19
 */
package com.offer.normal;

import com.leetcode.util.TreeNode;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static com.leetcode.util.MathUtils.cTree;
import static com.leetcode.util.MathUtils.getArray;

/**
    (中等)
    剑指 Offer II 044. 二叉树每层的最大值
        给定一棵二叉树的根节点 root ，请找出该二叉树中每一层的最大值。
    示例1：
        输入: root = [1, 3, 2, 5, 3, null, 9]
        输出: [1, 3, 9]
        解释:
            1
           / \
          3   2
         / \   \
        5   3   9
    示例2：
        输入: root = [1, 2, 3]
        输出: [1, 3]
        解释:
           1
          / \
         2   3
    示例3：
        输入: root = [1]
        输出: [1]
    示例4：
        输入: root = [1, null, 2]
        输出: [1, 2]
        解释:
          1
           \
            2
    示例5：
        输入: root = []
        输出: []
*/
public class OfferII_044_N_LargestValues_x2 {

    @Test
    public void test() {
        assert getArray(1, 3, 9).equals(
            largestValues(cTree(1, 3, 2, 5, 3, null, 9)));
        assert getArray(1, 3).equals(
                largestValues(cTree(1, 2, 3)));
        assert getArray(1, 2).equals(
                largestValues(cTree(1, null, 2)));
        assert getArray(1).equals(
                largestValues(cTree(1)));
        assert largestValues(cTree(new Integer[]{})).isEmpty();
    }

    public List<Integer> largestValues(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        return res;
    }

}
















/**
// 方法1：层序遍历
public List<Integer> largestValues(TreeNode root) {
    List<Integer> res = new ArrayList<>();
    if (root == null)
        return res;
    Deque<TreeNode> queue = new LinkedList<>();
    queue.offer(root);
    while (!queue.isEmpty()) {
        int max = -1;
        int size = queue.size();
        while (size > 0) {
            TreeNode node = queue.poll();
            max = Math.max(max, node.val);

            if (node.left != null)
                queue.offer(node.left);

            if (node.right != null)
                queue.offer(node.right);

            size--;
        }
        res.add(max);
    }
    return res;
}

// 方法2：dfs
public List<Integer> largestValues(TreeNode root) {
    if (root == null)
        return new ArrayList<Integer>();

    List<Integer> res = new ArrayList<Integer>();
    dfs(res, root, 0);
    return res;
}

public void dfs(List<Integer> res, TreeNode root, int curHeight) {
    if (curHeight == res.size())
        res.add(root.val);
     else
        res.set(curHeight, Math.max(res.get(curHeight), root.val));

    if (root.left != null)
        dfs(res, root.left, curHeight + 1);

    if (root.right != null)
        dfs(res, root.right, curHeight + 1);
}
*/