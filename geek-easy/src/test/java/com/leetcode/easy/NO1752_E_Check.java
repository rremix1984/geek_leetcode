/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

/**
    [ARRAY] |||
    (简单)
    1752. 检查数组是否经排序和轮转得到
        给你一个数组nums。nums的源数组中，所有元素与nums相同，但按非递减顺序排列。
        如果nums能够由源数组轮转若干位置（包括0个位置）得到，
        则返回true；否则，返回false。源数组中可能存在重复项。
        注意：我们称数组A在轮转x个位置后得到长度相同的数组B，
        当它们满足 A[i] == B[(i+x) % A.length]，其中%为取余运算。
    示例 1：
        输入：nums = {3, 4, 5, 1, 2}
        输出：true
        解释：{1, 2, 3, 4, 5} 为有序的源数组。
        可以轮转 x = 3 个位置，使新数组从值为 3 的元素开始：{3, 4, 5, 1, 2} 。
    示例 2：
        输入：nums = {2, 1, 3, 4}
        输出：false
        解释：源数组无法经轮转得到 nums 。
    示例 3：
        输入：nums = {1, 2, 3}
        输出：true
        解释：{1, 2, 3} 为有序的源数组。
        可以轮转 x = 0 个位置（即不轮转）得到 nums 。
*/
public class NO1752_E_Check {

    @Test
    public void test() {
        assert check(new int[]{3, 4, 5, 1, 2});
        assert !check(new int[]{2, 1, 3, 4});
        assert check(new int[]{1, 2, 3});
        assert check(new int[]{6, 10, 6});
        assert !check(new int[]{3, 2, 1});
    }

    public boolean check(int[] nums) {
        // 2024/3/1 NO.3
        // 2024/3/10 NO.4
        // 2024/3/30 NO.5
        int cnt = 0;
        for (int i = 0; i < nums.length - 1; i++) {
            if (nums[i] > nums[i + 1]) {
                if (cnt++ > 0)
                    return false;
            }
        }
        return cnt == 0 || nums[0] >= nums[nums.length - 1];
    }

}














/*
// 方法1：
public boolean check(int[] nums) {
    int cnt = 0;
    int n = nums.length;
    for (int i = 1; i < n; i++) {
        if (nums[i] < nums[i - 1]) {
            if (cnt > 0)
                return false;
            cnt++;
        }
    }
    return cnt == 0 || nums[0] > nums[n - 1];
}

// 方法2：
public boolean check(int[] nums) {
    int cnt = 0;
    for (int i = 0; i < nums.length - 1; i++) {
        if (nums[i] > nums[i + 1]) {
            if (cnt++ > 0)
                return false;
        }
    }
    return cnt == 0 || nums[0] >= nums[nums.length - 1];
}
*/