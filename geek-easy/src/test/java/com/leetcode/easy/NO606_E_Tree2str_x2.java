/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import com.leetcode.util.TreeNode;
import org.junit.Test;
import static com.leetcode.util.MathUtils.cTree;
import static org.junit.Assert.assertEquals;

/**
    [TREE]
    (简单)
    606. 根据二叉树创建字符串
        给你二叉树的根节点 root ，请你采用前序遍历的方式，将二叉树转化为一个由括号和
        整数组成的字符串，返回构造出的字符串。
        空节点使用一对空括号对 "()" 表示，转化后需要省略所有不影响字符串与原始二叉树
        之间的一对一映射关系的空括号对。
    示例 1：
        输入：root = [1,2,3,4]
        输出："1(2(4))(3)"
        解释：初步转化后得到 "1(2(4)())(3()())" ，
             但省略所有不必要的空括号对后，字符串应该是"1(2(4))(3)" 。
    示例 2：
        输入：root = [1,2,3,null,4]
        输出："1(2()(4))(3)"
        解释：和第一个示例类似，但是无法省略第一个空括号对，
             否则会破坏输入与输出一一映射的关系。
    提示：
        树中节点的数目范围是 [1, 104]
        -1000 <= Node.val <= 1000
*/
public class NO606_E_Tree2str_x2 {

    @Test
    public void test() {
        assertEquals("1(2(4))(3)",
                tree2str(cTree(1, 2, 3, 4)));
        assertEquals("1(2()(4))(3)",
                tree2str(cTree(1, 2, 3, null, 4)));
    }

    public String tree2str(TreeNode<Integer> root) {
        return null;
    }

}

















/**
// 方法1：
public String tree2str(TreeNode root) {
    if (root == null)
        return "";

    if (root.left == null && root.right == null)
        return "" + root.val;

    // 右面括号不需要留着，左面需要
    if (root.right == null)
        return root.val + "(" + tree2str(root.left) + ")";

    return root.val + "(" + tree2str(root.left) + ")(" + tree2str(root.right) + ")";
}
*/