/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import com.leetcode.util.TreeNode;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static com.leetcode.util.MathUtils.cTree;
import static com.leetcode.util.TreeNodeUtil.inorder;

/**
    [TREE]
    (简单)
    897. 递增顺序搜索树
        给你一棵二叉搜索树的 root ，请你 按中序遍历 将其重新排列为一棵递增顺序搜索树，使树中最左边的节点成为树的根节点，并且每个节点没有左子节点，只有一个右子节点。
    示例 1：
        输入：root = [5, 3, 6, 2, 4, null, 8, 1, null, null, null, 7, 9]
        输出：[1, null, 2, null, 3, null, 4, null,
             5, null, 6, null, 7, null, 8, null, 9]
    示例 2：
        输入：root = [5, 1, 7]
        输出：[1, null, 5, null, 7]
    提示：
        树中节点数的取值范围是 [1, 100]
        0 <= Node.val <= 1000
*/
public class NO897_E_IncreasingBST_x2 {

    @Test
    public void test() {
        assert new TreeNode(1,
            null, new TreeNode(2, 
                    null, new TreeNode(3, 
                            null, new TreeNode(4, 
                                    null, new TreeNode(5, 
                                        null, new TreeNode(6, 
                                                null, new TreeNode(7, 
                                                        null, new TreeNode(8, 
                                                                        null, 9)))))))).equals(
            increasingBST(cTree(5, 3, 6, 2, 4, null, 8, 1,
                            null, null, null, null, null, 7, 9)));
        assert cTree(1, null, 5, null, null, null, 7).equals(
            increasingBST(cTree(5, 1, 7)));
    }

    public TreeNode<Integer> increasingBST(TreeNode<Integer> root) {
        return null;
    }

}














/**
// 方法1：二叉树的中序遍历就是有序的
public TreeNode increasingBST(TreeNode root) {
    List<Integer> res = new ArrayList<>();
    inorder(root, res);

    TreeNode dummy = new TreeNode(-1);
    TreeNode cur = dummy;
    for (int value : res) {
        cur.right = new TreeNode(value);
        cur = cur.right;
    }
    return dummy.right;
}
*/