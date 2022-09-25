/**
 * copyright 2022/1/19
 */
package com.offer.easy;

import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;

/**
    (简单)
    剑指 Offer 57. 和为s的两个数字
        输入一个递增排序的数组和一个数字s，在数组中查找两个数，使得它们的和正好是s。
        如果有多对数字的和等于s，则输出任意一对即可。
    示例 1：
        输入：nums = {2, 7, 11, 15},  target = 9
        输出：{2, 7} 或者 {7, 2}
    示例 2：
        输入：nums = {10, 26, 30, 31, 47, 60},  target = 40
        输出：{10, 30} 或者 {30, 10}
*/
public class Offer57_E_TwoSum_x2 {
    
    @Test
    public void test() {
        assertArrayEquals(new int[]{2, 7}, twoSum(new int[]{2, 7, 11, 15}, 9));
        assertArrayEquals(new int[]{10, 30}, twoSum(new int[]{10, 26, 30, 31, 47, 60}, 40));
    }

    public int[] twoSum(int[] nums, int target) {
        int l = 0;
        int r = nums.length - 1;
        while (l < r) {
            if (nums[l] + nums[r] < target) {
                l++;
            } else if (nums[l] + nums[r] > target) {
                r--;
            } else {
                return new int[]{nums[l], nums[r]};
            }
        }
        return new int[]{-1, -1};
    }

}
