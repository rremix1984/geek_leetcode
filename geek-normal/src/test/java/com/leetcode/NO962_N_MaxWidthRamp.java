package com.leetcode;

import org.junit.Test;
import java.util.Comparator;

import static com.leetcode.util.MathUtils.MAX;
import static com.leetcode.util.SystemUtil.printArr;
import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.util.Arrays.sort;

/**
    [ARRAY] |||
    (中等)
    NO.962 最大宽度坡
    给定一个整数数组 A，坡是元组 (i, j)，其中 i < j 且 A[i] <= A[j]。
    这样的坡的宽度为 j - i。
    找出 A 中的坡的最大宽度，如果不存在，返回 0 。
    示例 1：
        输入：[6, 0, 8, 2, 1, 5]
        输出：4
        解释：
        最大宽度的坡为 (i, j) = (1, 5): A[1] = 0 且 A[5] = 5.
    示例 2：

        输入：[9, 8, 1, 0, 1, 9, 4, 0, 4, 1]
        输出：7
        解释：
        最大宽度的坡为 (i, j) = (2, 9): A[2] = 1 且 A[9] = 1.
    提示：
        2 <= A.length <= 50000
        0 <= A[i] <= 50000
    Related Topics:栈,数组,单调栈
*/
@SuppressWarnings("all")
public class NO962_N_MaxWidthRamp {

    @Test
    public void test() {
        assert 4 == maxWidthRamp(new int[]{6, 0, 8, 2, 1, 5});
        assert 7 == maxWidthRamp(new int[]{9, 8, 1, 0, 1, 9, 4, 0, 4, 1});
    }

    public int maxWidthRamp(int[] nums) {
        // 2024/3/19 NO.2 挺难想的，需要琢磨
        // 2024/3/22 NO.3 没思路，琢磨不出来
        int len = 0;

        return len;
    }

}














/*
public int maxWidthRamp(int[] nums) {
    // 2024/3/19 NO.2
    // 兼容结果为0的场景
    int ans = 0;

    if (nums == null || nums.length == 0)
        return 0;

    int n = nums.length;
    Integer[] dict = new Integer[n];

    for (int i = 0; i < n; i++)
        dict[i] = i;

    // 这个方法需要看懂
    sort(dict, Comparator.comparing(i -> nums[i]));

    int min = n;
    for (int i : dict) {
        ans = max(ans, i - min);
        min = min(min, i);
    }

    return ans;
}
*/
