/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;

/**
    [ARRAY] ||
    (简单)
    88. 合并两个有序数组
        给你两个按【非递减顺序】排列的整数数组nums1和nums2，另有两个整数m和n，
        分别表示nums1和nums2中的元素数目。
        请你【合并nums2到nums1中】，使合并后的数组同样按【非递减顺序】排列。
        注意：最终，合并后数组不应由函数返回，而是存储在数组nums1中。为了应对这种情况，
        nums1的初始长度为m + n，其中前m个元素表示应合并的元素，后n个元素为0，应忽略。
        nums2的长度为n。
    示例 1：
        输入：nums1 = [1, 2, 3, 0, 0, 0], m = 3, nums2 = [2, 5, 6], n = 3
        输出：[1, 2, 2, 3, 5, 6]
        解释：需要合并[1, 2, 3] 和 [2, 5, 6]。
             合并结果是[1, 2, 2, 3, 5, 6]，其中斜体加粗标注的为nums1中的元素。
    示例 2：
        输入：nums1 = [1], m = 1, nums2 = [], n = 0
        输出：[1]
        解释：需要合并 [1] 和 [] 。
             合并结果是 [1] 。
    示例 3：
        输入：nums1 = [0], m = 0, nums2 = [1], n = 1
        输出：[1]
        解释：需要合并的数组是[] 和 [1]。
             合并结果是[1]。
             注意，因为m = 0，所以nums1中没有元素。nums1中仅存的0
             仅仅是为了确保合并结果可以顺利存放到nums1中。
*/
@SuppressWarnings("all")
public class NO088_E_MergeSortedArray {

    @Test
    public void test() {
        int[] target = {1, 2, 3, 0, 0, 0};
        merge(target, 3, new int[]{2, 5, 6}, 3);// [1, 2, 2, 3, 5, 6]
        assertArrayEquals(new int[]{1,2,2,3,5,6}, target);

        int[] target1 = {1};
        merge(target1, 1, new int[]{}, 0);
        assertArrayEquals(new int[]{1}, target1);// [1]

        int[] target2 = {0};
        merge(target2, 0, new int[]{1}, 1);
        assertArrayEquals(new int[]{1}, target2);// [1]
    }

    public void merge(int[] nums1, int m, int[] nums2, int n) {
        // 2024/2/21 NO.3 双指针法
        // 2024/3/5  NO.4 双指针法 隔了一个月，居然没做出来

    }

}
















/*
// 方法1：双指针法，每次从头部取出相对较小的元素，放入新数组
public void merge(int[] nums1, int m, int[] nums2, int n) {
    int p1 = m - 1; // nums1 数组的指针，从后向前
    int p2 = n - 1; // nums2 数组的指针，从后向前
    int tail = m + n - 1;// 新数组的尾指针：tail

    while (p1 >= 0 || p2 >= 0) {
        int cur; //当前值，通过tail指针放入
        // 数组p1 到头了，就直接遍历 p2 数组
        if (p1 == -1)
            cur = nums2[p2--];
        // 数组p2 到头了，就直接遍历 p1 数组
        else if (p2 == -1 || nums1[p1] > nums2[p2])
            cur = nums1[p1--];
        else // target[p1] <= source[p2]
            cur = nums2[p2--];

        nums1[tail--] = cur;
    }
}

// 简化后的方法2：
public void merge(int[] nums1, int m, int[] nums2, int n) {
    int p1 = m - 1;
    int p2 = n - 1;
    int tail = m + n - 1;
    while (p1 >= 0 || p2 >= 0) {
        // 如果p1到头了
        if (p1 == -1 || (p2 != -1 && nums1[p1] < nums2[p2]))
            nums1[tail--] = nums2[p2--];
        else
            nums1[tail--] = nums1[p1--];
    }
}
*/