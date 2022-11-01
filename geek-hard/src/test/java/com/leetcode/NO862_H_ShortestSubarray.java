/**
 * copyright 2022/1/19
 */
package com.leetcode;

import org.junit.Test;

import java.util.ArrayDeque;
import java.util.Deque;

import static java.lang.Math.min;

/**
    (简单)
    862. 和至少为 K 的最短子数组
        给你一个整数数组nums和一个整数k，找出nums中和至少为k的最短非空子数组，
        并返回该子数组的长度。如果不存在这样的子数组，返回-1。
        子数组是数组中【连续】的一部分。
    示例 1：
        输入：nums = [1], k = 1
        输出：1
    示例 2：
        输入：nums = [1,2], k = 4
        输出：-1
    示例 3：
        输入：nums = [2,-1,2], k = 3
        输出：3
    提示：
        1 <= nums.length <= 105
        -105 <= nums[i] <= 105
        1 <= k <= 109
*/
public class NO862_H_ShortestSubarray {

    @Test
    public void test() {
        assert 1 == shortestSubarray(new int[]{1}, 1);
        assert -1 == shortestSubarray(new int[]{1, 2}, 4);
        assert 3 == shortestSubarray(new int[]{2, -1, 2}, 3);
    }

    public int shortestSubarray(int[] nums, int k) {
        int n = nums.length;
        long[] preSumArr = new long[n + 1];
        for (int i = 0; i < n; i++)
            preSumArr[i + 1] = preSumArr[i] + nums[i];

        int res = n + 1;
        Deque<Integer> queue = new ArrayDeque<>();
        for (int i = 0; i <= n; i++) {
            long curSum = preSumArr[i];
            while (!queue.isEmpty() && curSum - preSumArr[queue.peekFirst()] >= k)
                res = min(res, i - queue.pollFirst());

            while (!queue.isEmpty() && preSumArr[queue.peekLast()] >= curSum)
                queue.pollLast();

            queue.offerLast(i);
        }
        return res < n + 1 ? res : -1;
    }

}
