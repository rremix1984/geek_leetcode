/**
 * copyright 2022/1/19
 */
package com.offer.easy;

import org.junit.Test;
// import static com.leetcode.util.SwapUtil.swap;
import static org.junit.Assert.assertArrayEquals;

/**
    (简单)
    剑指 Offer 21. 调整数组顺序使奇数位于偶数前面
        输入一个整数数组，实现一个函数来调整该数组中数字的顺序，使得所有奇数在数组的前半部分，所有偶数在数组的后半部分。
    示例：
        输入：nums = [1,2,3,4]
        输出：[1,3,2,4]
          注：[3,1,2,4] 也是正确的答案之一。
*/
public class Offer_021_E_Exchange_x2 {

    @Test
    public void test() {
        assertArrayEquals(new int[]{1, 3, 2, 4},
                exchange(new int[]{1, 2, 3, 4}));
        assertArrayEquals(new int[]{1, 3, 5},
                exchange(new int[]{1, 3, 5}));
    }

    public int[] exchange(int[] nums) {
        return nums;
    }

}
















/**
// 方法1：
public int[] exchange(int[] nums) {
    int l = 0;
    int r = nums.length - 1;

    while (l < r) {
        while ((nums[r] & 1) == 0 && l < r)
            r--;

        while ((nums[l] & 1) == 1 && l < r)
            l++;

        swap(nums, l++, r--);
    }
    return nums;
}
*/