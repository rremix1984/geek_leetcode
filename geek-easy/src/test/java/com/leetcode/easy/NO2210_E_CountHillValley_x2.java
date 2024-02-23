/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

/**
    [ARRAY]
    (简单)
    2210. 统计数组中峰和谷的数量
         给你一个下标从0开始的整数数组nums。如果两侧距i最近的不相等邻居的值均小于nums[i] ，
         则下标i是nums中，某个峰的一部分。类似地，如果两侧距i最近的不相等邻居的值均大于nums[i]，
         则下标i是nums中某个谷的一部分。对于相邻下标i和j，如果nums[i]==nums[j]，
         则认为这两下标属于同一个峰或谷。
         注意，要使某个下标所做峰或谷的一部分，那么它左右两侧必须 都 存在不相等邻居。
         返回 nums 中峰和谷的数量。
    示例 1：
        输入：nums = {2, 4, 1, 1, 6, 5}
        输出：3
        解释：在下标 0 ：由于 2 的左侧不存在不相等邻居，所以下标 0 既不是峰也不是谷。
             在下标 1 ：4 的最近不相等邻居是 2 和 1 。由于 4 > 2 且 4 > 1 ，下标 1 是一个峰。
             在下标 2 ：1 的最近不相等邻居是 4 和 6 。由于 1 < 4 且 1 < 6 ，下标 2 是一个谷。
             在下标 3 ：1 的最近不相等邻居是 4 和 6 。由于 1 < 4 且 1 < 6 ，下标 3 符合谷的定义，但需要注意它和下标 2 是同一个谷的一部分。
             在下标 4 ：6 的最近不相等邻居是 1 和 5 。由于 6 > 1 且 6 > 5 ，下标 4 是一个峰。
             在下标 5 ：由于 5 的右侧不存在不相等邻居，所以下标 5 既不是峰也不是谷。
             共有 3 个峰和谷，所以返回 3 。
    示例 2：
        输入：nums = {6, 6, 5, 5, 4, 1}
        输出：0
        解释：在下标 0 ：由于 6 的左侧不存在不相等邻居，所以下标 0 既不是峰也不是谷。
             在下标 1 ：由于 6 的左侧不存在不相等邻居，所以下标 1 既不是峰也不是谷。
             在下标 2 ：5 的最近不相等邻居是 6 和 4 。由于 5 < 6 且 5 > 4 ，下标 2 既不是峰也不是谷。
             在下标 3 ：5 的最近不相等邻居是 6 和 4 。由于 5 < 6 且 5 > 4 ，下标 3 既不是峰也不是谷。
             在下标 4 ：4 的最近不相等邻居是 5 和 1 。由于 4 < 5 且 4 > 1 ，下标 4 既不是峰也不是谷。
             在下标 5 ：由于 1 的右侧不存在不相等邻居，所以下标 5 既不是峰也不是谷。
             共有 0 个峰和谷，所以返回 0 。
*/
public class NO2210_E_CountHillValley_x2 {

    @Test
    public void test() {
        assert 3 == countHillValley(new int[]{2, 4, 1, 1, 6, 5});
        assert 0 == countHillValley(new int[]{6, 6, 5, 5, 4, 1});
    }

    public int countHillValley(int[] nums) {
        int res = 0;
        int j = 0;
        for (int i = 1; i < nums.length - 1; i++) {
            if (nums[i] == nums[i + 1])
                continue;

            // 峰
            if (nums[i] > nums[j] && nums[i] > nums[i + 1])
                res++;

            // 谷
            if (nums[i] < nums[j] && nums[i] < nums[i + 1])
                res++;

            // 跳过谷、峰
            j = i;
        }
        return res;
    }

}













/**
// 方法1：
public int countHillValley(int[] nums) {
    int res = 0;
    // 定义一个j用于存储前一个不重复的值的下标
    for (int i = 1, j = 0; i < nums.length - 1; i++) {
        if (nums[i] == nums[i + 1])
            continue;

        if (nums[i] > nums[j] && nums[i] > nums[i + 1])
            res++;

        if  (nums[i] < nums[j] && nums[i] < nums[i + 1])
            res++;

        j = i;
    }
    return res;
}
*/