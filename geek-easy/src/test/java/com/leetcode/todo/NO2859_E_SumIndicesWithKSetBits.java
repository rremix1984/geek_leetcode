package com.leetcode.todo;

import org.junit.Test;
import java.util.List;
import static com.leetcode.util.MathUtils.getArray;

/**
    [ARRAY]
    （简单）
    NO.2859 计算K位置下标对应元素的和
    给你一个下标从 0 开始的整数数组 nums 和一个整数 k 。
    请你用整数形式返回 nums 中的特定元素之 和 ，这些特定元素满足：其对应下标的二进制表示中恰存在 k 个置位。
    整数的二进制表示中的 1 就是这个整数的 置位 。
    例如，21 的二进制表示为 10101 ，其中有 3 个置位。
    示例 1：
        输入：nums = [5, 10, 1, 5, 2], k = 1
        输出：13
        解释：下标的二进制表示是：
                0 = 0002
                1 = 0012
                2 = 0102
                3 = 0112
                4 = 1002
        下标 1、2 和 4 在其二进制表示中都存在 k = 1 个置位。
        因此，答案为 nums[1] + nums[2] + nums[4] = 13 。
    示例 2：
        输入：nums = [4,3,2,1], k = 2
        输出：1
        解释：下标的二进制表示是：
                0 = 002
                1 = 012
                2 = 102
                3 = 112
        只有下标 3 的二进制表示中存在 k = 2 个置位。
        因此，答案为 nums[3] = 1 。
    提示：
        1 <= nums.length <= 1000
        1 <= nums[i] <= 105
        0 <= k <= 10
    Related Topics:位运算,数组
    思路和算法:
    解法一的时间复杂度与下标值无关，对于任意整数都需要遍历其二进制表示的全部数位。一种时间复杂度更低的解法是 Brian Kernighan 算法。
    Brian Kernighan 算法的原理是：对于任意整数 i，i & (i−1) 的结果是将 i 的二进制表示的最后一个 1 变成 0 之后的整数，即 i & (i−1) 的置位数比 i 的置位数少 1 个。对于给定的整数 i，计算 i & (i−1) 的值并将 i 的值更新为该值，直到 i 变成 0，则操作次数为 i 的置位数。
*/
@SuppressWarnings("all")
public class NO2859_E_SumIndicesWithKSetBits {

    @Test
    public void test() {
        assert 13 == sumIndicesWithKSetBits(getArray(5, 10, 1, 5, 2), 1);
        assert 1 == sumIndicesWithKSetBits(getArray(4, 3, 2, 1), 2);
    }

    public int sumIndicesWithKSetBits(List<Integer> nums, int k) {
        int sum = 0;
        return sum;
    }

}



















/*
// 方法1
public int sumIndicesWithKSetBits(List<Integer> nums, int k) {
    int sum = 0;
    int n = nums.size();
    for (int i = 0; i < n; i++) {
        int ones = 0;
        int temp = i;
        while (temp != 0) {
            temp &= temp - 1;
            ones++;
        }
        if (ones == k) {
            sum += nums.get(i);
        }
    }
    return sum;
}
*/