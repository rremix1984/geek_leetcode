/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import com.leetcode.util.TreeNode;
import org.junit.Test;
import java.util.*;
import static com.leetcode.util.MathUtils.*;

/**
    [TREE]
    (简单)
    257. 二叉树的所有路径
        给你一个二叉树的根节点 root ，按 任意顺序 ，返回所有从根节点到叶子节点的路径。
        叶子节点 是指没有子节点的节点。
    示例 1：
        输入：root = [1, 2, 3, null, 5]
        输出：["1->2->5", "1->3"]
    示例 2：
        输入：root = [1]
        输出：["1"]
    提示：
        树中节点的数目在范围 [1, 100] 内
        -100 <= Node.val <= 100
*/
public class NO257_E_BinaryTreePaths_x2 {

    @Test
    public void test() {
        assert getArray("1->2->5", "1->3").equals(
                binaryTreePaths(cTree(1,2,3,null,5)));
        assert getArray("1").equals(
                binaryTreePaths(cTree(1)));
    }

    public List<String> binaryTreePaths(TreeNode root) {
        List<String> res = new ArrayList<>();
        return res;
    }

}


















/**
// 方法1：
public List<String> binaryTreePaths(TreeNode root) {
    List<String> paths = new ArrayList<>();
    call(root, "", paths);
    return paths;
}

public void call(TreeNode root, String curPath, List<String> paths) {
    if (root == null)
        return;

    StringBuilder cur = new StringBuilder(curPath);
    cur.append(root.val);
    if (root.left == null && root.right == null) {  // 当前节点是叶子节点
        paths.add(cur.toString());  // 把路径加入到答案中
        return;
    }

    // 当前节点不是叶子节点，继续递归遍历
    cur.append("->");
    call(root.left, cur.toString(), paths);
    call(root.right, cur.toString(), paths);
}
*/
