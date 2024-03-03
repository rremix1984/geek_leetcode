/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;

/**
    [ARRAY] |
    (简单)（太简单，读题很重要）
    1920. 基于排列构建数组
        给你一个从0开始的排列nums（下标也从0开始）。请你构建一个同样长度
        的数组ans，其中对于每个i（0 <= i < nums.length），都满足
        ans[i] = nums[nums[i]]，返回构建好的数组ans。
        从0开始的排列nums是一个由0 到 nums.length - 1
        （0 和 nums.length - 1 也包含在内）的不同整数组成的数组。
    示例 1：
        输入：nums = {0, 2, 1, 5, 3, 4}
        输出：{0, 1, 2, 4, 5, 3}
        解释：数组 ans 构建如下：
             ans = [nums[nums[0]],  nums[nums[1]],  nums[nums[2]],  nums[nums[3]],  nums[nums[4]],  nums[nums[5]]]
             = [nums[0],  nums[2],  nums[1],  nums[5],  nums[3],  nums[4]]
             = [0, 1, 2, 4, 5, 3]
    示例 2：
        输入：nums = {5, 0, 1, 2, 3, 4}
        输出：{4, 5, 0, 1, 2, 3}
        解释：数组 ans 构建如下：
             ans = [nums[nums[0]], nums[nums[1]], nums[nums[2]], nums[nums[3]], nums[nums[4]], nums[nums[5]]]
             = [nums[5], nums[0], nums[1], nums[2], nums[3], nums[4]]
             = [4,5,0,1,2,3]
*/
public class NO1920_E_BuildArray_x2 {

    @Test
    public void test() {
        assertArrayEquals(new int[]{0, 1, 2, 4, 5, 3},
            buildArray(new int[]{0, 2, 1, 5, 3, 4}));
        assertArrayEquals(new int[]{4, 5, 0, 1, 2, 3},
            buildArray(new int[]{5, 0, 1, 2, 3, 4}));
    }

    public int[] buildArray(int[] nums) {
        // 2024/2/27 NO.3
        int[] ans = new int[nums.length];
        return ans;
    }

}

















/*
// 方法1：
public int[] buildArray(int[] nums) {
    int[] ans = new int[nums.length];
    for (int i = 0; i < nums.length; i++)
        ans[i] = nums[nums[i]];

    return ans;
}
*/