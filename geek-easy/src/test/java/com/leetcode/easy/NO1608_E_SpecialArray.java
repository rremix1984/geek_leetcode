/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;
import java.util.Arrays;
import static com.leetcode.util.SwapUtil.swap;

/**
    [ARRAY] |
    (简单)
    1608. 特殊数组的特征值
        给你一个非负整数数组nums。如果存在一个数x，使得nums中恰好有x个元素
        大于或者等于x，那么就称nums是一个特殊数组，而x是该数组的特征值。
        注意：x不必是nums的中的元素。
        如果数组nums是一个特殊数组，请返回它的特征值x。
        否则，返回-1。可以证明的是，如果nums是特殊数组，那么其特征值x是唯一的。
    示例 1：
        输入：nums = {3, 5}
        输出：2
        解释：有 2 个元素（3 和 5）大于或等于 2 。
    示例 2：
        输入：nums = {0, 0}
        输出：-1
        解释：没有满足题目要求的特殊数组，故而也不存在特征值 x 。
             如果 x = 0，应该有 0 个元素 >= x，但实际有 2 个。
             如果 x = 1，应该有 1 个元素 >= x，但实际有 0 个。
             如果 x = 2，应该有 2 个元素 >= x，但实际有 0 个。
             x 不能取更大的值，因为 nums 中只有两个元素。
    示例 3：
        输入：nums = {0, 4, 3, 0, 4}
        输出：3
        解释：有 3 个元素大于或等于 3 。
    示例 4：
        输入：nums = {3, 6, 7, 7, 0}
        输出：-1
*/
public class NO1608_E_SpecialArray {

    @Test
    public void test() {
        assert  2 == specialArray(new int[]{3, 5});
        assert -1 == specialArray(new int[]{0, 0});
        assert  3 == specialArray(new int[]{0, 4, 3, 0, 4});
        assert -1 == specialArray(new int[]{3, 6, 7, 7, 0});
    }

    public int specialArray(int[] nums) {
        // 2024/3/8 NO.1
        return -1;
    }



}
















/*
// 方法1：
public int specialArray(int[] nums) {
    Arrays.sort(nums);
    int n = nums.length;
    // 翻转数组
    for (int i = 0, j = n - 1; i < j; i++, j--)
        swap(nums, i, j);

    for (int i = 1; i <= n; i++)
        if (nums[i - 1] >= i && (i == n || nums[i] < i))
            return i;

    return -1;
}
*/