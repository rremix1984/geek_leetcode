/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;

/**
    [ARRAY]
    (简单)
    496. 下一个更大元素 I
        nums1中数字x的下一个更大元素是指x在nums2中对应位置右侧的第一个比x大的元素。
        给你两个【没有重复元素】的数组nums1和nums2，下标从0开始计数，其中nums1是nums2的子集。
        对于每个0 <= i < nums1.length，找出满足nums1[i] == nums2[j]的下标j，
        并且在nums2确定nums2[j]的下一个更大元素。如果不存在下一个更大元素，那么本次查询的答案是-1。
        返回一个长度为nums1.length的数组ans作为答案，满足ans[i]是如上所述的下一个更大元素。
    示例 1：
        输入：nums1 = [4, 1, 2],  nums2 = [1, 3, 4, 2].
        输出：[-1, 3, -1]
        解释：nums1 中每个值的下一个更大元素如下所述：
            4，nums2 = [1, 3, 4, 2]。不存在下一个更大元素，所以答案是 -1 。
            1，nums2 = [1, 3, 4, 2]。下一个更大元素是 3 。
            2，nums2 = [1, 3, 4, 2]。不存在下一个更大元素，所以答案是 -1 。
    示例 2：
        输入：nums1 = [2, 4],  nums2 = [1, 2, 3, 4].
        输出：[3, -1]
        解释：nums1 中每个值的下一个更大元素如下所述：
            - 2 ，用加粗斜体标识，nums2 = [1, 2, 3, 4]。下一个更大元素是 3 。
            - 4 ，用加粗斜体标识，nums2 = [1, 2, 3, 4]。不存在下一个更大元素，所以答案是 -1 。
    提示：
        1 <= nums1.length <= nums2.length <= 1000
        0 <= nums1[i], nums2[i] <= 104
        nums1和nums2中所有整数 互不相同
        nums1 中的所有整数同样出现在 nums2 中
*/
public class NO496_E_NextGreaterElement_x3 {

    @Test
    public void test() {
        assertArrayEquals(new int[]{-1, 3, -1},
                nextGreaterElement(new int[]{4, 1, 2}, new int[]{1, 3, 4, 2}));
        assertArrayEquals(new int[]{3, -1},
                nextGreaterElement(new int[]{2, 4}, new int[]{1, 2, 3, 4}));
    }

    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        // 2024/2/23 NO.3
        int[] res = new int[nums1.length];
        return res;
    }

}





















/*
// 方法1：
public int[] nextGreaterElement(int[] nums1, int[] nums2) {
    int[] res = new int[nums1.length];
    for (int i = 0; i < nums1.length; i++) {
        int j = 0;
        while (j < nums2.length && nums2[j] != nums1[i])
            j++;

        // 此时 nums2[j] == nums1[i]
        int next = j + 1;
        // 找到下一个大于 j 的元素
        while (next < nums2.length && nums2[next] < nums2[j])
            next++;

        if (next < nums2.length)
            res[i] = nums2[next];
        else
            res[i] = -1;
    }
    return res;
}
*/