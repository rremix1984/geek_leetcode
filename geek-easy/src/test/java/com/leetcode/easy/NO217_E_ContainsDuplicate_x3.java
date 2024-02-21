/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

/**
    [ARRAY] |
    (简单)
    217. 存在重复元素
        给你一个整数数组 nums 。如果任一值在数组中出现 至少两次 ，返回 true ；
        如果数组中每个元素互不相同，返回 false 。
    示例 1：
        输入：nums = {1, 2, 3, 1}
        输出：true
    示例 2：
        输入：nums = {1, 2, 3, 4}
        输出：false
    示例 3：
        输入：nums = {1, 1, 1, 3, 3, 4, 3, 2, 4, 2}
        输出：true
*/
public class NO217_E_ContainsDuplicate_x3 {

    @Test
    public void test() {
        assert containsDuplicate(new int[]{1, 2, 3, 1});
        assert !containsDuplicate(new int[]{1, 2, 3, 4});
        assert containsDuplicate(new int[]{1, 1, 1, 3, 3, 4, 3, 2, 4, 2});
    }

    public boolean containsDuplicate(int[] nums) {
        return false;
    }

}



















/*
// 方法1：
public boolean containsDuplicate(int[] nums) {
    Arrays.sort(nums);
    int n = nums.length;
    for (int i = 0; i < n - 1; i++)
        if (nums[i] == nums[i + 1])
            return true;
    return false;
}
*/