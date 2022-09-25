/**
 * copyright 2022/1/19
 */
package com.offer;

import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;

/**
    (简单)
    剑指 Offer II 075. 数组相对排序
        给定两个数组，arr1 和 arr2，
        arr2 中的元素各不相同
        arr2 中的每个元素都出现在 arr1 中
        对 arr1 中的元素进行排序，使 arr1 中项的相对顺序和 arr2 中的相对顺序相同。未在 arr2 中出现过的元素需要按照升序放在 arr1 的末尾。
    示例：
        输入：arr1 = {2, 3, 1, 3, 2, 4, 6, 7, 9, 2, 19}, arr2 = {2, 1, 4, 3, 9, 6}
        输出：{2, 2, 2, 1, 4, 3, 3, 9, 6, 7, 19}
*/
public class OfferII_075_E_RelativeSortArray {

    @Test
    public void test() {
        assertArrayEquals(new int[]{2, 2, 2, 1, 4, 3, 3, 9, 6, 7, 19}, relativeSortArray(
            new int[]{2, 3, 1, 3, 2, 4, 6, 7, 9, 2, 19}, new int[]{2, 1, 4, 3, 9, 6}));
    }

    public int[] relativeSortArray(int[] arr1, int[] arr2) {
        int[] ans = new int[arr1.length];

        // 统计出现次数
        int upper = 0;
        for (int x : arr1)
            upper = Math.max(upper, x);

        int[] frequency = new int[upper + 1];
        for (int x : arr1)
            frequency[x]++;

        // 数组前段，arr2中有的元素
        int index = 0;
        for (int x : arr2) {
            for (int i = 0; i < frequency[x]; i++)
                ans[index++] = x;
            frequency[x] = 0;
        }

        // 数组后段，arr2中没有的元素
        for (int x = 0; x <= upper; ++x)
            for (int i = 0; i < frequency[x]; ++i)
                ans[index++] = x;

        return ans;
    }

}

















/**
// 方法1：
public int[] relativeSortArray(int[] arr1, int[] arr2) {
    int[] ans = new int[arr1.length];

    // 统计出现次数
    int upper = 0;
    for (int x : arr1)
        upper = Math.max(upper, x);

    int[] frequency = new int[upper + 1];
    for (int x : arr1)
        frequency[x]++;

    // 数组前段，arr2中有的元素
    int index = 0;
    for (int x : arr2) {
        for (int i = 0; i < frequency[x]; i++)
            ans[index++] = x;
        frequency[x] = 0;
    }

    // 数组后段，arr2中没有的元素
    for (int x = 0; x <= upper; ++x)
        for (int i = 0; i < frequency[x]; ++i)
            ans[index++] = x;

    return ans;
}
*/