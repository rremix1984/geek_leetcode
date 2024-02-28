/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

/**
    [ARRAY] |
    (简单)
    896. 单调数列
        如果数组是【单调递增】或【单调递减】的，那么它是单调的。
        1）如果对于所有i<=j，nums[i]<=nums[j]，那么数组nums是单调递增的。
        2）如果对于所有i<=j，nums[i]>=nums[j]，那么数组nums是单调递减的。
        当给定的数组nums是单调数组时返回true，否则返回false。
    示例 1：
        输入：nums = [1, 2, 2, 3]
        输出：true
    示例 2：
        输入：nums = [6, 5, 4, 4]
        输出：true
    示例 3：
        输入：nums = [1, 3, 2]
        输出：false
    提示：
        1 <= nums.length <= 105
        -105 <= nums[i] <= 105
*/
public class NO896_E_IsMonotonic {

    @Test
    public void test() {
        assert isMonotonic(new int[]{1, 2, 2, 3});
        assert isMonotonic(new int[]{6, 5, 4, 4});
        assert !isMonotonic(new int[]{1, 3, 2});
    }

    public boolean isMonotonic(int[] nums) {
        // 2024/2/27 NO.3
        return true;
    }

}














/*
// 方法1：
public boolean isMonotonic(int[] nums) {
    return isSorted(nums, true)
            || isSorted(nums, false);
}

public boolean isSorted(int[] nums, boolean increasing) {
    int n = nums.length;
    if (increasing) {
        for (int i = 0; i < n - 1; i++)
            if (nums[i] > nums[i + 1])
                return false;
    } else {
        for (int j = 0; j < n - 1; j++)
            if (nums[j] < nums[j + 1])
                return false;
    }
    return true;
}
*/