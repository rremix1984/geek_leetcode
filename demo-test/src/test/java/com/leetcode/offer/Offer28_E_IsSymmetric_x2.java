/**
 * copyright 2022/1/19
 */
package com.leetcode.offer;

import com.leetcode.util.TreeNode;
import org.junit.Test;

/**
    (简单)
    剑指 Offer 28. 对称的二叉树
        请实现一个函数，用来判断一棵二叉树是不是对称的。如果一棵二叉树和它的镜像一样，那么它是对称的。
        例如，二叉树 [1,2,2,3,4,4,3] 是对称的。
                1
               / \
              2   2
             / \ / \
            3  4 4  3
        但是下面这个 [1,2,2,null,3,null,3] 则不是镜像对称的:
                1
               / \
              2   2
               \   \
               3    3
    示例 1：
        输入：root = [1, 2, 2, 3, 4, 4, 3]
        输出：true
    示例 2：
        输入：root = [1, 2, 2, null, 3, null, 3]
        输出：false
*/
public class Offer28_E_IsSymmetric_x2 {

    @Test
    public void test() {
        assert isSymmetric(null);
        assert isSymmetric(new TreeNode(1,
                new TreeNode(2,
                        3,4), new TreeNode(2,
                                                4,3)));
        assert !isSymmetric(new TreeNode(1,
                new TreeNode(2,
                    null,3), new TreeNode(2,
                                            null,3)));
    }

    public boolean isSymmetric(TreeNode root) {
        if (root == null)
            return true;

        return true;
    }

}






















/**
// 方法1：递归法
public boolean isSymmetric(TreeNode root) {
    if (root == null)
        return false;
    return call(root.left, root.right);
}

private boolean call(TreeNode left, TreeNode right) {
    if (left == null && right == null)
        return true;

    if (left == null || right == null || left.val != right.val)
        return false;

    return call(left.left, right.right) && call(right.left, left.right);
}


// 方法2：迭代法
public boolean isSymmetric(TreeNode root) {
    if (root == null)
        return true;

    Deque<TreeNode> queue = new LinkedList<>();
    queue.offer(root.left);
    queue.offer(root.right);

    while (queue.size() > 0) {
        TreeNode left = queue.poll();
        TreeNode right = queue.poll();

        if (left == null && right == null)
            continue;

        if (left == null || right == null || left.val != right.val)
            return false;

        queue.offer(left.left);
        queue.offer(right.right);

        queue.offer(right.left);
        queue.offer(right.left);
    }
    return true;
}
*/