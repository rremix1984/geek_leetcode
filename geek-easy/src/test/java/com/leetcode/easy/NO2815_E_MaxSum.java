package com.leetcode.easy;

import lombok.var;
import org.junit.Test;
import java.util.Arrays;

/**
    [ARRAY]
    （简单）
    NO.2815 数组中的最大数对和
    给你一个下标从0开始的整数数组nums。请你从nums中找出和最大的一对数，
    且这两个数数位上最大的数字相等。返回最大和，如果不存在满足题意的数字对，返回-1。

    示例 1：
        输入：nums = [51,71,17,24,42]
        输出：88
        解释：
            i = 1 和 j = 2 ，nums[i] 和 nums[j] 数位上最大的数字相等，且这一对的总和 71 + 17 = 88 。
            i = 3 和 j = 4 ，nums[i] 和 nums[j] 数位上最大的数字相等，且这一对的总和 24 + 42 = 66 。
            可以证明不存在其他数对满足数位上最大的数字相等，所以答案是 88 。
    示例 2：
        输入：nums = [1,2,3,4]
        输出：-1
        解释：不存在数对满足数位上最大的数字相等。
    提示：

        2 <= nums.length <= 100
        1 <= nums[i] <= 104
    Related Topics: 数组
*/
public class NO2815_E_MaxSum {

    @Test
    public void test() {
        assert 88 == maxSum(new int[]{51, 71, 17, 24, 42});
        assert -1 == maxSum(new int[]{1, 2, 3, 4});
    }

    public int maxSum(int[] nums) {
        int ans = -1;
        int[] maxVal = new int[10];
        Arrays.fill(maxVal, Integer.MIN_VALUE);
        for (int v : nums) {
            int maxD = 0;
            for (int x = v; x > 0; x /= 10) maxD = Math.max(maxD, x % 10);
            ans = Math.max(ans, v + maxVal[maxD]);
            maxVal[maxD] = Math.max(maxVal[maxD], v);
        }
        return ans;
    }

}
