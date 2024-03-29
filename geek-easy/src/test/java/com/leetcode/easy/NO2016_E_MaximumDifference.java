/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;
import static java.lang.Math.max;

/**
    [ARRAY] ||||
    (简单)
    2016. 增量元素之间的最大差值
        给你一个下标从 0 开始的整数数组 nums, 该数组的大小为 n，
        请你计算 nums[j] - nums[i] 能求得的最大差值，
        其中0 <= i < j < n 且nums[i] < nums[j]。
        返回最大差值。如果不存在满足要求的 i 和 j，返回 -1。
    示例 1：
        输入：nums = {7, 1, 5, 4}
        输出：4
        解释：最大差值出现在 i = 1 且 j = 2 时，nums[j] - nums[i] = 5 - 1 = 4。
             注意，尽管i = 1且j = 0时，nums[j] - nums[i] = 7 - 1 = 6 > 4，
             但i>j不满足题面要求，所以6不是有效的答案。
    示例 2：
        输入：nums = {9, 4, 3, 2}
        输出：-1
        解释：不存在同时满足i < j和nums[i] < nums[j]这两个条件的i, j组合。
    示例 3：
        输入：nums = {1, 5, 2, 10}
        输出：9
        解释：最大差值出现在i = 0且j = 3时，nums[j] - nums[i] = 10 - 1 = 9。
*/
public class NO2016_E_MaximumDifference {

    @Test
    public void test() {
        assert  4 == maximumDifference(new int[]{7, 1, 5, 4});
        assert  9 == maximumDifference(new int[]{1, 5, 2, 10});
        assert -1 == maximumDifference(new int[]{9, 4, 3, 2});
    }

    public int maximumDifference(int[] nums) {
        // 2024/3/4 NO.1
        // 2024/3/8 NO.2 还是不会
        // 2024/3/10 NO.3
        // 2024/3/25 NO.4 能理解了，没做出来
        int ans = -1;
        return ans;
    }

}














/*
// 方法1：
public int maximumDifference(int[] nums) {
    int ans = -1;
    int min = nums[0];
    for (int num : nums) {
        if (num > min)
            ans = max(ans, num - min);
        else
            min = num;
    }
    return ans;
}
*/