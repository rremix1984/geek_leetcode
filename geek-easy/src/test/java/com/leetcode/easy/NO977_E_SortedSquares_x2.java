/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;

/**
    (简单)
    977. 有序数组的平方
        给你一个按【非递减顺序】排序的整数数组 nums，
        返回每个数字的平方组成的新数组，要求也按非递减顺序排序。
    示例 1：
        输入：nums = {-4, -1, 0, 3, 10}
        输出：{0, 1, 9, 16, 100}
        解释：平方后，数组变为 {16, 1, 0, 9, 100}
             排序后，数组变为 {0, 1, 9, 16, 100}
    示例 2：
        输入：nums = {-7, -3, 2, 3, 11}
        输出：{4, 9, 9, 49, 121}
    提示：
        1 <= nums.length <= 10 ^ 4
        -10 ^ 4 <= nums[i] <= 10 ^ 4
        nums 已按 非递减顺序 排序
*/
public class NO977_E_SortedSquares_x2 {

    @Test
    public void test() {
        assertArrayEquals(new int[]{0, 1, 9, 16, 100}, 
                sortedSquares(new int[]{-4, -1, 0, 3, 10}));
        assertArrayEquals(new int[]{4, 9, 9, 49, 121}, 
                sortedSquares(new int[]{-7, -3, 2, 3, 11}));
    }

    public int[] sortedSquares(int[] nums) {
        int [] ans = new int [nums.length];
        return ans;
    }

}

















/**
// 方法1：
public int[] sortedSquares(int[] nums) {
    int[] ans = new int[nums.length];
    for (int i = 0; i < nums.length; i++)
        ans[i] = nums[i] * nums[i];

    Arrays.sort(ans);
    return ans;
}

// 方法2：推荐
public int[] sortedSquares(int[] nums) {
    int [] ans = new int [nums.length];

    // 头索引
    int l = 0;

    // 尾部索引
    int r = nums.length - 1;

    // i 新数组的的下标
    for (int i = nums.length - 1; i >= 0; i--)
        if (nums[l] * nums[l] < nums[r] * nums[r]) {
            ans[i] = nums[r] * nums[r];
            r--;
        } else {
            ans[i] = nums[l] * nums[l];
            l++;
        }
    return ans;
}
*/