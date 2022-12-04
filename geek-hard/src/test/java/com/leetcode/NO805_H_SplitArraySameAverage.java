/**
 * copyright 2022/1/19
 */
package com.leetcode;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
    (困难)
    805. 数组的均值分割
        给定你一个整数数组 nums
        我们要将 nums 数组中的每个元素移动到 A 数组 或者 B 数组中，使得 A 数组和 B 数组不为空，并且 average(A) == average(B) 。
        如果可以完成则返回true ， 否则返回 false  。
        注意：对于数组 arr ,  average(arr) 是 arr 的所有元素的和除以 arr 长度。
    示例 1:
        输入: nums = [1,2,3,4,5,6,7,8]
        输出: true
        解释: 我们可以将数组分割为 [1,4,5,8] 和 [2,3,6,7], 他们的平均值都是4.5。
    示例 2:
        输入: nums = [3,1]
        输出: false
    提示:
        1 <= nums.length <= 30
        0 <= nums[i] <= 104
*/
public class NO805_H_SplitArraySameAverage {

    @Test
    public void test() {
        assert splitArraySameAverage(new int[]{1,2,3,4,5,6,7,8});
        assert !splitArraySameAverage(new int[]{3,1});
    }

    public boolean splitArraySameAverage(int[] nums) {
        if (nums.length == 1) {
            return false;
        }
        int n = nums.length, m = n / 2;
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }
        for (int i = 0; i < n; i++) {
            nums[i] = nums[i] * n - sum;
        }

        Set<Integer> left = new HashSet<>();
        for (int i = 1; i < (1 << m); i++) {
            int tot = 0;
            for (int j = 0; j < m; j++) {
                if ((i & (1 << j)) != 0) {
                    tot += nums[j];
                }
            }
            if (tot == 0) {
                return true;
            }
            left.add(tot);
        }
        int rsum = 0;
        for (int i = m; i < n; i++) {
            rsum += nums[i];
        }
        for (int i = 1; i < (1 << (n - m)); i++) {
            int tot = 0;
            for (int j = m; j < n; j++) {
                if ((i & (1 << (j - m))) != 0) {
                    tot += nums[j];
                }
            }
            if (tot == 0 || (rsum != tot && left.contains(-tot))) {
                return true;
            }
        }
        return false;
    }

}
