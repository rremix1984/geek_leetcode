/**
 * copyright 2022/1/19
 */
package com.offer.easy;

import com.leetcode.util.TreeNode;
import org.junit.Test;
import static com.leetcode.util.MathUtils.createFullTree;

/**
    (简单)
    剑指 Offer II 056. 二叉搜索树中两个节点之和
        给定一个二叉搜索树的 根节点 root 和一个整数 k , 请判断该二叉搜索树中是否存在两个节点它们的值之和等于 k 。假设二叉搜索树中节点的值均唯一。
    示例 1：
        输入: root = [8, 6, 10, 5, 7, 9, 11], k = 12
        输出: true
        解释: 节点 5 和节点 7 之和等于 12
    示例 2：
        输入: root = [8, 6, 10, 5, 7, 9, 11], k = 22
        输出: false
        解释: 不存在两个节点值之和为 22 的节点
*/
public class OfferII_056_E_FindTarget_x2 {

    @Test
    public void test() {
        TreeNode node = createFullTree(8, 6, 10, 5, 7, 9, 11);
        assert findTarget(node, 12);
        assert !findTarget(node, 22);
        assert findTarget(node, 16);
    }

    public boolean findTarget(TreeNode root, int k) {
        return false;
    }

}
















/**
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


// 方法2：
public boolean findTarget(TreeNode root, int k) {
    Set<Integer> set = new HashSet<Integer>();
    Queue<TreeNode> queue = new ArrayDeque<TreeNode>();
    queue.offer(root);
    while (!queue.isEmpty()) {
        TreeNode node = queue.poll();
        if (set.contains(k - node.val)) {
            return true;
        }
        set.add(node.val);
        if (node.left != null) {
            queue.offer(node.left);
        }
        if (node.right != null) {
            queue.offer(node.right);
        }
    }
    return false;
}

// 方法3：
List<Integer> list = new ArrayList<Integer>();

public boolean findTarget(TreeNode root, int k) {
    inorderTraversal(root);
    int left = 0, right = list.size() - 1;
    while (left < right) {
        if (list.get(left) + list.get(right) == k) {
            return true;
        }
        if (list.get(left) + list.get(right) < k) {
            left++;
        } else {
            right--;
        }
    }
    return false;
}

public void inorderTraversal(TreeNode node) {
    if (node == null) {
        return;
    }
    inorderTraversal(node.left);
    list.add(node.val);
    inorderTraversal(node.right);
}


// 方法4：
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
*/