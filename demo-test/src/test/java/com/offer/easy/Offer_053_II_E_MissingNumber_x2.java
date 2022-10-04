/**
 * copyright 2022/1/19
 */
package com.offer.easy;

import org.junit.Test;
import static com.leetcode.util.LogUtil.info;

/**
    (简单)
    剑指 Offer 53 - II. 0～n-1中缺失的数字
        一个长度为n-1的递增排序数组中的所有数字都是唯一的，并且每个数字都在范围0～n-1之内。
        在范围0～n-1内的n个数字中有且只有一个数字不在该数组中，请找出这个数字。
    示例 1:
        输入: {0, 1, 3}
        输出: 2
    示例 2:
        输入: {0, 1, 2, 3, 4, 5, 6, 7, 9}
        输出: 8
*/
public class Offer_053_II_E_MissingNumber_x2 {


    @Test
    public void test() {
        assert 8 == missingNumber(new int[]{0, 1, 2, 3, 4, 5, 6, 7, 9});
        assert 2 == missingNumber(new int[]{0, 1, 3});
        assert 1 == missingNumber(new int[]{0});
        assert 2 == missingNumber(new int[]{0, 1});
    }

    public int missingNumber(int[] nums) {
        return -1;
    }

}

















/**
// 方法1：异或法
public int missingNumber(int[] nums) {
    int ans = 0;
    for (int i = 0; i < nums.length + 1; i++){
        if (i < nums.length)
            ans ^= nums[i];
        ans ^= i;
    }
    return ans;
}


// 方法2：高斯方程法
public int missingNumber(int[] nums) {
    int n = nums.length + 1;
    int total = n * (n - 1) / 2;
    int arrSum = 0;
    for (int i = 0; i < n - 1; i++) {
        arrSum += nums[i];
    }
    return total - arrSum;
}


// 方法2：高斯法简化
public int missingNumber(int[] nums) {
    int total = (nums.length + 1) * (nums.length) / 2;
    int arrSum = Arrays.stream(nums).sum();
    return total - arrSum;
}

// 方法4：
public int missingNumber(int[] nums) {
    int xor = 0;
    int n = nums.length + 1;
    for (int i = 0; i < n - 1; i++) {
        xor ^= nums[i];
    }
    for (int i = 0; i <= n - 1; i++) {
        xor ^= i;
    }
    return xor;
}
*/