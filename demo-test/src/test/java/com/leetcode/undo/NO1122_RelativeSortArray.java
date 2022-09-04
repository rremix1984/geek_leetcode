/**
 * copyright 2022/1/19
 */
package com.leetcode.undo;

import org.junit.Test;
import static com.leetcode.util.LogUtil.info;
import static com.leetcode.util.MathUtils.max;

/**
    （简单）
    1122. 数组的相对排序
        给你两个数组，arr1 和 arr2，arr2 中的元素各不相同，arr2 中的每个元素都出现在 arr1 中。
        对 arr1 中的元素进行排序：
            1. 使 arr1 中项的相对顺序和 arr2 中的相对顺序相同。
            2. 未在 arr2 中出现过的元素需要按照升序放在 arr1 的末尾。
    示例 1：
        输入：arr1 = {2, 3, 1, 3, 2, 4, 6, 7, 9, 2, 19},
             arr2 = {2, 1, 4, 3, 9, 6}
        输出：[2, 2, 2, 1, 4, 3, 3, 9, 6, 7, 19]
    示例  2:
        输入：arr1 = {28, 6, 22, 8, 44, 17},
             arr2 = {22, 28, 8, 6}
        输出：[22, 28, 8, 6, 17, 44]
*/
public class NO1122_RelativeSortArray {

    @Test
    public void test() {
        info(relativeSortArray(new int[]{2, 3, 1, 3, 2, 4, 6, 7, 9, 2, 19},
                               new int[]{2, 1, 4, 3, 9, 6}));// [2, 2, 2, 1, 4, 3, 3, 9, 6, 7, 19]
        info(relativeSortArray(new int[]{28, 6, 22, 8, 44, 17},
                               new int[]{22, 28, 8, 6}));// [22, 28, 8, 6, 17, 44]
    }

    public int[] relativeSortArray(int[] arr1, int[] arr2) {
        int max = 0;
        for (int num : arr1)
            max = max(max, num);

        int[] counts = new int[max + 1];
        for (int num : arr1)
            counts[num]++;

        int length1 = arr1.length;
        int[] sorted = new int[length1];
        int index = 0;

        for (int num : arr2) {
            while (counts[num] > 0) {
                sorted[index++] = num;
                counts[num]--;
            }
        }

        for (int i = 0; i <= max; i++)
            while (counts[i] > 0) {
                sorted[index++] = i;
                counts[i]--;
            }

        return sorted;
    }

}



















/**
public int[] relativeSortArray(int[] arr1, int[] arr2) {
    int max = 0;
    for (int num : arr1)
        max = max(max, num);

    int[] counts = new int[max + 1];
    for (int num : arr1)
        counts[num]++;

    int length1 = arr1.length;
    int[] sorted = new int[length1];
    int index = 0;

    for (int num : arr2) {
        while (counts[num] > 0) {
            sorted[index++] = num;
            counts[num]--;
        }
    }

    for (int i = 0; i <= max; i++)
        while (counts[i] > 0) {
            sorted[index++] = i;
            counts[i]--;
        }

    return sorted;
}
*/