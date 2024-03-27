/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;

/**
    [ARRAY] |||
    (简单)
    1. 两数之和
        给定一个【升序】排列的整数数组 nums 和一个整数目标值 target，请你在
        该数组中找出 和为目标值 target  的那 两个 整数，并返回它们的数组下标。
        你可以假设每种输入只会对应一个答案。但是，数组中同一个元素在答案里不能
        重复出现。你可以按任意顺序返回答案。
    示例 1：
        输入：nums = [2, 7, 11, 15], target = 9
        输出：[0, 1]
        解释：因为 nums[0] + nums[1] == 9 ，返回 [0, 1] 。
    示例 2：
        输入：nums = [2, 3, 4], target = 6
        输出：[0, 2]
*/
public class NO001_II_E_TwoSumII {

    @Test
    public void test() {
        assertArrayEquals(new int[]{0, 1},
                twoSum( 9, 2, 7, 11, 15));
        assertArrayEquals(new int[]{0, 2},
                twoSum( 6, 2, 3, 4));
        assertArrayEquals(new int[]{3, 5},
                twoSum(10, 1, 2, 3, 4, 5, 6));
        assertArrayEquals(new int[]{1, 5},
                twoSum(16, 2, 4, 6, 8, 10, 12));
    }

    public int[] twoSum(int target, int... numbers) {
        // 2024/3/25 NO.2 一开始没思路，后来双指针、二分查找出来了
        // 2024/3/27 NO.3 一遍过
        return new int[]{0};
    }

}











/*
// 方法1：二分法
public static int[] twoSum(int[] numbers, int target) {
    for (int i = 0; i < numbers.length; i++) {
        int low = i;
        int high = numbers.length - 1;
        while (low <= high) {
            int mid = (high - low) / 2 + low;
            if (numbers[mid] == target - numbers[i]) {
                return new int[]{i, mid};
            } else if (numbers[mid] > target - numbers[i]) {// y 在左边
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
    }
    return new int[]{0};
}


// 方法2：双指针法（推荐）
public static int[] twoSum(int[] numbers, int target) {
    int low = 0;
    int high = numbers.length - 1;
    while (low < high) {
        int sum = numbers[low] + numbers[high];
        if (sum > target) {
            high--;
        } else if (sum < target) {
            low++;
        } else {
            return new int[]{low, high};
        }
    }
    return new int[]{0};
}
*/