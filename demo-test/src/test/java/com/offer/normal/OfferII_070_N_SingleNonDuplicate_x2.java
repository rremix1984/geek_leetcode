/**
 * copyright 2022/1/19
 */
package com.offer.normal;

import org.junit.Test;

/**
    (中等)
    剑指 Offer II 070. 排序数组中只出现一次的数字
        给定一个只包含整数的有序数组 nums ，每个元素都会出现两次，唯有一个数只会出现一次，请找出这个唯一的数字。
        你设计的解决方案必须满足 O(log n) 时间复杂度和 O(1) 空间复杂度。
    示例 1:
        输入: nums = {1, 1, 2, 3, 3, 4, 4, 8, 8}
        输出: 2
    示例 2:
        输入: nums = {3, 3, 7, 7, 10, 11, 11}
        输出: 10
*/
public class OfferII_070_N_SingleNonDuplicate_x2 {

    @Test
    public void test() {
        assert  2 == singleNonDuplicate(new int[]{1, 1, 2, 3, 3, 4, 4, 8, 8});
        assert 10 == singleNonDuplicate(new int[]{3, 3, 7, 7, 10, 11, 11});
        assert -1 == singleNonDuplicate(new int[]{});
        assert -1 == singleNonDuplicate(null);
    }

    public int singleNonDuplicate(int[] nums) {
        int low = 0;
        int high = nums.length - 1;
        while (low < high) {
            int mid = (high - low) / 2 + low;
            if (nums[mid] == nums[mid ^ 1])
                low = mid + 1;
            else
                high = mid;
        }
        return nums[low];
    }

}





























/**
// 方法1：
public int singleNonDuplicate(int[] nums) {
    if (nums == null || nums.length == 0)
        return -1;

    int res = 0;
    for (int i = 0; i < 32; i++) {
        int count = 0;
        for (int num : nums)
            count += (num >> i) & 1;

        if (count % 2 != 0)
            res |= 1 << i;
    }
    return res;
}

// 方法2：（最优）
public int singleNonDuplicate(int[] nums) {
    int low = 0, high = nums.length - 1;
    while (low < high) {
        int mid = (high - low) / 2 + low;
        if (nums[mid] == nums[mid ^ 1])
            low = mid + 1;
        else
            high = mid;
    }
    return nums[low];
}
*/