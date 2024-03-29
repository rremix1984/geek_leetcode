/**
 * copyright 2022/1/19
 */
package com.leetcode.normal;

import org.junit.Test;

//import static com.leetcode.sort.HeapSort.heapSort;
import static org.junit.Assert.assertArrayEquals;
import static com.leetcode.sort.HeapSort.heapSort;

/**
    [ARRAY]
    (中等)
    912. 排序数组
        给你一个整数数组 nums，请你将该数组升序排列。
    示例 1：
        输入：nums = {5, 2, 3, 1}
        输出：[1, 2, 3, 5]
    示例 2：
        输入：nums = {5, 1, 1, 2, 0, 0}
        输出：[0, 0, 1, 1, 2, 5]
*/
public class NO912_N_SortAnArray_x2 {

    @Test
    public void test() {
        assertArrayEquals(new int[]{1, 2, 3, 5},
                sortArray(new int[]{5, 2, 3, 1}));

        assertArrayEquals(new int[]{0, 0, 1, 1, 2, 5},
                sortArray(new int[]{5, 1, 1, 2, 0, 0}));
    }

    public int[] sortArray(int[] nums) {
        return heapSort(nums);
    }

}

















