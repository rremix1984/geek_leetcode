/**
 * copyright: Copyright (c) 2020-2021 fudai,
 * Inc. All Rights Reserved.
 */
package com.leetcode.normal;

import org.junit.Test;
import static java.lang.Integer.MAX_VALUE;
import static java.lang.Math.abs;
import static java.lang.Math.min;
import static java.util.Arrays.sort;

/**
    [ARRAY] |||
    (中等)
    NO.2740 找出分区值
    给你一个正整数数组 nums。
    将nums分成两个数组：nums1 和 nums2 ，并满足下述条件：
      1）数组nums中的每个元素都属于数组 nums1 或数组 nums2。
      2）两个数组都非空。
      3）分区值最小。
      4）分区值的计算方法是 |max(nums1) - min(nums2)| 。
    其中，max(nums1) 表示数组 nums1 中的最大元素，
         min(nums2) 表示数组 nums2 中的最小元素。
    返回表示分区值的整数。
    示例 1：
        输入：nums = [1, 3, 2, 4]
        输出：1
        解释：可以将数组 nums 分成 nums1 = [1, 2] 和 nums2 = [3, 4] 。
            - 数组 nums1 的最大值等于 2 。
            - 数组 nums2 的最小值等于 3 。
              分区值等于 |2 - 3| = 1 。
              可以证明 1 是所有分区方案的最小值。
    示例 2：
        输入：nums = [100, 1, 10]
        输出：9
        解释：可以将数组 nums 分成 nums1 = [10] 和 nums2 = [100,1] 。
            - 数组 nums1 的最大值等于 10 。
            - 数组 nums2 的最小值等于 1 。
              分区值等于 |10 - 1| = 9 。
              可以证明 9 是所有分区方案的最小值。
    提示：
        2 <= nums.length <= 10 ^ 5
        1 <= nums[i] <= 10 ^ 9
    Related Topics:数组,排序
    解题思路:
      1、先从小到大排序。
      2、排序后求相邻两数最小差。
*/
public class NO2740_N_FindValueOfPartition {

    @Test
    public void test() {
        assert 1 == findValueOfPartition(
                new int[]{1, 3, 2, 4});
        assert 9 == findValueOfPartition(
                new int[]{100, 1, 10});
        assert 9 == findValueOfPartition(
                new int[]{100, 1, 10, 1000, 10000});
    }

    public int findValueOfPartition(int[] nums) {
        // 2024/3/14 NO.1
        // 2024/3/19 NO.2 没思路
        // 2024/3/21 NO.3
        int min = MAX_VALUE;
        return min;
    }

}

















/*
// 方法1：
public int findValueOfPartition(int[] nums) {
    // 从小到大排序
    sort(nums);

    // 排序后求相邻两数最小差
    int min = MAX_VALUE;
    for (int i = 1; i < nums.length; i++) {
        int diff = abs(nums[i] - nums[i - 1]);
        min = min(min, diff);
    }
    return min;
}
*/