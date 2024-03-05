/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;
import java.util.Arrays;
import static java.lang.Math.max;

/**
    [ARRAY] ||
    (简单)
    594. 最长和谐子序列
        和谐数组是指一个数组里元素的最大值和最小值之间的差别正好是1。
        现在，给你一个整数数组nums，请你在所有可能的子序列中找到
        最长的和谐子序列的长度。数组的子序列是一个由数组派生出来的序列，
        它可以通过删除一些元素或不删除元素、且不改变其余元素的顺序而得到。
    示例 1：
        输入：nums = {1, 3, 2, 2, 5, 2, 3, 7}
        输出：5
        解释：最长的和谐子序列是 {3, 2, 2, 2, 3}
    示例 2：
        输入：nums = {1, 2, 3, 4}
        输出：2
    示例 3：
        输入：nums = {1, 1, 1, 1}
        输出：0
*/
public class NO594_E_FindLHS {

    @Test
    public void test() {
        assert 5 == findLHS(new int[]{1, 3, 2, 2, 5, 2, 3, 7});
        assert 2 == findLHS(new int[]{1, 2, 3, 4});
        assert 0 == findLHS(new int[]{1, 1, 1, 1});
        assert 5 == findLHS(new int[]{1, 1, 1, 1, 0});
        assert 4 == findLHS(new int[]{1, 2, 2, 1, 3, 4});
        assert 0 == findLHS(new int[]{1, 3, 5, 7});
    }

    public int findLHS(int[] nums) {
        // 2024/2/23 NO.3 排序和双指针的方法来寻找最长的和谐子序列
        // 2024/3/5  NO.4
        int max = 0;

        return max;
    }

}

















/*
// 方法1：
public int findLHS(int[] nums) {
    Arrays.sort(nums);
    int begin = 0;
    int res = 0;
    for (int i = 0; i < nums.length; i++) {
        // 大于1 就从 begin 开始重新计数
        while (nums[i] - nums[begin] > 1)
            begin++;

        // 如果差值等于1，最长的长度就与最终结果 len 比较一下
        // i - begin + 1 就是当前坐标 和 begin 的差就是这个长度
        if (nums[i] - nums[begin] == 1)
            res = max(res, i - begin + 1);
    }
    return res;
}
*/