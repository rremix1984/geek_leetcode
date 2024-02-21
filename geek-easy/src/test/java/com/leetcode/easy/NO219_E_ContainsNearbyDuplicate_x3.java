/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

/**
    [ARRAY]
    (简单)
    219. 存在重复元素 II
        给你一个整数数组 nums 和一个整数 k ，判断数组中是否存在两个 不同的索引 i 和 j ，
        满足 nums[i] == nums[j] 且 abs(i - j) <= k 。如果存在，返回 true ；
        否则，返回 false 。
    示例 1：
        输入：nums = {1, 2, 3, 1},  k = 3
        输出：true
    示例 2：
        输入：nums = {1, 0, 1, 1},  k = 1
        输出：true
    示例 3：
        输入：nums = {1, 2, 3, 1, 2, 3},  k = 2
        输出：false
*/
public class NO219_E_ContainsNearbyDuplicate_x3 {

    @Test
    public void test() {
        assert containsNearbyDuplicate(new int[]{1, 2, 3, 1},3);
        assert containsNearbyDuplicate(new int[]{1, 0, 1, 1},1);
        assert !containsNearbyDuplicate(new int[]{1, 2, 3, 1, 2, 3},2);
    }

    public boolean containsNearbyDuplicate(int[] nums, int k) {
        // 2024/2/21 NO.3
        return false;
    }

}













/*
// 方法1：
public boolean containsNearbyDuplicate(int[] nums, int k) {
    Set<Integer> set = new HashSet<>();
    for (int i = 0; i < nums.length; i++) {
        // 滑动窗口
        if (i > k)
            set.remove(nums[i - k - 1]);
        // 当出现相同元素，就返回True
        if (!set.add(nums[i]))
            return true;
    }
    return false;
}


// 方法2：
public boolean containsNearbyDuplicate(int[] nums, int k) {
    Map<Integer, Integer> map = new HashMap<>();
    for (int i = 0; i < nums.length; i++) {
        if (map.containsKey(nums[i])
                && i - map.get(nums[i]) <= k)
            return true;

        map.put(nums[i], i);
    }
    return false;
}

// 方法3：
public boolean containsNearbyDuplicate(int[] nums, int k){
    for (int i = 0; i < nums.length; i++) {
        // 从i+1开始，向后查找k个元素
        for (int j = i + 1; j <= i + k && j < nums.length; j++) {
            if (nums[i] == nums[j]) {
                return true; // 发现满足条件的重复元素
            }
        }
    }
    return false; // 没有找到满足条件的重复元素
}
*/