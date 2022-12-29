/**
 * copyright 2022/1/19
 */
package com.leetcode.undo;

import com.leetcode.util.TreeNode;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static com.leetcode.util.MathUtils.cTree;

/**
    (中等)
    105. 从前序与中序遍历序列构造二叉树
        给定两个整数数组 preorder 和 inorder ，其中 preorder 是二叉树的先序遍历，
        inorder 是同一棵树的中序遍历，请构造二叉树并返回其根节点。
    示例 1:
        输入: preorder = [3,9,20,15,7], inorder = [9,3,15,20,7]
        输出: [3,9,20,null,null,15,7]
    示例 2:
        输入: preorder = [-1], inorder = [-1]
        输出: [-1]
    提示:
        1 <= preorder.length <= 3000
        inorder.length == preorder.length
        -3000 <= preorder[i], inorder[i] <= 3000
        preorder 和 inorder 均 无重复
        inorder 均出现在 preorder
        preorder 保证 为二叉树的前序遍历序列
        inorder 保证 为二叉树的中序遍历序列
 wxz
*/
public class NO105_N_BuildTree {

    @Test
    public void test() {
        assert cTree(3, 9, 20, null, null, 15, 7).equals(
            buildTree(new int[]{3, 9, 20, 15, 7},  new int[]{9, 3, 15, 20, 7}));
        assert cTree(-1).equals(
            buildTree(new int[]{-1},  new int[]{-1}));
    }

    public TreeNode myBuildTree(Map<Integer, Integer> map, int[] preorder,
                                int preLeft, int preRight, int inLeft) {
        if (preLeft > preRight)
            return null;

        // 前序遍历中的第一个节点就是根节点
        // 在中序遍历中定位根节点
        int inRootVal = map.get(preorder[preLeft]);

        // 先把根节点建立出来
        TreeNode root = new TreeNode(preorder[preLeft]);

        // 得到左子树中的节点数目
        int size_left_subtree = inRootVal - inLeft;

        // 递归地构造左子树，并连接到根节点
        // 先序遍历中「从 左边界+1 开始的 size_left_subtree」个元素就对应了中序遍历中「从 左边界 开始到 根节点定位-1」的元素
        root.left = myBuildTree(map, preorder,
                preLeft + 1, preLeft + size_left_subtree,
                inLeft);

        // 递归地构造右子树，并连接到根节点
        // 先序遍历中「从 左边界+1+左子树节点数目 开始到 右边界」的元素就对应了中序遍历中「从 根节点定位+1 到 右边界」的元素
        root.right = myBuildTree(map, preorder,
                preLeft + size_left_subtree + 1, preRight,
                inRootVal + 1);

        return root;
    }

    public TreeNode buildTree(int[] preorder, int[] inorder) {
        // 构造哈希映射，帮助我们快速定位根节点
        Map<Integer, Integer> map = new HashMap<>();

        // 根据中序遍历创建map映射
        for (int i = 0; i < inorder.length; i++)
            map.put(inorder[i], i);

        return myBuildTree(map, preorder,
                0, preorder.length - 1,
                0);
    }

}
