/**
 * copyright 2022/1/19
 */
package com.offer;

import com.leetcode.util.TreeNode;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static com.leetcode.util.MathUtils.cTree;
import static com.leetcode.util.MathUtils.getArray;
import static com.leetcode.util.SystemUtil.print;

/**
    [TREE] |
    (中等)
    剑指 Offer 34. 二叉树中和为某一值的路径
        给你二叉树的根节点root和一个整数目标和targetSum，
        找出所有从根节点到叶子节点路径总和等于给定目标和的路径。
        叶子节点是指没有子节点的节点。
    示例 1：
        输入：root = [5, 4, 8, 11, null, 13, 4, 7, 2,
                    null, null, null, null, 5, 1], targetSum = 22
        输出：[[5, 4, 11, 2], [5, 8, 4, 5]]

                           [5],
                    [4],          [8],
              [11],    null, 13,       [4],
          7,     [2],   null, null,[5],      1]

    示例 2：
        输入：root = [1, 2, 3], targetSum = 5
        输出：[]
    示例 3：
        输入：root = [1, 2], targetSum = 0
        输出：[]
    提示：
        树中节点总数在范围 [0, 5000] 内
        -1000 <= Node.val <= 1000
        -1000 <= targetSum <= 1000
*/
public class Offer_034_N_PathSum {

    @Test
    public void test() {
        assert getArray(new int[][]{{5, 4, 11, 2}, {5, 8, 4, 5}}).equals(
                pathSum(cTree(5, 4, 8, 11, null, 13, 4, 7, 2,
                                null, null, null, null, 5, 1),  22));
        assert getArray().equals(
                pathSum(cTree(1, 2, 3),  5));
        assert getArray().equals(
                pathSum(cTree(1, 2),  0));
    }

    public List<List<Integer>> pathSum(TreeNode root, int target) {
        // 2024/3/21 NO.1 没做出来，但看懂了
        // 2024/3/22 NO.2
        List<List<Integer>> ret = new ArrayList<>();
        ArrayList<Integer> list = new ArrayList<>();
        dfs(ret, list, root, target);
        return ret;
    }

    private void dfs(List<List<Integer>> ret, ArrayList<Integer> list,
                     TreeNode root, int target) {
        if (root == null)
            return;

        target -= root.val;
        list.add(root.val);

        if (target == 0 && root.left == null && root.right == null) {
            ret.add(new ArrayList<>(list));
        }

        dfs(ret, list, root.left, target);

        dfs(ret, list, root.right, target);

        list.remove(list.size() - 1);
    }

}


















/*
// 方法1：
public List<List<Integer>> pathSum(TreeNode root, int target) {
    List<List<Integer>> ret = new ArrayList<>();
    dfs(ret, new ArrayList<>(), root, target);
    return ret;
}

public void dfs(List<List<Integer>> ret, ArrayList<Integer> list,
                TreeNode root, int target) {
    if (root == null)
        return;

    list.add(root.val);
    target -= root.val;

    // 是叶节点且，减到0
    if (root.left == null
            && root.right == null && target == 0)
        ret.add(new ArrayList<>(list));

    dfs(ret, list, root.left, target);
    dfs(ret, list, root.right, target);

    list.remove(list.size() - 1);
}
*/