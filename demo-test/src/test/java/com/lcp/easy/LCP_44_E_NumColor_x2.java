/**
 * copyright 2022/1/19
 */
package com.lcp.easy;

import com.leetcode.util.TreeNode;
import org.junit.Test;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import static com.leetcode.util.MathUtils.cTree;

/**
    (简单)
    LCP 44. 开幕式焰火
        「力扣挑战赛」开幕式开始了，空中绽放了一颗二叉树形的巨型焰火。
        给定一棵二叉树 root 代表焰火，节点值表示巨型焰火这一位置的颜色种类。请帮小扣计算巨型焰火有多少种不同的颜色。
    示例 1：
        输入：root = [1,3,2,1,null,2]
        输出：3
        解释：焰火中有 3 个不同的颜色，值分别为 1、2、3
    示例 2：
        输入：root = [3,3,3]
        输出：1
        解释：焰火中仅出现 1 个颜色，值为 3
    提示：
        1 <= 节点个数 <= 1000
        1 <= Node.val <= 1000
*/
public class LCP_44_E_NumColor_x2 {

    @Test
    public void test() {
        assert 3 == numColor(cTree(1, 3, 2, 1, null, 2));
        assert 1 == numColor(cTree(3, 3, 3));
    }

    public int numColor(TreeNode root) {
        Set<Integer> set = new HashSet<>();
        Deque<TreeNode> queue = new LinkedList<>();
        queue.addLast(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size>0) {
                TreeNode cur = queue.pollFirst();
                set.add(cur.val);
                if (cur.left != null)
                    queue.addLast(cur.left);

                if (cur.right!=null)
                    queue.addLast(cur.right);

                size--;
            }
        }
        return set.size();
    }

}















/**
// 方法1：
public int numColor(TreeNode root) {
    Set<Integer> set = new HashSet<>();
    call(set, root);
    return set.size();
}

public void call(Set<Integer> set, TreeNode root) {
    if (root == null)
        return;

    set.add(root.val);
    call(set, root.left);
    call(set, root.right);
}


// 方法2：
public int numColor(TreeNode root) {
    Set<Integer> set = new HashSet<>();
    Deque<TreeNode> queue = new LinkedList<>();
    queue.addLast(root);
    while (!queue.isEmpty()) {
        int size = queue.size();
        while (size>0) {
            TreeNode cur = queue.pollFirst();
            set.add(cur.val);
            if (cur.left != null)
                queue.addLast(cur.left);

            if (cur.right!=null)
                queue.addLast(cur.right);

            size--;
        }
    }
    return set.size();
}
*/