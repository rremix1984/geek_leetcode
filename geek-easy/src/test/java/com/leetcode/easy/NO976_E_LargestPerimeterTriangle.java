/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

import java.util.Arrays;

import static java.util.Arrays.sort;

/**
    [ARRAY] ||
    (简单)
    976. 三角形的最大周长
        给定由一些正数（代表长度）组成的数组nums，返回由其中三个长度
        组成的、面积不为零的三角形的最大周长。如果不能形成任何面积不
        为零的三角形，返回 0。
    示例 1：
        输入：nums = [2, 1, 2]
        输出：5
    示例 2：
        输入：nums = [1, 2, 1]
        输出：0
*/
public class NO976_E_LargestPerimeterTriangle {

    @Test
    public void test() {
        assert 5 == largestPerimeter(new int[]{2, 1, 2});
        assert 0 == largestPerimeter(new int[]{1, 2, 1});
        assert 21 == largestPerimeter(new int[]{2, 3, 3, 6, 5, 8, 7, 1});
    }

    public int largestPerimeter(int[] nums) {
        // 2024/2/25 NO.3
        // 2024/3/29 NO.4 没做出来，能看懂

        return 0;
    }

}






















/*
    1）排序：首先，我们需要对数组进行排序，这样我们就可以从大到小检查是否可以形成三角形。
    2）选择三个数：然后，从排序后的数组中选择三个连续的数（记为a, b, c，其中a <= b <= c），检查这三个数是否能构成三角形。根据三角形的性质，任意两边之和大于第三边，所以我们只需要检查a + b > c是否成立。
    3）遍历数组：从数组的最大元素开始，向左遍历数组，每次尝试选择一组新的三个连续的数，直到找到一组符合条件的数或遍历完数组。
    在排序后的数组中，假设我们选取的三个数为a、b和c（a <= b <= c），为了满足构成三角形的条件，我们需要：
    a + b > c
    a + c > b（这总是成立的，因为c是最大的）
    b + c > a（这也总是成立的）
    实际上，只需要检验条件1，因为如果最小的两个数之和大于第三个数，那么另外两个条件自然成立。
    选择数组中排序后相邻的三个数进行检查的原因是，这样可以确保我们检查的是最大的可能周长。因为如果较大的数不能构成三角形，那么用更小的数替换任何一个较大数同样不可能构成三角形。

// 方法1：
public int largestPerimeter(int[] nums) {
    // 对数组进行排序
    Arrays.sort(nums);
    // 从最大的数开始，尝试找到能形成三角形的三个数
    for (int i = nums.length - 1; i >= 2; i--) {
        // 检查是否可以构成三角形
        if (nums[i] < nums[i - 1] + nums[i - 2]) {
            // 如果可以，返回它们的周长
            return nums[i] + nums[i - 1] + nums[i - 2];
        }
    }
    // 如果没有找到，返回0
    return 0;
}
*/