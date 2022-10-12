/**
 * copyright 2022/1/19
 */
package com.leetcode;

import org.junit.Test;

/**
    (简单)
    2367. 算术三元组的数目
        给你一个下标从 0 开始、严格递增 的整数数组 nums 和一个正整数 diff 。如果满足下述全部条件，则三元组 (i, j, k) 就是一个 算术三元组 ：
        i < j < k ，
        nums[j] - nums[i] == diff 且
        nums[k] - nums[j] == diff
        返回不同 算术三元组 的数目。
    示例 1：
        输入：nums = {0, 1, 4, 6, 7, 10},  diff = 3
        输出：2
        解释：(1,  2,  4) 是算术三元组：7 - 4 == 3 且 4 - 1 == 3 。
             (2,  4,  5) 是算术三元组：10 - 7 == 3 且 7 - 4 == 3 。
    示例 2：
        输入：nums = {4, 5, 6, 7, 8, 9},  diff = 2
        输出：2
        解释：(0, 2, 4) 是算术三元组：8 - 6 == 2 且 6 - 4 == 2 。
             (1, 3, 5) 是算术三元组：9 - 7 == 2 且 7 - 5 == 2 。
*/
public class NO2367_E_ArithmeticTriplets {

    @Test
    public void test() {
        assert 2 == arithmeticTriplets(new int[]{0, 1, 4, 6, 7, 10},3);
        assert 2 == arithmeticTriplets(new int[]{0, 1, 4, 6, 7, 10},3);
    }

    public int arithmeticTriplets(int[] nums, int diff) {
        int ans = 0;
        int j = 1;
        int i = 0;
        for (int num : nums) {
            while (nums[j] + diff < num)
                j++;

            if (nums[j] + diff != num)
                continue;

            //当找到一对 （nums[j] ,x）时 才会执行下列代码
            while (nums[i] + diff < nums[j])
                i++;

            if (nums[i] + diff == nums[j])
                ans++;
        }
        return ans;
    }

}
