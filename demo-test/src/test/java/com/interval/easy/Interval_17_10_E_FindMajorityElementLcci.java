/**
 * copyright 2022/1/19
 */
package com.interval.easy;

import org.junit.Test;

import java.util.Arrays;

import static com.leetcode.util.LogUtil.info;

/**
    [ARRAY] |
    (简单)
    面试题 17.10. 主要元素
        数组中占比【超过一半】的元素称之为主要元素。给你一个【整数】数组，
        找出其中的主要元素。若没有则返回-1。
        请设计时间复杂度为 O(N) 、空间复杂度为 O(1) 的解决方案。
    示例 1：
        输入：{1, 2, 5, 9, 5, 9, 5, 5, 5}
        输出：5
    示例 2：
        输入：{3, 2}
        输出：-1
    示例 3：
        输入：{2, 2, 1, 1, 1, 2, 2}
        输出：2
*/
public class Interval_17_10_E_FindMajorityElementLcci {

    @Test
    public void test() {
        assert  5 == majorityElement(
            new int[]{1, 2, 5, 9, 5, 9, 5, 5, 5});
        assert -1 == majorityElement(
            new int[]{3, 2});
        assert  2 == majorityElement(
            new int[]{2, 2, 1, 1, 1, 2, 2});
        assert 3 == majorityElement(
            new int[]{1, 3, 2, 3, 4, 3, 5, 3, 6, 3, 3});
    }

    public int majorityElement(int[] nums) {
        // 2024/3/4 NO.1 此类问题有固定模式
        return -1;
    }

}



















/*
// 方法1：
public int majorityElement(int[] nums) {
    int major = nums[0];
    int count = 1;
    for (int i = 1; i < nums.length; i++) {
        if (major == nums[i]) {
            count++;
        } else if (--count == 0) {
            major = nums[i];
            count = 1;
        }
    }

    int c = 0;
    for (int num : nums)
        if (num == major)
            c++;

    if ((nums.length / c) > 1)
        return -1;

    return major;
}
*/