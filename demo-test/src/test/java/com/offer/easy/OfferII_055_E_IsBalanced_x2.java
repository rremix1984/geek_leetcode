/**
 * copyright 2022/1/19
 */
package com.offer.easy;

import com.leetcode.util.TreeNode;
import org.junit.Test;
import static com.leetcode.util.MathUtils.cTree;

/**
    (简单)
    剑指 Offer 55 - II. 平衡二叉树
        输入一棵二叉树的根节点，判断该树是不是平衡二叉树。
        如果某二叉树中任意节点的左右子树的深度相差不超过1，
        那么它就是一棵平衡二叉树。
    示例 1:
        给定二叉树 {3, 9, 20, null, null, 15, 7}
                3
               / \
              9  20
                /  \
               15   7
        返回 true。
    示例 2:
        给定二叉树 {1, 2, 2, 3, 3, null, null, 4, 4}
                1
               / \
              2   2
             / \
            3   3
           / \
          4   4
        返回 false。
*/
public class OfferII_055_E_IsBalanced_x2 {

    @Test
    public void test() {
        assert isBalanced(cTree(3, 9, 20, null, null, 15, 7));
        assert !isBalanced(cTree(1, 2, 2, 3, 3, null, null, 4, 4));
    }
    
    public boolean isBalanced(TreeNode root) {
        return true;
    }

}

















/**
// 方法1：递归法
public boolean isBalanced(TreeNode root) {
    if (root == null)
        return true;

    return abs(height(root.left) - height(root.right)) <= 1
            && isBalanced(root.left)
            && isBalanced(root.right);
}

public int height(TreeNode root) {
    if (root == null)
        return 0 ;

    return max(height(root.left), height(root.right)) + 1;
}

// 方法2：自底向上递归法
public boolean isBalanced(TreeNode root) {
    return height(root) >= 0;
}

public int height(TreeNode root) {
    if (root == null)
        return 0;
    int leftHeight = height(root.left);
    int rightHeight = height(root.right);
    if (leftHeight == -1 ||
        rightHeight == -1 ||
        abs(leftHeight - rightHeight) > 1)
        return -1;
    return Math.max(leftHeight, rightHeight) + 1;
}

// 方法3
public boolean isBalanced(TreeNode root) {
    return dfs(root) != -1;
}

//用left，right记录root左右子节点的深度，避免遍历root时对左右节点的深度进行重复计算。
//考虑到需要同时记录各个节点的深度和其是否符合平衡性要求，这里的返回值设为int,用一个特殊值-1来表示出现不平衡的节点的情况，而不是一般采用的boolean
public int dfs(TreeNode root) {
    //用后序遍历的方式遍历二叉树的每个节点（从底至顶）,先左子树，再右子树，最后根节点，
    if (root == null)
        return 0;//root等于0时，该节点符合要求，返回其深度0，而不返回-1；
    int left = dfs(root.left); //left最开始的取值为0，从底朝上遍历，先左子树，后右子树，最后根节点
    if (left == -1)
        return -1;//若出现节点的深度为-1，则进行剪枝，开始向上返回，之后的迭代不再进行
    int right = dfs(root.right);
    if (right == -1)
        return -1;
    return Math.abs(right-left) < 2 ? Math.max(left, right) + 1 : -1; //+1不能少
    //最开始计算的是左子树最左侧的一个叶节点，其左右子节点不存在，left=0，right=0，满足条件，返回该叶节点的深度max(0,0)+1=1;
}
*/