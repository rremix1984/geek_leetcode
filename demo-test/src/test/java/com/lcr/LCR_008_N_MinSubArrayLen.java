/**
 *
 */
package com.lcr;

import org.junit.Test;
import static com.leetcode.util.MathUtils.MAX;
import static com.leetcode.util.MathUtils.frontSum;

/**
    [ARRAY] ||||||
    （中等）
    LCR.008 长度最小的子数组
        给定一个含有 n 个正整数的数组和一个正整数 target 。
    找出该数组中满足其和 ≥ target 的长度最小的【连续子数组】
    [numsl, numsl+1, ..., numsr - 1, numsr]，并返回其
    长度。如果不存在符合条件的子数组，返回 0。
    示例 1：
        输入：target = 7, nums = [2, 3, 1, 2, 4, 3]
        输出：2
        解释：子数组 [4, 3] 是该条件下的长度最小的子数组。
    示例 2：
        输入：target = 4, nums = [1, 4, 4]
        输出：1
    示例 3：
        输入：target = 11, nums = [1, 1, 1, 1, 1, 1, 1, 1]
        输出：0
        提示：
         1 <= target <= 10 ^ 9
         1 <= nums.length <= 10 ^ 5
         1 <= nums[i] <= 10 ^ 5
    进阶:
        如果你已经实现O(n)时间复杂度的解法, 请尝试设计一个O(nlog(n))时间复杂度的解法。

    方法一：前缀和 + 二分查找
        为了使用二分查找，需要额外创建一个数组 sums 用于存储数组 nums 的前缀和，
    其中 sums[i] 表示从 nums[0] 到 nums[i−1] 的元素和。得到前缀和之后，对于
    每个开始下标 i，可通过二分查找得到大于或等于 i 的最小下标 bound，使得
    sums[bound] − sums[i − 1] ≥ s，并更新子数组的最小长度（此时子数组的长度
    是 bound − (i − 1)）。
        因为这道题保证了数组中每个元素都为正，所以前缀和一定是递增的，这一点保证
    了二分的正确性。如果题目没有说明数组中每个元素都为正，这里就不能使用二分来查找
    这个位置了。
 */
public class LCR_008_N_MinSubArrayLen {

    @Test
    public void test() {
        assert 2 == minSubArrayLen(7,
                new int[]{2, 3, 1, 2, 4, 3});
        assert 1 == minSubArrayLen(4,
                new int[]{1, 4, 4});
        assert 0 == minSubArrayLen(11,
                new int[]{1, 1, 1, 1, 1, 1, 1, 1});
        assert 2 == minSubArrayLen(15,
                new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11});
        assert 2 == minSubArrayLen(15,
                new int[]{5, 1, 3, 5, 10, 7, 4, 9, 2, 8});
    }

    public int minSubArrayLen(int target, int[] nums) {
        // 2024/3/1  NO.1 不会
        // 2024/3/13 NO.2 不会
        // 2024/3/22 NO.3 没思路，看不懂
        // 2024/3/25 NO.4 没做出来，思路有一点了，还得再做
        // 2024/3/30 NO.5 没做出来,肯定能理解了
        // 2024/3/31 NO.6 有思路了，没做出来
        int ans = MAX;
        int[] sums = frontSum(nums);
        int n = nums.length;

        // TODO

        return ans == MAX ? 0 : ans;
    }

}

















/*
// 方法1：
public int minSubArrayLen(int target, int[] nums) {
    int ans = MAX_VALUE;
    int n = nums.length;
    int[] sums = new int[n + 1];
    for (int i = 1; i <= n; i++)
        sums[i] = sums[i - 1] + nums[i - 1];

    for (int i = 1; i <= n; ++i) {
        int sum = target + sums[i - 1];

        // 找到元素直接返回坐标，找不到元素，就返回【需要添加的位置】的相反数 - 1
        // return -(low + 1);  key not found.
        int bound = binarySearch(sums, sum);

        // 小于0 说明没找到 target，就会返回：【应插入的位置的相反数】- 1
        if (bound < 0)
            bound = - bound - 1;

        // 大于0 说明找到了元素位置 bound
        if (bound <= n)
            ans = min(ans, bound - i + 1);
    }
    return ans == MAX_VALUE ? 0 : ans;
}

public static int[] frontSum(int[] nums) {
    int n = nums.length;
    int[] sums = new int[n + 1];
    for (int i = 1; i <= n; i++)
        sums[i] = sums[i - 1] + nums[i - 1];
    return sums;
}
*/
