/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import com.leetcode.util.TreeNode;
import org.junit.Test;
import static com.leetcode.util.MathUtils.cTree;

/**
    [TREE]
    (简单)
    572. 另一棵树的子树
        给你两棵二叉树 root 和 subRoot 。检验 root 中是否包含和 subRoot
        具有相同结构和节点值的子树。
        如果存在，返回 true ；否则，返回 false 。
        二叉树 tree 的一棵子树包括 tree 的某个节点和这个节点的所有后代节点。
        tree 也可以看做它自身的一棵子树。
    示例 1：
        输入：root = {3, 4, 5, 1, 2},
             subRoot = {4, 1, 2}
        输出：true
    示例 2：
        输入：root = {3, 4, 5, 1, 2, null, null, null, null, 0},
             subRoot = {4, 1, 2}
        输出：false
    提示：
        root 树上的节点数量范围是 [1, 2000]
        subRoot 树上的节点数量范围是 [1, 1000]
        -10^4 <= root.val <= 10^4
        -10^4 <= subRoot.val <= 10^4
*/
public class NO572_E_IsSubtree_x2 {

    @Test
    public void test() {
        assert isSubtree(
                cTree(3, 4, 5, 1, 2), 
                cTree(4, 1, 2));
        assert !isSubtree(
                cTree(3, 4, 5, 1, 2, null, null, null, null, 0), 
                cTree(4, 1, 2));
    }

    public boolean isSubtree(TreeNode<Integer> s, TreeNode<Integer> t) {
        return false;
    }

}


















/**
// 方法2：
public boolean isSubtree(TreeNode s, TreeNode t) {
    return dfs(s, t);
}

public boolean dfs(TreeNode s, TreeNode t) {
    if (s == null)
        return false;

    return check(s, t) || dfs(s.left, t) || dfs(s.right, t);
}

public boolean check(TreeNode s, TreeNode t) {
    if (s == null && t == null)
        return true;

    if (s == null || t == null || s.val != t.val)
        return false;

    return check(s.left, t.left) && check(s.right, t.right);
}
*/