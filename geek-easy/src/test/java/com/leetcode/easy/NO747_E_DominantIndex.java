/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

/**
    [ARRAY] |
    (简单)
    747. 至少是其他数字两倍的最大数
        给你一个整数数组nums，其中总是存在【唯一的】一个最大整数。
        请你找出数组中的最大元素并检查它是否至少是数组中每个其他数字的两倍。
        如果是，则返回最大元素的下标，否则返回-1。
    示例 1：
        输入：nums = {3, 6, 1, 0}
        输出：1
        解释：6是最大的整数，对于数组中的其他整数，6至少是数组中其他元素的两倍。
             6的下标是1，所以返回1。
    示例 2：
        输入：nums = {1, 2, 3, 4}
        输出：-1
        解释：4没有超过3的两倍大，所以返回-1。
    示例 3：
        输入：nums = {1}
        输出：0
        解释：因为不存在其他数字，所以认为现有数字1至少是其他数字的两倍。
*/
public class NO747_E_DominantIndex {

    @Test
    public void test() {
        assert 1 == dominantIndex(new int[]{3, 6, 1, 0});
        assert -1 == dominantIndex(new int[]{1, 2, 3, 4});
        assert 0 == dominantIndex(new int[]{1});
    }

    public int dominantIndex(int[] nums) {
        // 2024/3/4 NO.1
        int idx = -1;
        return idx;
    }

}



















/*
// 方法1：
public int dominantIndex(int[] nums) {
    // 第 1 大的元素
    int m1 = -1;

    // 第 2 大的元素
    int m2 = -1;
    int index = -1;
    for (int i = 0; i < nums.length; i++) {
        // 大于m1 就是第一大元素，
        // 原来的第 1 大（m1），就给第 2 大（m2）
        if (nums[i] > m1) {
            m2 = m1;
            m1 = nums[i];
            index = i;
        } else if (nums[i] > m2) {
            m2 = nums[i];
        }
    }
    return m1 >= m2 * 2 ? index : -1;
}
*/