/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
    [ARRAY]
    (简单)
    704. 二分查找
        给定一个 n 个元素有序的（升序）整型数组 nums 和一个目标值 target
        ，写一个函数搜索 nums 中的 target，如果目标值存在返回下标，
        否则返回 -1。
    示例 1:
        输入: nums = [-1, 0, 3, 5, 9, 12], target = 9
        输出: 4
        解释: 9 出现在 nums 中并且下标为 4
    示例 2:
        输入: nums = [-1, 0, 3, 5, 9, 12], target = 2
        输出: -1
        解释: 2 不存在 nums 中因此返回 -1
*/
public class NO704_E_BinarySearch_x2 {

    @Test
    public void test() {
        assertEquals(search(new int[]{-1, 0, 3, 5, 9, 12}, 9), 4);
        assertEquals(search(new int[]{-1, 0, 3, 5, 9, 12}, 2), -1);
    }

    public int search(int[] nums, int target) {
        return -1;
    }
}

















/*
public int search(int[] nums, int target) {
    int left = 0;
    int right = nums.length - 1;
    while (left <= right) {
        int mid = (right - left) / 2 + left;
        int num = nums[mid];
        if (num == target)
            return mid;
        else if (num > target)
            right = mid - 1;
        else
            left = mid + 1;
    }
    return -1;
}
*/