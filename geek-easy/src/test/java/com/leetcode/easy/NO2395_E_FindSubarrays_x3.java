/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;
import java.util.HashSet;
import java.util.Set;

/**
    [ARRAY] |
    (简单)
    2395. 和相等的子数组
    给你一个下标从0开始的整数数组nums，判断是否存在【两个长度为2的】子数组且它们的和相等。
    （注意：这两个子数组起始位置的下标必须不相同）。如果这样的子数组存在，请返回true，
    否则返回false。【子数组】是一个数组中一段【连续非空】的元素组成的序列。
    示例 1：
        输入：nums = {4, 2, 4}
        输出：true
        解释：元素为 {4, 2} 和 {2, 4} 的子数组有相同的和 6 。
    示例 2：
        输入：nums = {1, 2, 3, 4, 5}
        输出：false
        解释：没有长度为2的两个子数组和相等。
    示例 3：
        输入：nums = {0, 0, 0}
        输出：true
        解释：子数组 [nums[0],nums[1]] 和 [nums[1],nums[2]] 的和相等，都为 0 。
        注意即使子数组的元素相同，这两个子数组也视为不相同的子数组，因为它们在原数组中的起始位置不同。
*/
public class NO2395_E_FindSubarrays_x3 {

    @Test
    public void test() {
        assert findSubarrays(new int[]{4, 2, 4});
        assert !findSubarrays(new int[]{1, 2, 3, 4, 5});
        assert findSubarrays(new int[]{0, 0, 0});
        assert findSubarrays(new int[]{1, 3, 2, 2});
    }

    public boolean findSubarrays(int[] nums) {
        // 2024/2/24 NO.3
        return false;
    }

}
























/*
// 方法1：
public boolean findSubarrays(int[] nums) {
    Set<Integer> set = new HashSet<>();
    for (int i = 1; i < nums.length; i++) {
        int sum = nums[i] + nums[i - 1];
        if (set.contains(sum))
            return true;

        set.add(sum);
    }
    return false;
}
*/