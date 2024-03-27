/**
 * copyright 2022/1/19
 */
package com.offer.easy;

import com.leetcode.util.TreeNode;
import org.junit.Test;
import java.util.*;
import static com.leetcode.util.MathUtils.cTree;
import static com.leetcode.util.TreeNodeUtil.inorder;

/**
    [TREE] ||||
    (简单)
    剑指 Offer II 056. 二叉搜索树中两个节点之和
        给定一个二叉搜索树的根节点 root 和一个整数k, 请判断该二叉搜索树
        中是否存在【两个节点】它们的值之和等于 k。
        假设二叉搜索树中节点的值均唯一。
    示例 1：
        输入: root = [8, 6, 10, 5, 7, 9, 11], k = 12
        输出: true
        解释: 节点 5 和节点 7 之和等于 12

                    8
                6       10
            5     7   9    11

    示例 2：
        输入: root = [8, 6, 10, 5, 7, 9, 11], k = 22
        输出: false
        解释: 不存在两个节点值之和为 22 的节点

                      8
                6         10
            5      7   9      11
*/
public class OfferII_056_E_FindTarget {

    @Test
    public void test() {
        TreeNode node = cTree(8, 6, 10, 5, 7, 9, 11);
        assert  findTarget(node, 12);
        assert !findTarget(node, 22);
        assert  findTarget(node, 16);
    }

    public boolean findTarget(TreeNode root, int k) {
        // 2024/3/22 NO.1 递归法、二分查找 + 中序遍历
        // 2024/3/23 NO.2 一遍过
        // 2024/3/25 NO.3 没做出来
        // 2024/3/27 NO.4 中序遍历法，一遍过
        List<Integer> res = new ArrayList<>();

        return false;
    }

}
















/*
// 方法1：递归法
Set<Integer> set = new HashSet<>();
public boolean findTarget(TreeNode root, int k) {
    if (root == null)
        return false;

    if (set.contains(k - root.val))
        return true;

    set.add(root.val);
    return findTarget(root.left, k)
        || findTarget(root.right, k);
}

// 方法2：层序遍历
public boolean findTarget(TreeNode root, int k) {
    Set<Integer> set = new HashSet<Integer>();
    Queue<TreeNode> queue = new ArrayDeque<TreeNode>();
    queue.offer(root);
    while (!queue.isEmpty()) {
        TreeNode node = queue.poll();
        if (set.contains(k - node.val))
            return true;

        set.add(node.val);
        if (node.left != null)
            queue.offer(node.left);

        if (node.right != null)
            queue.offer(node.right);
    }
    return false;
}

// 方法3：中序遍历法
public boolean findTarget(TreeNode root, int k) {
    List<Integer> list = new ArrayList<>();
    inorder(list, root);
    int left = 0;
    int right = list.size() - 1;
    while (left < right) {
        if (list.get(left) + list.get(right) == k)
            return true;

        if (list.get(left) + list.get(right) < k)
            left++;
        else
            right--;
    }
    return false;
}

public void inorder(List<Integer> list, TreeNode node) {
    if (node == null) {
        return;
    }
    inorder(list, node.left);
    list.add(node.val);
    inorder(list, node.right);
}

// 方法4：二分查找
public boolean findTarget(TreeNode root, int k) {
    List<Integer> list = new ArrayList<>();
    inorder(root, list);
    int l = 0;
    int r = list.size() - 1;
    while (l < r)
        if (list.get(l) + list.get(r) > k)
            r--;
        else if (list.get(l) + list.get(r) < k)
            l++;
        else
            return true;
    return false;
}

// 方法3：
public boolean findTarget(TreeNode root, int k) {
    Set<Integer> set = new HashSet<>();
    return dfs(set, root, k);
}

private boolean dfs(Set<Integer> set,
                    TreeNode root, int k) {
    if (root == null)
        return false;

    if (set.contains(k - root.val))
        return true;

    set.add(root.val);
    return dfs(set, root.left, k)
            || dfs(set, root.right, k);
}
*/