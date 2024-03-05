/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

import java.util.Arrays;

/**
    [ARRAY] ||
    (简单)
    561. 数组拆分
        给定长度为2n的整数数组nums，你的任务是将这些数分成n对,
        例如 (a1, b1), (a2, b2), ..., (an, bn) ，使得从1到n的
        min(ai, bi) 总和最大。返回该[最大总和]。
    示例 1：
        输入：nums = {1, 4, 3, 2}
        输出：4
        解释：所有可能的分法（忽略元素顺序）为：
          1. (1, 4), (2, 3) -> min(1, 4) + min(2, 3) = 1 + 2 = 3
          2. (1, 3), (2, 4) -> min(1, 3) + min(2, 4) = 1 + 2 = 3
          3. (1, 2), (3, 4) -> min(1, 2) + min(3, 4) = 1 + 3 = 4
        所以最大总和为 4
    示例 2：
        输入：nums = {6, 2, 6, 5, 1, 2}
        输出：9
        解释：最优的分法为 (2, 1), (2, 5), (6, 6).
             min(2, 1) + min(2, 5) + min(6, 6) = 1 + 2 + 6 = 9
    提示：
        1 <= n <= 104
        nums.length == 2 * n
        -10^4 <= nums[i] <= 10^4
*/
public class NO561_E_ArrayPairSum {

    @Test
    public void test() {
        assert 4 == arrayPairSum(new int[]{1, 4, 3, 2});
        assert 9 == arrayPairSum(new int[]{6, 2, 6, 5, 1, 2});
    }

    public int arrayPairSum(int[] nums) {
        // 2024/2/23 NO.3
        // 2024/3/5  NO.4
        int ans = 0;
        return ans;
    }

}















/*
// 方法1：
public int arrayPairSum(int[] nums) {
    Arrays.sort(nums);
    int ans = 0;
    for (int i = 0; i < nums.length; i += 2)
        ans += nums[i];

    return ans;
}
*/