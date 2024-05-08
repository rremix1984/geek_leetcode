/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;
import static com.leetcode.util.LogUtil.info;
import static java.lang.Math.max;

/**
    (简单)
    674. 最长连续递增序列
        给定一个未经排序的整数数组，找到最长且 连续递增的子序列，并返回该序列的长度。
        连续递增的子序列 可以由两个下标 l 和 r（l < r）确定，如果对于每个 l <= i < r，
        都有 nums[i] < nums[i + 1] ，那么子序列 [nums[l], nums[l + 1], ..., nums[r - 1], nums[r]]
        就是连续递增子序列。
    示例 1：
        输入：nums = [1,3,5,4,7]
        输出：3
        解释：最长连续递增序列是 [1,3,5], 长度为3。
             尽管 [1,3,5,7] 也是升序的子序列, 但它不是连续的，因为 5 和 7 在原数组里被 4 隔开。
    示例 2：
        输入：nums = [2,2,2,2,2]
        输出：1
        解释：最长连续递增序列是 [2], 长度为1。
*/
@SuppressWarnings("all")
public class NO674_E_LongestContinuousIncreasingSubsequence_x2 {

    @Test
    public void test() {
        assert 5 == lengthOfLIS(new int[]{1, 2, 3, 2, 3, 4, 3, 4, 5, 6, 7});// 5
        assert 3 == lengthOfLIS(new int[]{1, 3, 5, 4, 7});// 3
        assert 1 == lengthOfLIS(new int[]{2, 2, 2, 2, 2});// 1
        assert 1 == lengthOfLIS(new int[]{1});// 1
    }

    public int lengthOfLIS(int[] nums) {
        int res = 1;
        return res;
    }

}



















/*
// 方法1：
public int lengthOfLIS(int[] nums) {
    // 最短序列从哪里开始
    int start = 0;
    int max = 1;
    for (int i = 1; i< nums.length; i++) {
        // 后面小于前面了，start 要重新计算
        if (nums[i] <= nums[i - 1])
            start = i;

        max = max(max, i - start + 1);
    }
    return max;
}
*/