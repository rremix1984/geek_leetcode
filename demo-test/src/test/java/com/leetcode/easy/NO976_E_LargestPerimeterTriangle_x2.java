/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;
import java.util.Arrays;

/**
    (简单)
    976. 三角形的最大周长
        给定由一些正数（代表长度）组成的数组 nums ，返回 由其中三个长度组成的、
        面积不为零的三角形的最大周长 。如果不能形成任何面积不为零的三角形，返回 0。
    示例 1：
        输入：nums = [2,1,2]
        输出：5
    示例 2：
        输入：nums = [1,2,1]
        输出：0
*/
public class NO976_E_LargestPerimeterTriangle_x2 {

    @Test
    public void test() {
        assert 5 == largestPerimeter(new int[]{2, 1, 2});
        assert 0 == largestPerimeter(new int[]{1, 2, 1});
        assert 21 == largestPerimeter(new int[]{2, 3, 3, 6, 5, 8, 7, 1});
    }

    public int largestPerimeter(int[] nums) {
        Arrays.sort(nums);
        for (int i = nums.length - 1; i >= 2; i--) {
            if (nums[i - 1] + nums[i - 2] > nums[i]) {
                return nums[i - 1] + nums[i - 2] + nums[i];
            }
        }
        return 0;
    }

}
