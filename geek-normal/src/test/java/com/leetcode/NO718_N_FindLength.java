package com.leetcode;

import org.junit.Test;

/**
    [ARRAY]
    [DP]
    (中等)
    NO.718 最长重复子数组
    给两个整数数组nums1和nums2，返回两个数组中公共的、长度最长的子数组的长度。
    示例 1：
        输入：nums1 = [1, 2, 3, 2, 1], nums2 = [3, 2, 1, 4, 7]
        输出：3
        解释：长度最长的公共子数组是 [3,2,1] 。
    示例 2：
        输入：nums1 = [0, 0, 0, 0, 0], nums2 = [0, 0, 0, 0, 0]
        输出：5
        提示：
            1 <= nums1.length, nums2.length <= 1000
            0 <= nums1[i], nums2[i] <= 100
    Related Topics:数组,二分查找,动态规划,滑动窗口,哈希函数,滚动哈希
*/
@SuppressWarnings("all")
public class NO718_N_FindLength {

    @Test
    public void test() {
        assert 3 == findLength(
                new int[]{1, 2, 3, 2, 1},
                new int[]{3, 2, 1, 4, 7});
        assert 5 == findLength(
                new int[]{0, 0, 0, 0, 0},
                new int[]{0, 0, 0, 0, 0});
    }

    public int findLength(int[] A, int[] B) {
        // 2024/3/16 NO.1
        int ans = 0;

        return ans;
    }

}

















/*
// 方法1：
public int findLength(int[] A, int[] B) {
    int i, b;
    int len = 0;
    // 建立一个二维表。
    int[][] dp = new int[A.length + 1][B.length + 1];
    for (i = 1; i <= A.length; i++) {
        for (b = 1; b <= B.length; b++) {
            // 如果两个元素相等，就看他前面一个元素匹配的结果。
            if (A[i - 1] == B[b - 1])
                dp[i][b] = dp[i - 1][b - 1] + 1;

            // 这里是找最长得长度
            if (dp[i][b] > len)
                len = dp[i][b];
        }
    }
    return len;
}

// 方法2：
public int findLength(int[] A, int[] B) {
    // 2024/3/16 NO.1
    int ans = 0;
    // 建立一个二维表。
    int[][] dp = new int[A.length + 1][B.length + 1];
    for (int i = 1; i <= A.length; i++)
        for (int j = 1; j <= B.length; j++) {
            // 如果两个元素相等，就看他前面一个元素匹配的结果。
            if (A[i - 1] == B[j - 1])
                dp[i][j] = dp[i - 1][j - 1] + 1;

            // 这里是找最长得长度
            if (dp[i][j] > ans)
                ans = dp[i][j];
        }
    return ans;
}
*/