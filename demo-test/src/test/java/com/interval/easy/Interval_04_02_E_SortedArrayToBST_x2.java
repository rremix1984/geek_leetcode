/**
 * copyright 2022/1/19
 */
package com.interval.easy;

import com.leetcode.util.TreeNode;
import org.junit.Test;
import static com.leetcode.util.MathUtils.cTree;

/**
    [TREE]
    (简单)
    面试题 04.02. 最小高度树
        给定一个有序整数数组，元素各不相同且按升序排列，编写一个算法，创建一棵高度最小的二叉搜索树。
        示例:
        给定有序数组: [-10, -3, 0, 5, 9],
        一个可能的答案是：[0, -3, 9, -10, null, 5]，
        它可以表示下面这个高度平衡二叉搜索树：
             0
            / \
          -3   9
          /   /
        -10  5
*/
public class Interval_04_02_E_SortedArrayToBST_x2 {

    @Test
    public void test() {
        assert cTree(0, -10, 5, null, -3, null, 9).equals(
                sortedArrayToBST(new int[]{-10, -3, 0, 5, 9}));
    }

    public TreeNode<Integer> sortedArrayToBST(int[] nums) {
        return null;
    }

}

















/**
public TreeNode sortedArrayToBST(int[] nums) {
    return call(nums, 0, nums.length - 1);
}

public TreeNode call(int[] nums, int left, int right) {
    if (left > right)
        return null;

    // 总是选择中间位置左边的数字作为根节点
    int mid = left + (right - left) / 2;

    TreeNode root = new TreeNode(nums[mid]);
    root.left = call(nums, left, mid - 1);
    root.right = call(nums, mid + 1, right);
    return root;
}
*/