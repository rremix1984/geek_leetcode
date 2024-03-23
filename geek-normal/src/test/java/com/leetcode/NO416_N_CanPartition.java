package com.leetcode;

import org.junit.Test;
import java.util.Arrays;

import static java.util.Arrays.stream;

/**
    [ARRAY]
    (中等)
    NO.416 分割等和子集
    给你一个只包含正整数的非空数组nums。请你判断是否可以将这个数组分割成两个子集，
    使得两个子集的元素和相等。
    示例 1：
        输入：nums = [1, 5, 11, 5]
        输出：true
        解释：数组可以分割成 [1, 5, 5] 和 [11] 。
    示例 2：
        输入：nums = [1, 2, 3, 5]
        输出：false
        解释：数组不能分割成两个元素和相等的子集。
    提示：
        1 <= nums.length <= 200
        1 <= nums[i] <= 100
    Related Topics:数组,动态规划
*/
public class NO416_N_CanPartition {

    @Test
    public void test() {
        assert canPartition(new int[]{1, 5, 11, 5});
        assert !canPartition(new int[]{1, 2, 3, 5});
    }

    public boolean canPartition(int[] nums) {
        // 2024/3/12 NO.1
        final int n = nums.length;
        int target = stream(nums).sum();
        if (target % 2 != 0)
            return false;

        target = target >> 1;
        boolean[][] partition = new boolean[n][target + 1];
        for (int i = 0; i < n; i++)
            partition[i][0] = true;

        for (int i = 1; i < n; i++)
            for (int j = 1; j <= target; j++)
                partition[i][j] = partition[i - 1][j]
                        || (j >= nums[i] && partition[i - 1][j - nums[i]]);

        return partition[n - 1][target];
    }

}
