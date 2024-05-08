package com.leetcode.todo;

import org.junit.Test;

/**
    [ARRAY]
    （简单）
    NO.3046 分割数组
    给你一个长度为 偶数 的整数数组 nums 。你需要将这个数组分割成 nums1 和 nums2 两部分，要求：
      1）nums1.length == nums2.length == nums.length / 2 。
      2）nums1 应包含 互不相同 的元素。
    nums2也应包含 互不相同 的元素。
    如果能够分割数组就返回 true ，否则返回 false 。
    示例 1：
        输入：nums = [1,1,2,2,3,4]
        输出：true
        解释：分割 nums 的可行方案之一是 nums1 = [1,2,3] 和 nums2 = [1,2,4] 。
    示例 2：
        输入：nums = [1,1,1,1]
        输出：false
        解释：分割 nums 的唯一可行方案是 nums1 = [1,1] 和 nums2 = [1,1] 。但 nums1 和 nums2 都不是由互不相同的元素构成。因此，返回 false 。
    提示：
        1 <= nums.length <= 100
        nums.length % 2 == 0
        1 <= nums[i] <= 100
    Related Topics:数组,哈希表,计数
*/
@SuppressWarnings("all")
public class NO3046_E_IsPossibleToSplit {

    @Test
    public void test() {
        assert isPossibleToSplit(new int[]{1, 1, 2, 2, 3, 4});
        assert !isPossibleToSplit(new int[]{1, 1, 1, 1});
    }

    public boolean isPossibleToSplit(int[] nums) {
        return true;
    }

}



















/*
// 方法1：
public boolean isPossibleToSplit(int[] nums) {
    // 使用数组统计元素出现次数，1 <= nums[i] <= 100
    int[] nCntArray = new int[101];
    for (int num : nums) {
        if (++nCntArray[num] > 2)
            // 要保证两个子数组内的元素都互不相同
            return false;

    }
    return true;
}
*/