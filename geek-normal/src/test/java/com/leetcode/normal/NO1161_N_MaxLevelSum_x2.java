/**
 * copyright 2022/1/19
 */
package com.leetcode.normal;

import com.leetcode.util.TreeNode;
import org.junit.Test;
import static com.leetcode.util.MathUtils.cTree;

/**
    (中等)
    1161. 最大层内元素和
        给你一个二叉树的根节点 root。设根节点位于二叉树的第 1 层，
        而根节点的子节点位于第 2 层，依此类推。
        请返回层内元素之和 最大 的那几层（可能只有一层）的层号，
        并返回其中 最小 的那个。
    示例 1：
        输入：root = [1,7,0,7,-8,null,null]
        输出：2
        解释：
        第 1 层各元素之和为 1，
        第 2 层各元素之和为 7 + 0 = 7，
        第 3 层各元素之和为 7 + -8 = -1，
        所以我们返回第 2 层的层号，它的层内元素之和最大。
    示例 2：
        输入：root = [989,null,10250,98693,-89388,null,null,null,-32127]
        输出：2
    提示：
        树中的节点数在 [1, 104]范围内
        -105 <= Node.val <= 105

    方法一：深度优先搜索
        我们可以采用深度优先搜索来遍历这棵二叉树，递归的同时记录当前的层号。
    相比哈希表，这里我们采用效率更高的动态数组来维护每一层的元素之和，如果当
    前层号达到了数组的长度，则将节点元素添加到数组末尾，否则更新对应层号的元
    素之和。然后遍历数组，找到元素之和最大，且层号最小的元素。
*/
public class NO1161_N_MaxLevelSum_x2 {

    @Test
    public void test() {
        assert 2 == maxLevelSum(cTree(1, 7, 0, 7, -8, null, null));
        assert 2 == maxLevelSum(cTree(989, null, 10250, 98693, -89388, null, null, null, -32127));
    }

    public int maxLevelSum(TreeNode root) {
        int level = 0;
        return level;
    }

}
















/**
// 方法1：
public int maxLevelSum(TreeNode root) {
    // 每一层的总和数组
    List<Integer> sum = new ArrayList<>();

    // 深度遍历
    dfs(sum, root, 0);

    int level = 0;
    for (int i = 0; i < sum.size(); i++)
        if (sum.get(i) > sum.get(level))
            level = i;

    // 层号从 1 开始
    return level + 1;
}

private void dfs(List<Integer> sum, TreeNode node, int level) {
    // level = 0 的时候，直接 add 就可以了
    if (level == sum.size())
        sum.add(node.val);
    else
        sum.set(level, sum.get(level) + node.val);

    if (node.left != null)
        dfs(sum, node.left, level + 1);

    if (node.right != null)
        dfs(sum, node.right, level + 1);
}
*/