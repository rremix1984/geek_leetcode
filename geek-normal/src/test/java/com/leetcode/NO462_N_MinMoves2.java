/**
 * copyright@2019/11/24 G. Chen All Rights Reserved.
 */
package com.leetcode;

import org.junit.Test;
import java.util.Arrays;
import static java.lang.Math.abs;
import static java.util.Arrays.sort;

/**
    [ARRAY]
    (中等)
    NO.462 最小操作数使数组元素相等 II
    给你一个长度为 n 的整数数组 nums ，返回使所有数组元素相等需要的最小操作数。
    在一次操作中，你可以使数组中的一个元素加 1 或者减 1 。
    示例 1：
        输入：nums = [1, 2, 3]
        输出：2
        解释：
        只需要两次操作（每次操作指南使一个元素加 1 或减 1）：
                (+1)          (-1)
        [1,2,3]  =>  [2,2,3]  =>  [2,2,2]
    示例 2：
        输入：nums = [1, 10, 2, 9]
        输出：16
    提示：
        n == nums.length
        1 <= nums.length <= 105
        -109 <= nums[i] <= 109
    Related Topics:数组,数学,排序
*/
public class NO462_N_MinMoves2 {

    @Test
    public void test() {
        assert 2  == minMoves2(new int[]{1, 2, 3});
        assert 16 == minMoves2(new int[]{1, 10, 2, 9});
    }

    public int minMoves2(int[] nums) {
        // 2024/3/16 NO.1
        int ans = 0;
        return ans;
    }

}















/*
// 方法1：
public int minMoves2(int[] nums) {
    int ans = 0;
    sort(nums);
    int target = nums[nums.length / 2];
    for (int num : nums)
        ans += abs(num - target);

    return ans;
}
*/