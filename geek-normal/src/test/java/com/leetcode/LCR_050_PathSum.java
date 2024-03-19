/**
 * copyright@2019/12/12 lcc
 */
package com.leetcode;

import com.leetcode.util.TreeNode;
import org.junit.Test;
import java.util.HashMap;
import java.util.Map;
import static com.leetcode.util.MathUtils.cTree;

/**
    [TREE]
    (中等)
    LCR.050 路径总和 III
    给定一个二叉树的根节点 root ，和一个整数 targetSum ，求该二叉树里节点值之和
    等于 targetSum 的 路径 的数目。
    路径 不需要从根节点开始，也不需要在叶子节点结束，但是路径方向必须是向下的
    （只能从父节点到子节点）。
    示例 1：
        输入：root = [10, 5, -3, 3, 2, null, 11, 3, -2, null, 1],
             targetSum = 8
        输出：3
        解释：和等于 8 的路径有 3 条，如图所示。
    示例 2：
        输入：root = [5, 4, 8, 11, null, 13, 4, 7, 2, null, null, 5, 1],
             targetSum = 22
        输出：3
    提示:
        二叉树的节点个数的范围是 [0,1000]
        -109 <= Node.val <= 109
        -1000 <= targetSum <= 1000
    Related Topics:树,深度优先搜索,二叉树
*/
public class LCR_050_PathSum {

    @Test
    public void test() {
        assert 3 == pathSum(
                cTree(10, 5, -3, 3, 2, null, 11, 3, -2, null, 1),
                8);
        assert 3 == pathSum(
                cTree(5, 4, 8, 11, null, 13, 4, 7, 2, null, null, 5, 1),
                22);
        assert 0 == pathSum(
                cTree(715827882, 715827882, null, 715827882, null, 1,
                        null, 715827882, null, 715827882, null, 715827882, null),
                -3);
    }

    public int pathSum(TreeNode root, int targetSum) {
        if(root == null)
            return 0;

        int ret = rootSum(root, targetSum);
        ret += pathSum(root.left, targetSum);
        ret += pathSum(root.right, targetSum);
        return ret;
    }

    public int rootSum(TreeNode root, int targetSum) {
        if(root == null)
            return 0;

        int ret = 0;
        if(root.val == targetSum)
            ret ++;

        ret += rootSum(root.left, targetSum - root.val);
        ret += rootSum(root.right, targetSum - root.val);
        return ret;
    }

}

















/*
// 方法1：
public int pathSum(TreeNode root, int targetSum) {
    if(root == null)
        return 0;

    int ret = rootSum(root, targetSum);
    ret += pathSum(root.left, targetSum);
    ret += pathSum(root.right, targetSum);
    return ret;
}

public int rootSum(TreeNode root, int targetSum) {
    if(root == null)
        return 0;

    int ret = 0;
    if(root.val == targetSum)
        ret ++;

    ret += rootSum(root.left, targetSum - root.val);
    ret += rootSum(root.right, targetSum - root.val);
    return ret;
}

// 方法2：
public int pathSum(TreeNode root, int targetSum) {
    Map<Long, Integer> sumCountMap = new HashMap<>();
    if (root == null)
        return 0;

    sumCountMap.put(0L, 1);
    return getCounts(sumCountMap, root, root.val, targetSum);
}

public int getCounts(Map<Long, Integer> sumCountMap, TreeNode node, long sum, int targetSum) {
    int count = sumCountMap.getOrDefault(sum - targetSum, 0);

    sumCountMap.put(sum, sumCountMap.getOrDefault(sum, 0) + 1);

    TreeNode left = node.left;
    TreeNode right = node.right;
    if (left != null)
        count += getCounts(sumCountMap, left, sum + left.val, targetSum);

    if (right != null)
        count += getCounts(sumCountMap, right, sum + right.val, targetSum);

    sumCountMap.put(sum, sumCountMap.get(sum) - 1);
    return count;
}
*/