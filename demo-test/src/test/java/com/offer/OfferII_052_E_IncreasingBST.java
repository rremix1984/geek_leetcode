/**
 * copyright 2022/1/19
 */
package com.offer;

import com.leetcode.util.TreeNode;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static com.leetcode.util.MathUtils.createFullTree;
import static com.leetcode.util.MathUtils.inorder;

/**
    (简单)
    剑指 Offer II 052. 展平二叉搜索树
        给你一棵二叉搜索树，请 按中序遍历 将其重新排列为一棵递增顺序搜索树，使树中最左边的节点成为树的根节点，并且每个节点没有左子节点，只有一个右子节点。
    示例 1：
        输入：root = [5,3,6,2,4,null,8,1,null,null,null,7,9]
        输出：[1,null,2,null,3,null,4,null,5,null,6,null,7,null,8,null,9]
    示例 2：
        输入：root = [5,1,7]
        输出：[1,null,5,null,7]
*/
public class OfferII_052_E_IncreasingBST {

    @Test
    public void test() {
        assert createFullTree(1, null, 5, null, null, null, 7).equals(increasingBST(createFullTree(5, 1, 7)));
    }

    public TreeNode increasingBST(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        inorder(root, res);
        TreeNode dummy = new TreeNode(-1);
        TreeNode cur = dummy;
        for (int c : res) {
            cur.right = new TreeNode(c);
            cur = cur.right;
        }
        return dummy.right;
    }

}



















/**
// 方法1：
private TreeNode resNode;

public TreeNode increasingBST(TreeNode root) {
    TreeNode dummyNode = new TreeNode(-1);
    resNode = dummyNode;
    inorder(root);
    return dummyNode.right;
}

public void inorder(TreeNode node) {
    if (node == null) {
        return;
    }
    inorder(node.left);

    // 在中序遍历的过程中修改节点指向
    resNode.right = node;
    node.left = null;
    resNode = node;

    inorder(node.right);
}

// 方法2：中序遍历
public TreeNode increasingBST(TreeNode root) {
    List<Integer> res = new ArrayList<>();

    // 二叉搜索树，中序遍历就是向右展开的
    inorder(root, res);

    TreeNode dummyNode = new TreeNode(-1);
    TreeNode cur = dummyNode;
    for (int v : res) {
        cur.right = new TreeNode(v);
        cur = cur.right;
    }
    return dummyNode.right;
}
*/