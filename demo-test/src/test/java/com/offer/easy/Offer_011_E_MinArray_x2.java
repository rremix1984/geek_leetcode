/**
 * copyright 2022/1/19
 */
package com.offer.easy;

import org.junit.Test;

/**
    [ARRAY] |
    (简单)
    剑指 Offer 11. 旋转数组的最小数字
        把一个数组最开始的若干个元素搬到数组的末尾，我们称之为数组的旋转。
        给你一个可能存在 重复 元素值的数组numbers，它原来是一个升序排列的数组，
        并按上述情形进行了一次旋转。请返回旋转数组的最小元素。
        例如，数组[3,4,5,1,2]为[1,2,3,4,5]的一次旋转，该数组的最小值为 1。
        注意，数组[a[0], a[1], a[2], ..., a[n-1]]旋转一次的结果为
             数组[a[n-1], a[0], a[1], a[2], ..., a[n-2]]。
    示例 1：
        输入：numbers = [3, 4, 5, 1, 2]
        输出：1
    示例 2：
        输入：numbers = [2, 2, 2, 0, 1]
        输出：0
*/
public class Offer_011_E_MinArray_x2 {

    @Test
    public void test() {
        assert 1 == minArray(new int[]{3, 4, 5, 1, 2});
        assert 0 == minArray(new int[]{2, 2, 2, 0, 1});
        assert 1 == minArray(new int[]{3, 3, 1, 3});
    }

    public int minArray(int[] nums) {
        // 2024/3/10 NO.1
        return -1;
    }

}














/*
// 方法1：二分查找法
public int minArray(int[] nums) {
    int n = nums.length;
    int left = -1;
    int right = n - 1;
    // 开区间 (-1, n-1)
    while (left + 1 < right) {
        // 开区间不为空
        int mid = left + (right - left) / 2;
        if (nums[mid] < nums[n - 1])
            right = mid;
            // 蓝色
        else
            left = mid;
        // 红色
    }
    return nums[right];
}
*/