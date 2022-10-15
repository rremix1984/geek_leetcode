/**
 * copyright 2022/1/19
 */
package com.leetcode;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;

/**
    (简单)
    2099. 找到和最大的长度为 K 的子序列
        给你一个整数数组 nums 和一个整数 k 。你需要找到 nums 中长度为 k 的 子序列 ，且这个子序列的 和最大 。
        请你返回 任意 一个长度为 k 的整数子序列。
        子序列 定义为从一个数组里删除一些元素后，不改变剩下元素的顺序得到的数组。
    示例 1：
        输入：nums = [2,1,3,3], k = 2
        输出：[3,3]
        解释：子序列有最大和：3 + 3 = 6 。
    示例 2：
        输入：nums = [-1,-2,3,4], k = 3
        输出：[-1,3,4]
        解释：子序列有最大和：-1 + 3 + 4 = 6 。
    示例 3：
        输入：nums = [3,4,3,3], k = 2
        输出：[3,4]
        解释：子序列有最大和：3 + 4 = 7 。
             另一个可行的子序列为 [4, 3] 。
*/
public class NO2099_E_MaxSubsequence {

    @Test
    public void test() {
        assertArrayEquals(new int[]{3, 3}, maxSubsequence(new int[]{2, 1, 3, 3}, 2));
        assertArrayEquals(new int[]{-1, 3, 4}, maxSubsequence(new int[]{-1, -2, 3, 4},3));
        assertArrayEquals(new int[]{3, 4}, maxSubsequence(new int[]{3, 4, 3, 3},2));
    }

    public int[] maxSubsequence(int[] nums, int k) {
        int n = nums.length;
        int[][] arr = new int[n][2];
        for (int i = 0; i < n; i++) {
            arr[i][0] = i;
            arr[i][1] = nums[i];
        }
        Arrays.sort(arr, (a, b) -> b[1] - a[1]);

        int[] idxs = new int[k];
        for (int i = 0; i < k; i++) {
            idxs[i] = arr[i][0];
        }
        Arrays.sort(idxs);

        int[] res = new int[k];
        for (int i = 0; i < k; i++) {
            res[i] = nums[idxs[i]];
        }
        return res;
    }

}
