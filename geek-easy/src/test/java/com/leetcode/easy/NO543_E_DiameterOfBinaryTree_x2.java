/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import com.leetcode.util.TreeNode;
import org.junit.Test;
import static com.leetcode.util.MathUtils.cTree;
import static java.lang.Math.max;

/**
    [TREE]
    (简单)
    543. 二叉树的直径
        给定一棵二叉树，你需要计算它的直径长度。
        一棵二叉树的直径长度是任意两个结点路径长度中的最大值。
        这条路径可能穿过也可能不穿过根结点。
    示例: 给定二叉树
             1
            / \
           2   3
          / \
         4   5
    返回 3, 它的长度是路径 [4, 2, 1, 3] 或者 [5, 2, 1, 3]。
    方法一：深度优先搜索
        首先我们知道一条路径的长度为该路径经过的节点数减一，所以求直径（即求路径长度的最大值）等效于求路径经过节点数的最大值减一。
    而任意一条路径均可以被看作由某个节点为起点，从其左儿子和右儿子向下遍历的路径拼接得到。
    如图我们可以知道路径 [9, 4, 2, 5, 7, 8] 可以被看作以 22 为起点，从其左儿子向下遍历的路径 [2, 4, 9]
    和从其右儿子向下遍历的路径 [2, 5, 7, 8] 拼接得到。
    假设我们知道对于该节点的左儿子向下遍历经过最多的节点数 LL （即以左儿子为根的子树的深度）和其右儿子向下遍历
    经过最多的节点数 R （即以右儿子为根的子树的深度），那么以该节点为起点的路径经过节点数的最大值即为 L+R+1L+R+1 。
    我们记节点 node 为起点的路径经过节点数的最大值为d_node，那么二叉树的直径就是所有节点d_node的最大值减一。
    最后的算法流程为：我们定义一个递归函数 depth(node) 计算 d_node
    ，函数返回该节点为根的子树的深度。先递归调用左儿子和右儿子求得它们为根的子树的深度 LL 和 RR ，则该节点为根的子树的深度即为
    max(L,R)+1
    该节点的 d_node 值为 L+R+1 递归搜索每个节点并设一个全局变量 ans 记录 d_node 的最大值，最后返回 ans-1 即为树的直径。
*/
public class NO543_E_DiameterOfBinaryTree_x2 {

    @Test
    public void test() {
        assert 3 == diameterOfBinaryTree(
                cTree(1,2,3,4,5));
    }

    int ans = 0;
    public int diameterOfBinaryTree(TreeNode<Integer> root) {
        return ans;
    }

}



















/*
// 方法1：
int ans;
public int diameterOfBinaryTree(TreeNode root) {
    ans = 0;
    depth(root);
    return ans;
}

public int depth(TreeNode node) {
    if (node == null)
        return 0; // 访问到空节点了，返回0

    int left = depth(node.left); // 左儿子为根的子树的深度
    int right = depth(node.right); // 右儿子为根的子树的深度
    ans = max(ans, left + right + 1); // 计算d_node即L + R + 1 并更新ans
    return max(left, right) + 1; // 返回该节点为根的子树的深度
}
*/