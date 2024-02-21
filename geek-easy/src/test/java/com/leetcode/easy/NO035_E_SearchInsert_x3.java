/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

/**
    [ARRAY] |
    (简单)
    35. 搜索插入位置
        给定一个【排序数组】和一个【目标值】，在数组中找到目标值，并返回其索引。
        如果目标值不存在于数组中，返回它将会被按顺序插入的位置。
        请必须使用时间复杂度为 O(log n) 的算法。
    示例 1:
        输入: nums = {1, 3, 5, 6}, target = 5
        输出: 2
    示例 2:
        输入: nums = {1, 3, 5, 6}, target = 2
        输出: 1
    示例 3:
        输入: nums = {1, 3, 5, 6}, target = 7
        输出: 4
    提示:
        1 <= nums.length <= 104
        -104 <= nums[i] <= 104
        nums 为 无重复元素 的 升序 排列数组
        -104 <= target <= 104
*/
public class NO035_E_SearchInsert_x3 {

    @Test
    public void test() {
        assert 2 == searchInsert(new int[]{1, 3, 5, 6},5);
        assert 1 == searchInsert(new int[]{1, 3, 5, 6},2);
        assert 4 == searchInsert(new int[]{1, 3, 5, 6},7);
    }

    public int searchInsert(int[] nums, int target) {
        int ans = -1;
        return ans;
    }

}

















/**
public int searchInsert(int[] nums, int target) {
    int left = 0;
    int right = nums.length - 1;
    int ans = nums.length;
    while (left <= right) {
        int mid = ((right - left) >> 1) + left;
        if (target <= nums[mid]) {
            ans = mid;
            right = mid - 1;
        } else
            left = mid + 1;
    }
    return ans;
}
*/