/**
 * copyright 2022/1/19
 */
package com.leetcode;

import com.leetcode.util.TreeNode;
import org.junit.Test;

/**
    (中等)
    剑指 Offer 26. 树的子结构
        输入两棵二叉树A和B，判断B是不是A的子结构。(约定空树不是任意一个树的子结构)
        B是A的子结构， 即 A 中有出现和B相同的结构和节点值。
    例如:
        给定的树 A:
                3
               / \
              4   5
             / \
            1   2
        给定的树 B：
                4
               /
              1
        返回 true，因为 B 与 A 的一个子树拥有相同的结构和节点值。
    示例 1：
        输入：A = [1,2,3], B = [3,1]
        输出：false
    示例 2：
        输入：A = [3,4,5,1,2], B = [4,1]
        输出：true
*/
public class Offer_26_N_IsSubStructure {

    @Test
    public void test() {
        assert !isSubStructure(new TreeNode(1,
                                    2, 3), new TreeNode(3,
                                                            1));
        assert isSubStructure(new TreeNode(3,
                        new TreeNode(4,
                                1, 2), 5), new TreeNode(4,
                                                                1));
    }

    public boolean isSubStructure(TreeNode A, TreeNode B) {
        return false;
    }

}

















/**
// 方法1：
public boolean isSubStructure(TreeNode A, TreeNode B) {
    if (A == null || B == null)
        return false;

    //先从根节点判断B是不是A的子结构，如果不是在分别从左右两个子树判断，
    //只要有一个为true，就说明B是A的子结构
    return recur(A, B)
            || isSubStructure(A.left, B)
            || isSubStructure(A.right, B);
}

boolean recur(TreeNode A, TreeNode B) {
    //这里如果B为空，说明B已经访问完了，确定是A的子结构
    if (B == null)
        return true;

    //如果B不为空A为空，或者这两个节点值不同，说明B树不是
    //A的子结构，直接返回false
    if (A == null || A.val != B.val)
        return false;

    //当前节点比较完之后还要继续判断左右子节点
    return recur(A.left, B.left) && recur(A.right, B.right);
}
*/