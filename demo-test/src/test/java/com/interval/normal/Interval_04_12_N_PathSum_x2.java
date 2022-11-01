/**
 * copyright 2022/1/19
 */
package com.interval.normal;

import com.leetcode.util.TreeNode;
import org.junit.Test;
import static com.leetcode.util.MathUtils.cTree;

/**
    (中等)
    面试题 04.12. 求和路径
        给定一棵二叉树，其中每个节点都含有一个整数数值(该值或正或负)。
        设计一个算法，打印节点数值总和等于某个给定值的所有路径的数量。
        注意，路径不一定非得从二叉树的根节点或叶节点开始或结束，但是其方向必须向下(只能从父节点指向子节点方向)。
    示例:
        给定如下二叉树，以及目标和 sum = 22，
                5
               / \
              4   8
             /   / \
            11  13  4
           /  \    / \
          7    2  5   1
        返回: 3
        解释：和为 22 的路径有：{5, 4, 11, 2},  {5, 8, 4, 5},  {4, 11, 7}
*/
public class Interval_04_12_N_PathSum_x2 {

    @Test
    public void test() {
        assert 3 == pathSum(cTree(5, 4, 8, 11, null, 13, 4, 7, 2, null, null, 5, 1), 22);
    }

    public int pathSum(TreeNode root, int sum) {
        return 0;
    }

}
















/**
// 方法1：
public int pathSum(TreeNode root, int sum) {
    if (root == null)
        return 0;

    int ret = rootSum(root, sum);
    ret += pathSum(root.left, sum);
    ret += pathSum(root.right, sum);
    return ret;
}

public int rootSum(TreeNode root, int sum) {
    if (root == null)
        return 0;

    int ret = 0;
    if (root.val == sum)
        ret++;

    ret += rootSum(root.left, sum - root.val);
    ret += rootSum(root.right, sum - root.val);
    return ret;
}
*/