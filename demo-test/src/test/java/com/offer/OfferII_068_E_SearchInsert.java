/**
 * copyright 2022/1/19
 */
package com.offer;

import org.junit.Test;
import static com.leetcode.util.MathUtils.getArrays;

/**
    (简单)
    剑指 Offer II 068. 查找插入位置
        给定一个排序的整数数组 nums 和一个整数目标值 target ，请在数组中找到 target ，
        并返回其下标。如果目标值不存在于数组中，返回它将会被按顺序插入的位置。
        请必须使用时间复杂度为 O(log n) 的算法。
    示例 1:
        输入: nums = {1, 3, 5, 6},  target = 5
        输出: 2
    示例 2:
        输入: nums = {1, 3, 5, 6},  target = 2
        输出: 1
    示例 3:
        输入: nums = {1, 3, 5, 6},  target = 7
        输出: 4
    示例 4:
        输入: nums = {1, 3, 5, 6},  target = 0
        输出: 0
    示例 5:
        输入: nums = {1},  target = 0
        输出: 0
*/
public class OfferII_068_E_SearchInsert {

    @Test
    public void test() {
        assert 2 == searchInsert(getArrays(1, 3, 5, 6), 5);
        assert 1 == searchInsert(getArrays(1, 3, 5, 6), 2);
        assert 4 == searchInsert(getArrays(1, 3, 5, 6), 7);
        assert 0 == searchInsert(getArrays(1, 3, 5, 6), 0);
        assert 0 == searchInsert(getArrays(1), 0);
    }

    public int searchInsert(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        int ans = nums.length;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (target <= nums[mid]) {
                ans = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return ans;
    }

}

















/**
// 方法1：二分查找法
public int searchInsert(int[] nums, int target) {
    int left = 0;
    int right = nums.length - 1;
    int ans = nums.length;
    while (left <= right) {
        int mid = left + (right - left) / 2;
        if (target <= nums[mid]) {
            ans = mid;
            right = mid - 1;
        } else {
            left = mid + 1;
        }
    }
    return ans;
}
*/