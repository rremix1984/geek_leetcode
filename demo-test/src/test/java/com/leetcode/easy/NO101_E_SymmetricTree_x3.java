/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import com.leetcode.util.TreeNode;
import org.junit.Test;

/**
    (简单)
    101. 对称二叉树
        给你一个二叉树的根节点 root ， 检查它是否轴对称。
    示例 1：
        输入：root = [1, 2, 2, 3, 4, 4, 3]
        输出：true
    示例 2：
        输入：root = [1, 2, 2, null, 3, null, 3]
        输出：false
*/
public class NO101_E_SymmetricTree_x3 {

    @Test
    public void test() {
        assert isSymmetric(new TreeNode(1,
                new TreeNode(2,
                        3, 4), new TreeNode(2,
                                                4, 3)));
        assert !isSymmetric(new TreeNode(1,
                new TreeNode(2,
                        null, 3), new TreeNode(2,
                                                null, 3)));
        assert !isSymmetric(new TreeNode(1,
                new TreeNode(2,
            new TreeNode(3,
                    4, 5), 3), new TreeNode(2,
                                                    null, 3)));
    }

    public boolean isSymmetric(TreeNode root) {
        if (root == null)
            return true;
        return true;
    }

}


















/**
// 方法1：递归
public boolean isSymmetric(TreeNode root) {
    if (root == null)
        return true;

    return call(root.left, root.right);
}

private boolean call(TreeNode left, TreeNode right) {
    if (left == null && right == null)
        return true;

    if (left == null || right == null || left.val != right.val)
        return false;

    return call(left.left, right.right)
            && call(left.right, right.left);
}

// 方法2：迭代法
public boolean isSymmetric(TreeNode root) {
    if (root == null ||
        (root.left == null && root.right == null))
        return true;

    //用队列保存节点
    Deque<TreeNode> queue = new LinkedList<>();

    //将根节点的左右孩子放到队列中
    queue.offer(root.left);
    queue.offer(root.right);

    while (queue.size() > 0) {
        //从队列中取出两个节点，再比较这两个节点
        TreeNode left = queue.poll();
        TreeNode right = queue.poll();

        //如果两个节点都为空就继续循环，两者有一个为空就返回false
        if (left == null && right == null)
            continue;

        if (left == null || right == null || left.val != right.val)
            return false;

        //将左节点的左孩子， 右节点的右孩子放入队列
        queue.offer(left.left);
        queue.offer(right.right);

        //将左节点的右孩子，右节点的左孩子放入队列
        queue.offer(left.right);
        queue.offer(right.left);
    }
    return true;
}
*/