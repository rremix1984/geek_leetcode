/**
 * copyright 2022/1/19
 */
package com.leetcode;

import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;

import static java.lang.Math.max;

/**
    (简单)
    1235. 规划兼职工作
        你打算利用空闲时间来做兼职工作赚些零花钱。
        这里有 n 份兼职工作，每份工作预计从 startTime[i] 开始到 endTime[i] 结束，
        报酬为 profit[i]。给你一份兼职工作表，包含开始时间 startTime，结束时间 endTime
        和预计报酬 profit 三个数组，请你计算并返回可以获得的最大报酬。
        注意，时间上出现重叠的 2 份工作不能同时进行。
        如果你选择的工作在时间 X 结束，那么你可以立刻进行在时间 X 开始的下一份工作。
    示例 1：
        输入：startTime = {1, 2, 3, 3},  endTime = {3, 4, 5, 6},
             profit = {50, 10, 40, 70}
        输出：120
        解释：我们选出第 1 份和第 4 份工作，
             时间范围是 {1 - 3} + {3 - 6}，共获得报酬 120 = 50 + 70。
    示例 2：
        输入：startTime = {1, 2, 3, 4, 6},  endTime = {3, 5, 10, 6, 9},
             profit = {20, 20, 100, 70, 60}
        输出：150
        解释：我们选择第 1，4，5 份工作。
             共获得报酬 150 = 20 + 70 + 60。
    示例 3：
        输入：startTime = {1, 1, 1},  endTime = {2, 3, 4},  profit = {5, 6, 4}
        输出：6
    提示：
        1 <= startTime.length == endTime.length == profit.length <= 5 * 10 ^ 4
        1 <= startTime[i] < endTime[i] <= 10 ^ 9
        1 <= profit[i] <= 10^4
                     ______________
                    | profit = 40 |
             _______|_____________|
            | profit = 10 |
     _______|_____________|______________
    | profit = 50   |   profit = 70     |
  --|--------------|--------------------|--
    1      2        3     4      5      6

    方法一：动态规划 + 二分查找
        由题意知，兼职工作由开始时间startTime，结束时间endTime和预计报酬profit三种属性表示。
    我们首先将兼职工作按结束时间endTime从小到大进行排序。
    使用 dp[i] 表示前 i 份兼职工作可以获得的最大报酬，即区间 [0, i - 1] 的所有兼职工作
    可以获得的最大报酬。初始时 dp[0]=0，表示没有兼职工作时报酬为 0。
    那么对于 i>0，根据第 i - 1 份兼职工作是否被选择，我们有以下转移方程：
      dp[i] = max(dp[i − 1], dp[k] + profit[i − 1])
    其中 k 表示满足结束时间小于等于第 i - 1 份工作开始时间的兼职工作数量
    （因为兼职工作是按照结束时间从小到大进行排序的，所以选择第 i−1 份兼职工作后，
    我们只能继续选择前 k 份兼职工作），可以通过二分查找获得。
*/
public class NO1235_H_JobScheduling {

    @Test
    public void test() {
        assert 120 == jobScheduling(new int[]{1, 2, 3, 3}, 
                new int[]{3, 4, 5, 6},  new int[]{50, 10, 40, 70});
        assert 150 == jobScheduling(new int[]{1, 2, 3, 4, 6}, 
                new int[]{3, 5, 10, 6, 9},  new int[]{20, 20, 100, 70, 60});
        assert 6 == jobScheduling(new int[]{1, 1, 1}, 
                new int[]{2, 3, 4}, new int[]{5, 6, 4});
    }

    public int jobScheduling(int[] startTime, int[] endTime, int[] profit) {
        int n = startTime.length;
        int[][] jobs = new int[n][];
        for (int i = 0; i < n; i++)
            jobs[i] = new int[]{startTime[i], endTime[i], profit[i]};

        Arrays.sort(jobs, Comparator.comparingInt(a -> a[1]));
        int[] dp = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            int k = binarySearch(jobs, i - 1, jobs[i - 1][0]);
            dp[i] = max(dp[i - 1], dp[k] + jobs[i - 1][2]);
        }
        return dp[n];
    }

    public int binarySearch(int[][] jobs, int right, int target) {
        int left = 0;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (jobs[mid][1] > target)
                right = mid;
            else
                left = mid + 1;
        }
        return left;
    }

}
