/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static com.leetcode.util.LogUtil.info;

/**
    （简单）
    1. 两数之和
        给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出 和为目标值 target  的那 两个 整数，并返回它们的数组下标。
        你可以假设每种输入只会对应一个答案。但是，数组中同一个元素在答案里不能重复出现。
        你可以按任意顺序返回答案。
    示例 1：
        输入：nums = [2, 7, 11, 15], target = 9
        输出：[0, 1]
        解释：因为 nums[0] + nums[1] == 9 ，返回 [0, 1] 。
    示例 2：
        输入：nums = [3,2,4], target = 6
        输出：[1, 2]
    示例 3：
        输入：nums = [3,3], target = 6
        输出：[0, 1]
*/
public class NO1_E_TwoSum_x2 {

    @Test
    public void test() {
        info(twoSum(new int[]{2, 2, 4}, 6));// [1, 2]
        info(twoSum(new int[]{2, 7, 11, 15}, 9));// [0, 1]
        info(twoSum(new int[]{3, 2, 4}, 6));// [1, 2]
        info(twoSum(new int[]{3, 3},6));// [0, 1]
    }

    public int[] twoSum(int[] nums, int target) {
        return new int[0];
    }
}











/**
// 方法1：暴力法
public int[] twoSum(int[] nums, int target) {
    int[] a = new int[2];
    int numSize = nums.length;
    for (int i = 0; i < numSize - 1; i++) {
        for (int j = i + 1; j < numSize; j++) {
            if (nums[i] + nums[j] == target) {
                a[0] = i;
                a[1] = j;
                return a;
            }
        }
    }
    return new int[0];
}

// 方法2：HashMap方法
public int[] twoSum(int[] nums, int target) {
    // map 是nums数组中 v 和 k 的集合
    // map.put(v, k)
    Map<Integer, Integer> map = new HashMap<>();
    for (int i = 0; i < nums.length; i++) {
        int comp = target - nums[i];
        if (map.containsKey(comp))
            return new int[]{map.get(comp), i};

        map.put(nums[i], i);
    }
    return new int[0];
}
*/