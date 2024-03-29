/**
 * copyright@2019/12/23 lyc
 */
package com.leetcode;

import org.junit.Test;

import static com.leetcode.util.SystemUtil.printArr;
import static java.lang.Math.abs;

/**
    [ARRAY] |||
    (中等)
    NO.041 缺失的第一个正整数
    给你一个未排序的整数数组 nums，请你找出其中没有出现的最小的正整数。
    请你实现时间复杂度为 O(n) 并且只使用常数级别额外空间的解决方案。
    示例 1：
        输入：nums = [1, 2, 0]
        输出：3
        解释：范围 [1,2] 中的数字都在数组中。
    示例 2：
        输入：nums = [3, 4, -1, 1]
        输出：2
        解释：1 在数组中，但 2 没有。
    示例 3：
        输入：nums = [7, 8, 9, 11, 12]
        输出：1
        解释：最小的正数 1 没有出现。
    提示：
        1 <= nums.length <= 10 ^ 5
        -2 ^ 31 <= nums[i] <= 2 ^ 31 - 1
    Related Topics:数组,哈希表
*/
public class NO041_N_FirstMissingPositive {

    @Test
    public void test() {
        assert 3 == firstMissingPositive(new int[]{1, 2, 0});
        assert 2 == firstMissingPositive(new int[]{3, 4, -1, 1});
        assert 1 == firstMissingPositive(new int[]{7, 8, 9, 11, 12});
    }

    public int firstMissingPositive(int[] nums) {
        // 2024/3/19 NO.1
        // 2024/3/20 NO.2 理解不了，脑子不转了
        // 2024/3/27 NO.3 终于看懂了，能做出来了
        int n = nums.length;

        return n + 1;
    }

}















/*
// 方法1：
// 第一步：处理非正数
//  首先，我们要确保只考虑正整数，因为我们要找的是最小的缺失的正整数。
//  所以，代码的第一步是将所有非正数（包括零和负数）替换为一个肯定不
//  会是我们想要找的数的数字，即 n + 1（因为在最坏的情况下，即数组
//  正好包含了从1到n的所有正整数，缺失的最小正整数将是 n + 1）。
//
// 第二步：标记存在的正整数
//  接下来，我们要找出哪些正整数是存在于数组中的。通过遍历数组，我们利
//  用数组索引作为一种“存在标记”，如果某个正整数 num 存在于数组中，我
//  们就将索引为 num - 1 的数组元素标记为负数。这里使用绝对值是为了确
//  保不会因为标记过程中的负号改变而影响判断其他数字的存在性。
//
// 第三步：找出缺失的最小正整数
//  最后，我们再次遍历数组，找到第一个仍然为正数的数组元素。这个元素的
//  索引加1，就是我们要找的最小缺失的正整数。如果所有的数都被标记为了负数，
//  这意味着数组中包含了从1到n的所有正整数，因此缺失的最小正整数是 n + 1。
//
// 感性理解
//  把非正数变成无关紧要的数字，因为我们只关心正整数。
//  通过变负数来“签到”，把存在的正整数标记出来，就像在一张签到表上打勾。
//  找到第一个没“签到”的位置，它就是我们失踪的最小正整数。
public int firstMissingPositive(int[] nums) {
    int n = nums.length;
    for (int i = 0; i < n; i++)
        if (nums[i] <= 0)
            nums[i] = n + 1;

    for (int i = 0; i < n; i++) {
        int num = abs(nums[i]);
        if (num <= n)
            nums[num - 1] = -abs(nums[num - 1]);
    }

    for (int i = 0; i < n; i++)
        if (nums[i] > 0)
            return i + 1;

    return n + 1;
}
*/