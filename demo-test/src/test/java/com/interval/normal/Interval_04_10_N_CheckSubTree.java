/**
 * copyright 2022/1/19
 */
package com.interval.normal;

import com.leetcode.util.TreeNode;
import org.junit.Test;
import static com.leetcode.util.MathUtils.cTree;

/**
    (中等)
    面试题 04.10. 检查子树
        检查子树。你有两棵非常大的二叉树：T1，有几万个节点；T2，有几万个节点。
        设计一个算法，判断T2是否为T1的子树。
        如果T1有这么一个节点n，其子树与T2一模一样，则T2为T1的子树，
        也就是说，从节点n处把树砍断，得到的树与T2完全相同。
        注意：此题相对书上原题略有改动。
    示例1:
        输入：t1 = [1, 2, 3], t2 = [2]
        输出：true
    示例2:
        输入：t1 = [1, null, 2, 4], t2 = [3, 2]
        输出：false
    提示：
        树的节点数目范围为[0, 20000]。
*/
public class Interval_04_10_N_CheckSubTree {

    @Test
    public void test() {
        assert checkSubTree(cTree(1, 2, 3), cTree(2));
        assert !checkSubTree(cTree(1, null, 2, 4), cTree(3, 2));
    }

    public boolean checkSubTree(TreeNode t1, TreeNode t2) {
        StringBuilder s1 = new StringBuilder();
        StringBuilder s2 = new StringBuilder();
        travesal(t1, s1);
        travesal(t2, s2);
        return s1.toString().contains(s2.toString());
    }

    public void travesal(TreeNode node, StringBuilder s) {
        if (node == null)
            return;

        travesal(node.left, s);

        s.append(node.val);

        travesal(node.right, s);
    }

}
