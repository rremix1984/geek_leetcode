/**
 * copyright 2022/1/19
 */
package com.leetcode.donnot;

import org.junit.Test;
import java.util.Arrays;
import static org.junit.Assert.assertArrayEquals;

/**
    (简单)
    2389. 和有限的最长子序列
        给你一个长度为 n 的整数数组 nums，和一个长度为 m 的整数数组 queries 。
        返回一个长度为 m 的数组 answer ，其中 answer[i] 是 nums 中 元素之和
        小于等于queries[i]的子序列的最大长度。
        子序列 是由一个数组删除某些元素（也可以不删除）但不改变剩余元素顺序得到的一个数组。
    示例 1：
        输入：nums = {4, 5, 2, 1},  queries = {3, 10, 21}
        输出：{2, 3, 4}
        解释：queries 对应的 answer 如下：
             - 子序列 {2, 1} 的和小于或等于 3 。可以证明满足题目要求的子序列的最大长度是 2 ，所以 answer{0} = 2 。
             - 子序列 {4, 5, 1} 的和小于或等于 10 。可以证明满足题目要求的子序列的最大长度是 3 ，所以 answer{1} = 3 。
             - 子序列 {4, 5, 2, 1} 的和小于或等于 21 。可以证明满足题目要求的子序列的最大长度是 4 ，所以 answer{2} = 4 。
    示例 2：
        输入：nums = {2, 3, 4, 5},  queries = {1}
        输出：{0}
        解释：空子序列是唯一一个满足元素和小于或等于 1 的子序列，所以 answer[0] = 0 。
*/
public class NO2389_E_AnswerQueries {

    @Test
    public void test() {
        assertArrayEquals(new int[]{2, 3, 4},
                answerQueries(new int[]{4, 5, 2, 1}, new int[]{3, 10, 21}));
        assertArrayEquals(new int[]{0},
                answerQueries(new int[]{2, 3, 4, 5}, new int[]{1}));
    }

    public int[] answerQueries(int[] nums, int[] queries) {
        int m = queries.length;
        int[] res = new int[m];

        // 排序 + 前缀和 + 二分
        Arrays.sort(nums);  // 排序
        int n = nums.length;
        int[] sum = new int[n + 1];

        for (int i = 0; i < n; i++)
            sum[i + 1] = sum[i] + nums[i];

        // 查找sum中首个小于等于 queries[i] 的最大索引
        for (int i = 0; i < m; i++) {
            int t = queries[i];
            int l = 0;
            int r = n;
            while (l < r) {
                int mid = l + (r - l + 1) / 2;
                if (sum[mid] > t)
                    r = mid - 1;
                else
                    l = mid;
            }
            res[i] = l;
        }
        return res;
    }

}
