/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;
import java.util.Arrays;
import static java.lang.Math.min;
import static org.junit.Assert.assertArrayEquals;

/**
    [ARRAY] ||
    (简单)
    350. 两个数组的交集 II
        给你两个整数数组nums1和nums2，请你以数组形式返回两数组的交集。
        返回结果中每个元素出现的次数，应与元素在两个数组中都出现的次数一致
        （如果出现次数不一致，则考虑取较小值）。可以不考虑输出结果的顺序。
    示例 1：
        输入：nums1 = [1, 2, 2, 1], nums2 = [2, 2]
        输出：[2, 2]
    示例 2:
        输入：nums1 = [4, 9, 5], nums2 = [9, 4, 9, 8, 4]
        输出：[4, 9]
*/
public class NO350_E_Intersect_x3 {

    @Test
    public void test() {
        assertArrayEquals(new int[]{2, 2},
                intersect(new int[]{1, 2, 2, 1}, new int[]{2, 2}));
        assertArrayEquals(new int[]{4, 9},
                intersect(new int[]{4, 9, 5}, new int[]{9, 4, 9, 8, 4}));
    }

    public int[] intersect(int[] nums1, int[] nums2) {
        return null;
    }

}



















/*
// 方法1：
public int[] intersect(int[] nums1, int[] nums2) {
    Arrays.sort(nums1);
    Arrays.sort(nums2);
    int len1 = nums1.length;
    int len2 = nums2.length;

    int[] res = new int[min(len1, len2)];

    int i = 0;
    int j = 0;
    int idx = 0;

    while (i < len1 && j < len2)
        if (nums1[i] < nums2[j])
            i++;
        else if (nums1[i] > nums2[j])
            j++;
        else {
            res[idx++] = nums1[i];
            j++;
            j++;
        }
    return Arrays.copyOfRange(res, 0, idx);
}
*/