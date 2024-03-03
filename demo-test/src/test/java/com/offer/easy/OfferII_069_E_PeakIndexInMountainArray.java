/**
 * copyright 2022/1/19
 */
package com.offer.easy;

import org.junit.Test;

/**
    [ARRAY]
    (简单)
    剑指 Offer II 069. 山峰数组的顶部
        符合下列属性的数组 arr 称为 山峰数组（山脉数组） ：
          1）arr.length >= 3
          2）存在 i（0 < i < arr.length - 1）使得：
          3）arr[0] < arr[1] < ... arr[i-1] < arr[i]
          4）arr[i] > arr[i+1] > ... > arr[arr.length - 1]
        给定由整数组成的山峰数组arr，返回任何满足
        arr[0] < arr[1] < ... arr[i - 1] < arr[i] > arr[i + 1] > ... > arr[arr.length - 1]
        的下标i，即山峰顶部。
    示例 1：
        输入：arr = {0, 1, 0}
        输出：1
    示例 2：
        输入：arr = {1, 3, 5, 4, 2}
        输出：2
    示例 3：
        输入：arr = {0, 10, 5, 2}
        输出：1
    示例 4：
        输入：arr = {3, 4, 5, 1}
        输出：2
    示例 5：
        输入：arr = {24, 69, 100, 99, 79, 78, 67, 36, 26, 19}
        输出：2
*/
public class OfferII_069_E_PeakIndexInMountainArray {

    @Test
    public void test() {
        assert 1 == peakIndexInMountainArray(new int[]{0, 1, 0});
        assert 2 == peakIndexInMountainArray(new int[]{1, 3, 5, 4, 2});
        assert 1 == peakIndexInMountainArray(new int[]{0, 10, 5, 2});
        assert 2 == peakIndexInMountainArray(new int[]{3, 4, 5, 1});
        assert 2 == peakIndexInMountainArray(new int[]{24, 69, 100, 99, 79, 78, 67, 36, 26, 19});
    }

    public int peakIndexInMountainArray(int[] arr) {
        int left = 1;
        int right = arr.length - 2;
        int ans = 0;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (arr[mid] > arr[mid + 1]) {
                ans = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return ans;
    }
}
















/**
// 方法1：二分法
public int peakIndexInMountainArray(int[] arr) {
    int left = 1;
    int right = arr.length - 2;
    int ans = 0;
    while (left <= right) {
        int mid = (left + right) / 2;
        if (arr[mid] > arr[mid + 1]) {
            ans = mid;
            right = mid - 1;
        } else {
            left = mid + 1;
        }
    }
    return ans;
}
*/