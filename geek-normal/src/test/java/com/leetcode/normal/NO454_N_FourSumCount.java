package com.leetcode.normal;

import org.junit.Test;
import java.util.HashMap;
import java.util.Map;

/**
    [ARRAY]
    (中等)
    NO.454 四数相加 II
    给你四个整数数组 nums1、nums2、nums3 和 nums4 ，数组长度都是 n ，
    请你计算有多少个元组 (i, j, k, l) 能满足：
     1）0 <= i, j, k, l < n
     2）nums1[i] + nums2[j] + nums3[k] + nums4[l] == 0
    示例 1：
        输入：nums1 = [1, 2], nums2 = [-2, -1], nums3 = [-1, 2], nums4 = [0, 2]
        输出：2
        解释：
            两个元组如下：
            1. (0, 0, 0, 1) -> nums1[0] + nums2[0] + nums3[0] + nums4[1] = 1 + (-2) + (-1) + 2 = 0
            2. (1, 1, 0, 0) -> nums1[1] + nums2[1] + nums3[0] + nums4[0] = 2 + (-1) + (-1) + 0 = 0
    示例 2：
        输入：nums1 = [0], nums2 = [0], nums3 = [0], nums4 = [0]
        输出：1
    提示：
        n == nums1.length
        n == nums2.length
        n == nums3.length
        n == nums4.length
        1 <= n <= 200
        -228 <= nums1[i], nums2[i], nums3[i], nums4[i] <= 228
    Related Topics:数组,哈希表
*/
public class NO454_N_FourSumCount {

    @Test
    public void test() {
        assert 2 == fourSumCount(new int[]{1, 2},
                                 new int[]{-2, -1},
                                 new int[]{-1, 2},
                                 new int[]{0, 2});
        assert 1 == fourSumCount(new int[]{0},
                                 new int[]{0},
                                 new int[]{0},
                                 new int[]{0});
    }

    public int fourSumCount(int[] nums1, int[] nums2, int[] nums3, int[] nums4) {
        // 2024/3/15 NO.1
        Map<Integer, Integer> map = new HashMap<>();
        int res = 0;
        for (int i : nums1)
            for (int j : nums2)
                map.put(i + j, map.getOrDefault(i + j, 0) + 1);

        for (int i : nums3)
            for (int j : nums4)
                res += map.getOrDefault(-i - j, 0);

        return res;
    }

}















/*
// 方法1：
public int fourSumCount(int[] nums1, int[] nums2, int[] nums3, int[] nums4) {
    Map<Integer, Integer> map = new HashMap<>();
    int res = 0;
    for (int i : nums1)
        for (int j : nums2)
            map.put(i + j, map.getOrDefault(i + j, 0) + 1);

    for (int i : nums3)
        for (int j : nums4)
            res += map.getOrDefault(-i - j, 0);

    return res;
}
*/