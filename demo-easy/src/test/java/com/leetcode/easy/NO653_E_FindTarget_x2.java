/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import com.leetcode.util.TreeNode;
import org.junit.Test;
import java.util.HashSet;
import java.util.Set;
import static com.leetcode.util.MathUtils.cTree;

/**
    (简单)
    653. 两数之和 IV - 输入二叉搜索树
        给定一个二叉搜索树 root 和一个目标结果 k，
        如果二叉搜索树中存在两个元素且它们的和等于给定的目标结果，
        则返回 true。
    示例 1：
        输入: root = [5,3,6,2,4,null,7], k = 9
        输出: true
    示例 2：
        输入: root = [5,3,6,2,4,null,7], k = 28
        输出: false
    提示:
        二叉树的节点个数的范围是  [1, 104].
        -104 <= Node.val <= 104
        题目数据保证，输入的 root 是一棵 有效 的二叉搜索树
        -105 <= k <= 105
*/
public class NO653_E_FindTarget_x2 {

    @Test
    public void test() {
        assert findTarget(cTree(5,3,6,2,4,null,7),9);
        assert !findTarget(cTree(5,3,6,2,4,null,7), 28);
    }

    Set<Integer> set = new HashSet<>();
    public boolean findTarget(TreeNode root, int k) {
        if (root == null)
            return false;

        if (set.contains(root.val))
            return true;

        set.add(k - root.val);

        return findTarget(root.left, k) || findTarget(root.right, k);
    }

}
