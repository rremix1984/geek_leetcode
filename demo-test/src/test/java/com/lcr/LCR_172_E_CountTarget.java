package com.lcr;

import org.junit.Test;

/**
    [ARRAY]
    （简单）
    统计目标成绩的出现次数
    某班级考试成绩按非严格递增顺序记录于整数数组 scores，请返回目标成绩 target 的出现次数。
    示例 1：
        输入: scores = [2, 2, 3, 4, 4, 4, 5, 6, 6, 8], target = 4
        输出: 3
    示例 2：
        输入: scores = [1, 2, 3, 5, 7, 9], target = 6
        输出: 0
    提示：
        0 <= scores.length <= 105
        -109 <= scores[i] <= 109
        scores 是一个非递减数组
        -109 <= target <= 109
*/
public class LCR_172_E_CountTarget {

    @Test
    public void test() {
        assert 3 == countTarget(new int[]{2, 2, 3, 4, 4, 4, 5, 6, 6, 8}, 4);
        assert 0 == countTarget(new int[]{1, 2, 3, 5, 7, 9}, 6);
    }

    public int countTarget(int[] nums, int target) {

        int leftIdx = binarySearch(nums, target, true);

        int rightIdx = binarySearch(nums, target, false) - 1;

        if (leftIdx <= rightIdx && rightIdx < nums.length
        && nums[leftIdx] == target && nums[rightIdx] == target) {
            return rightIdx - leftIdx + 1;
        }
        return 0;
    }

    public int binarySearch(int[] nums, int target, boolean lower) {
        int left = 0, right = nums.length - 1, ans = nums.length;
        while (left <= right) {
            int mid = (right - left) / 2 + left;
            if (nums[mid] > target || lower && nums[mid] >= target) {
                right = mid - 1; ans = mid; } else { left = mid + 1;
            }
        }
        return ans;
    }

}


















/*
// 方法1：
public int countTarget(int[] nums, int target) {
    int leftIdx = binarySearch(nums, target, true);
    int rightIdx = binarySearch(nums, target, false) - 1;
    if (leftIdx <= rightIdx && rightIdx < nums.length
        && nums[leftIdx] == target && nums[rightIdx] == target) {
        return rightIdx - leftIdx + 1;
    }
    return 0;
}
public int binarySearch(int[] nums, int target, boolean lower) {
    int left = 0, right = nums.length - 1, ans = nums.length;
    while (left <= right) {
        int mid = (right - left) / 2 + left;
        if (nums[mid] > target || lower && nums[mid] >= target) {
            right = mid - 1; ans = mid; } else { left = mid + 1;
        }
    }
    return ans;
}
*/