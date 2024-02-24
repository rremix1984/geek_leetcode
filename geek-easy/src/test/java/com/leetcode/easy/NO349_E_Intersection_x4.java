/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;

/**
    [ARRAY] ||
    (简单)
    349. 两个数组的交集
        给定两个数组nums1和nums2，返回它们的交集。输出结果中的
        每个元素一定是【唯一】的。我们可以不考虑输出结果的顺序。
    示例 1：
        输入：nums1 = [1, 2, 2, 1],  nums2 = [2, 2]
        输出：[2]
    示例 2：
        输入：nums1 = [4, 9, 5],  nums2 = [9, 4, 9, 8, 4]
        输出：[9, 4]
        解释：[4, 9] 也是可通过的
    提示：
        1 <= nums1.length, nums2.length <= 1000
        0 <= nums1[i], nums2[i] <= 1000
*/
public class NO349_E_Intersection_x4 {

    @Test
    public void test() {
        assertArrayEquals(new int[]{2}, 
                intersection(new int[]{1, 2, 2, 1},  new int[]{2, 2}));
        assertArrayEquals(new int[]{4, 9}, 
                intersection(new int[]{4, 9, 5},  new int[]{9, 4, 9, 8, 4}));
    }

    public int[] intersection(int[] nums1, int[] nums2) {
        // 2024/2/22 NO.3
        // 2024/2/24 NO.4
        return null;
    }

}













/*
// 方法1：
public int[] intersection(int[] nums1, int[] nums2) {
    Arrays.sort(nums1);
    Arrays.sort(nums2);
    // 先堆到一起，然后在截取数组
    int[] ans = new int[nums1.length + nums2.length];
    int tail = 0;
    int i = 0;
    int j = 0;
    while (i < nums1.length && j < nums2.length) {
        if (nums1[i] == nums2[j]) {
            // 保证加入元素的唯一性
            // 去重：仅当当前找到的交集元素不等于
            // res最后一个元素时，才将其加入结果数组
            if (tail == 0 || nums1[i] != ans[tail - 1])
                ans[tail++] = nums1[i];
            i++;
            j++;
        } else if (nums1[i] < nums2[j]) {
            i++;
        } else {
            j++;
        }
    }
    return Arrays.copyOfRange(ans, 0, tail);
}
*/