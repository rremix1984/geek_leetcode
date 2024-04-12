/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import com.leetcode.util.TreeNode;
import org.junit.Test;
import static com.leetcode.util.MathUtils.cTree;

/**
    [TREE]
    (简单)
    404. 左叶子之和
        给定二叉树的根节点 root ，返回所有左叶子之和。
    示例 1：
        输入: root = {3, 9, 20, null, null, 15, 7}
        输出: 24
        解释: 在这个二叉树中，有两个左叶子，分别是 9 和 15，所以返回 24
    示例 2:
        输入: root = {1}
        输出: 0
    提示:
        节点数在 [1, 1000] 范围内
        -1000 <= Node.val <= 1000
*/
public class NO404_E_SumOfLeftLeaves_x2 {

    @Test
    public void test() {
        assert 24 == sumOfLeftLeaves(cTree(3, 9, 20, null, null, 15, 7));
        assert 0 == sumOfLeftLeaves(cTree(1));
    }

    public int sumOfLeftLeaves(TreeNode<Integer> root) {
        int res = 0;
        return res;
    }

}



















/**
// 方法1：
public int sumOfLeftLeaves(TreeNode root) {
    if (root == null) {
        return 0;
    }

    Queue<TreeNode> queue = new LinkedList<TreeNode>();
    queue.offer(root);
    int ans = 0;
    while (!queue.isEmpty()) {
        TreeNode node = queue.poll();
        if (node.left != null) {
            if (isLeafNode(node.left)) {
                ans += node.left.val;
            } else {
                queue.offer(node.left);
            }
        }
        if (node.right != null) {
            if (!isLeafNode(node.right)) {
                queue.offer(node.right);
            }
        }
    }
    return ans;
}

public boolean isLeafNode(TreeNode node) {
    return node.left == null && node.right == null;
}


// 方法2：
public int sumOfLeftLeaves(TreeNode root) {
    if (root == null)
        return 0;

    return sumOfLeftLeaves(root.left)
            + sumOfLeftLeaves(root.right)
            + (root.left != null
                && root.left.left==null
                && root.left.right==null ? root.left.val : 0);
}


// 方法3：
public int sumOfLeftLeaves(TreeNode root) {
    return root != null ? dfs(root) : 0;
}

public int dfs(TreeNode node) {
    int ans = 0;
    if (node.left != null) {
        ans += isLeafNode(node.left) ? node.left.val : dfs(node.left);
    }
    if (node.right != null && !isLeafNode(node.right)) {
        ans += dfs(node.right);
    }
    return ans;
}

public boolean isLeafNode(TreeNode node) {
    return node.left == null && node.right == null;
}


// 方法4：
public int sumOfLeftLeaves(TreeNode root) {
    int res = 0;
    if (root == null)
        return res;

    // 左叶子
    if (root.left != null
            && root.left.left == null
            && root.left.right == null)
        res = root.left.val;

    return res
            + sumOfLeftLeaves(root.left)
            + sumOfLeftLeaves(root.right);
}
*/