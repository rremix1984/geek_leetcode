/**
 * copyright 2022/1/19
 */
package com.leetcode;

import com.leetcode.util.TreeNode;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static com.leetcode.util.MathUtils.cTree;

/**
    (中等)
    106. 从中序与后序遍历序列构造二叉树
        给定两个整数数组 inorder 和 postorder ，其中 inorder 是二叉树的中序遍历，
        postorder 是同一棵树的后序遍历，请你构造并返回这颗 二叉树 。
    示例 1:
        输入：inorder = [9, 3, 15, 20, 7], postorder = [9, 15, 7, 20, 3]
        输出：[3, 9, 20, null, null, 15, 7]
    示例 2:
        输入：inorder = [-1], postorder = [-1]
        输出：[-1]
    提示:
        1 <= inorder.length <= 3000
        postorder.length == inorder.length
        -3000 <= inorder[i], postorder[i] <= 3000
        inorder 和 postorder 都由 不同 的值组成
        postorder 中每一个值都在 inorder 中
        inorder 保证是树的中序遍历
        postorder 保证是树的后序遍历
*/
public class NO106_N_BuildTree {

    @Test
    public void test() {
        assert cTree(3, 9, 20, null, null, 15, 7).equals(
                buildTree(new int[]{9, 3, 15, 20, 7}, new int[]{9, 15, 7, 20, 3}));
        assert cTree(-1).equals(
                buildTree(new int[]{-1}, new int[]{-1}));
    }

    public TreeNode buildTree(int[] inorder, int[] postorder) {
        Map<Integer,Integer> memo = new HashMap<>();
        for (int i = 0; i < inorder.length; i++)
            memo.put(inorder[i], i);

        return buildTree(memo, postorder,
                    0,inorder.length - 1,
                0,postorder.length - 1);
    }

    public TreeNode buildTree(Map<Integer,Integer> memo, int[] post, int inStart, int inEnd, int postStart, int postEnd) {
        if(inEnd < inStart || postEnd < postStart)
            return null;

        int rootVal = post[postEnd];
        int ri = memo.get(rootVal);

        TreeNode node = new TreeNode(rootVal);
        node.left = buildTree(memo, post,
                inStart, ri - 1,
                postStart, postStart + ri - inStart - 1);
        node.right = buildTree(memo, post,
                ri + 1, inEnd,
                postStart + ri - inStart, postEnd - 1);
        return node;
    }

}
















/**
// 方法1：
int post_idx;
int[] postorder;
int[] inorder;
Map<Integer, Integer> idx_map = new HashMap<Integer, Integer>();

public TreeNode helper(int in_left, int in_right) {
    // 如果这里没有节点构造二叉树了，就结束
    if (in_left > in_right) {
        return null;
    }

    // 选择 post_idx 位置的元素作为当前子树根节点
    int root_val = postorder[post_idx];
    TreeNode root = new TreeNode(root_val);

    // 根据 root 所在位置分成左右两棵子树
    int index = idx_map.get(root_val);

    // 下标减一
    post_idx--;
    // 构造右子树
    root.right = helper(index + 1, in_right);
    // 构造左子树
    root.left = helper(in_left, index - 1);
    return root;
}

public TreeNode buildTree(int[] inorder, int[] postorder) {
    this.postorder = postorder;
    this.inorder = inorder;
    // 从后序遍历的最后一个元素开始
    post_idx = postorder.length - 1;

    // 建立（元素，下标）键值对的哈希表
    int idx = 0;
    for (Integer val : inorder) {
        idx_map.put(val, idx++);
    }

    return helper(0, inorder.length - 1);
}


// 方法2：
public TreeNode buildTree(int[] inorder, int[] postorder) {
    Map<Integer,Integer> memo = new HashMap<>();
    for(int i = 0;i < inorder.length; i++)
        memo.put(inorder[i], i);

    return buildTree(memo, postorder,
            0,inorder.length - 1,
            0,postorder.length - 1);
}

public TreeNode buildTree(Map<Integer,Integer> memo, int[] post, int inStart, int inEnd, int postStart, int postEnd) {
    if(inEnd < inStart || postEnd < postStart) return null;

    int val = post[postEnd];
    int ri = memo.get(val);

    TreeNode node = new TreeNode(val);
    node.left = buildTree(memo, post, inStart, ri - 1, postStart, postStart + ri - inStart - 1);
    node.right = buildTree(memo, post, ri + 1, inEnd, postStart + ri - inStart, postEnd - 1);
    return node;
}
*/