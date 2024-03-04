package com.leetcode.easy;

import org.junit.Test;

import static java.lang.Math.max;

/**
    [ARRAY]
    （简单）
    NO.2765 最长交替子数组
        给你一个下标从 0 开始的整数数组 nums 。如果 nums 中长度为 m 的子数组 s 满足以下条件，我们称它是一个 交替子数组 ：
        m 大于 1 。
        s1 = s0 + 1 。
        下标从 0 开始的子数组 s 与数组 [s0, s1, s0, s1,...,s(m-1) % 2] 一样。也就是说，s1 - s0 = 1 ，s2 - s1 = -1 ，s3 - s2 = 1 ，s4 - s3 = -1 ，以此类推，直到 s[m - 1] - s[m - 2] = (-1)m 。
        请你返回 nums 中所有 交替 子数组中，最长的长度，如果不存在交替子数组，请你返回 -1 。
        子数组是一个数组中一段连续 非空 的元素序列。
    示例 1：
        输入：nums = [2, 3, 4, 3, 4]
        输出：4
        解释：交替子数组有 [3,4] ，[3,4,3] 和 [3,4,3,4] 。最长的子数组为 [3,4,3,4] ，长度为4 。
    示例 2：
        输入：nums = [4, 5, 6]
        输出：2
        解释：[4,5] 和 [5,6] 是仅有的两个交替子数组。它们长度都为 2 。
    提示：
        2 <= nums.length <= 100
        1 <= nums[i] <= 104
        Related Topics:数组,枚举
 */
public class NO2765_E_AlternatingSubarray {

    @Test
    public void test() {
        assert 4 == alternatingSubarray(new int[]{2, 3, 4, 3, 4});
        assert 2 == alternatingSubarray(new int[]{4, 5, 6});
    }

    public int alternatingSubarray(int[] nums) {
        int n = nums.length;
        int ans = 0;    // 结果，最长交替子数组长度
        int m = 1;      // 统计交替子数组长度
        int diff = 1;   // 交替值
        for (int i = 1; i < n; i++) {
            if (nums[i] - nums[i - 1] == diff) {
                // 当前元素和前一个元素满足交替条件，长度累加，交替值翻转
                m++;
                diff *= -1;
            } else if (nums[i] - nums[i - 1] == 1) {
                // 否则不满足交替，但是和前一位元素满足s1-s0=1，[i-1, i]已经构成一个交替子数组，长度为2，从这里开始继续寻找
                m = 2;
                diff = -1;
            } else {
                // 否则不满足交替条件，且与前一位也构成不了交替数组起始条件，那么就把nums[i]当作s0查找下一位
                m = 1;
                diff = 1;
            }
            ans = max(m, ans);
        }
        return ans == 1 ? -1 : ans;     // 如果最长交替子数组长度为1，即没有交替子数组
    }

}
