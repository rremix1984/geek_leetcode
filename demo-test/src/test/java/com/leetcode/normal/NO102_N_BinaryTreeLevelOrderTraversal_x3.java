/**
 * copyright 2022/1/19
 */
package com.leetcode.normal;

import com.leetcode.util.TreeNode;
import org.junit.Test;
import java.util.*;
import static com.leetcode.util.LogUtil.info;
import static com.leetcode.util.MathUtils.createFullTree;
import static com.leetcode.util.MathUtils.getArray;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
    (中等)
    102. 二叉树的层序遍历
        给你二叉树的根节点 root ，返回其节点值的 层序遍历 。
        （即逐层地，从左到右访问所有节点）。
    示例 1：
        输入：root = {3, 9, 20, null, null, 15, 7}
        输出：[[3], [9, 20], [15, 7]]
    示例 2：
        输入：root = {1}
        输出：[[1]]
    示例 3：
        输入：root = {}
        输出：[]
*/
public class NO102_N_BinaryTreeLevelOrderTraversal_x3 {

    @Test
    public void test() {
        assertEquals(getArray(new int[][]{{1}, {2, 3}, {4, 5}}),
                levelOrder(createFullTree(1, 2, 3, 4, null, null, 5)));
        assertEquals(getArray(new int[][]{{3}, {9, 20}, {15, 7}}),
                levelOrder(createFullTree(3, 9, 20, null, null, 15, 7)));
        assertEquals(getArray(new int[][]{{1}}),
                levelOrder(createFullTree(1)));// [1]
//        assertEquals(getArray(new int[][]{{0}}),
//                levelOrder(new TreeNode()));// []
    }

    // 方法2：递归法
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        return res;
    }

}

















/**
// 方法1 层序遍历（BFS）
public List<List<Integer>> levelOrder(TreeNode root) {
    List<List<Integer>> ret = new ArrayList<>();
    if (root == null)
        return ret;

    Deque<TreeNode> queue = new LinkedList<>();
    queue.offer(root);

    while (!queue.isEmpty()) {
        List<Integer> level = new ArrayList<>();
        int size = queue.size();
        for (int i = 1; i <= size; i++) {
            TreeNode node = queue.poll();
            level.add(node.val);
            if (node.left != null)
                queue.offer(node.left);

            if (node.right != null)
                queue.offer(node.right);
        }
        ret.add(level);
    }
    return ret;
}

// 方法2：递归法
public List<List<Integer>> levelOrder(TreeNode root) {
    if (root == null)
        return new ArrayList<>();

    // 用来存放最终结果
    List<List<Integer>> res = new ArrayList<>();
    dfs(1, root, res);
    return res;
}

public void dfs(int index, TreeNode root, List<List<Integer>> res) {
    // 这一层第一个元素
    // 假设res是[ [1],[2,3] ]， index是3，就再插入一个空list放到res中
    if (res.size() < index)
        res.add(new ArrayList());

    // 将当前节点的值加入到res中，index代表当前层，假设index是3，节点值是99
    // res是[ [1],[2,3] [4] ]，加入后res就变为 [ [1],[2,3] [4,99] ]
    res.get(index-1).add(root.val);

    // 递归的处理左子树，右子树，同时将层数index+1
    if (root.left != null)
        dfs(index + 1, root.left, res);

    if (root.right != null)
        dfs(index + 1, root.right, res);
}
*/