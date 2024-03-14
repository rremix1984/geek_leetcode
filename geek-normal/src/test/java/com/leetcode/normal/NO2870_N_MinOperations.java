package com.leetcode.normal;

import org.junit.Test;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.leetcode.util.MathUtils.getDict;

/**
    [ARRAY]
    (中等)
    NO.2870 使数组为空的最少操作次数
    给你一个下标从 0 开始的正整数数组 nums 。
    你可以对数组执行以下两种操作 任意次 ：
      1）从数组中选择两个值相等的元素，并将它们从数组中删除。
      2）从数组中选择三个值相等的元素，并将它们从数组中删除。
    请你返回使数组为空的最少操作次数，如果无法达成，请返回 -1 。
    示例 1：
        输入：nums = [2, 3, 3, 2, 2, 4, 2, 3, 4]
        输出：4
        解释：我们可以执行以下操作使数组为空：
                - 对下标为 0 和 3 的元素执行第一种操作，得到 nums = [3,3,2,4,2,3,4] 。
                - 对下标为 2 和 4 的元素执行第一种操作，得到 nums = [3,3,4,3,4] 。
                - 对下标为 0 ，1 和 3 的元素执行第二种操作，得到 nums = [4,4] 。
                - 对下标为 0 和 1 的元素执行第一种操作，得到 nums = [] 。
        至少需要 4 步操作使数组为空。
    示例 2：
        输入：nums = [2, 1, 2, 2, 3, 3]
        输出：-1
        解释：无法使数组为空。
    提示：
        2 <= nums.length <= 105
        1 <= nums[i] <= 106
    Related Topics:贪心,数组,哈希表,计数
    题解：
    由于题目要求把每个元素尽量变小，而这里的操作是对3取余的操作，所以每3个元素可以减少一次
    （例如，4 -> 1，5 -> 2，6 -> 0，总共减少了3次，对应于 (3+2)/3 = 1.67，但实际上只需要1次操作）。
    然而，为了处理不能被3整除的情况（如2个元素或1个元素），加上2后再除以3可以确保至少进行一次操作，
    并且能够更准确地反映接近3的倍数的次数。假设 j 是数组中某个值出现的次数，那么 (j + 2) / 3
    就是将这些数值全部变为非负整数所需的最小操作次数。
*/
public class NO2870_N_MinOperations {

    @Test
    public void test() {
        assert 4 == minOperations(new int[]{2,3,3,2,2,4,2,3,4});
        assert -1 == minOperations(new int[]{2,1,2,2,3,3});
    }

    public int minOperations(int[] nums) {
        // 2024/3/14 NO.1
        int ans = 0;
        return ans;
    }

}





















/*
// 方法1：
public int minOperations(int[] nums) {
    // 2024/3/14 NO.1
    int ans = 0;
    int[] dict = getDict(1024, nums);
    for (int j : dict) {
        if (j == 0)
            continue;

        if (j == 1)
            return -1;

        ans += (j + 2) / 3;
    }
    return ans;
}
*/