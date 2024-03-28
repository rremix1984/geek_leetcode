/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.assertArrayEquals;

/**
    [ARRAY] ||
    (简单)
    NO.1331. 数组序号转换
        给你一个整数数组arr，请你将数组中的每个元素替换为它们排序后的序号。
        序号代表了一个元素有多大。序号编号的规则如下：
        1）序号从1开始编号。
        2）一个元素越大，那么序号越大。如果两个元素相等，那么它们的序号相同。
        3）每个数字的序号都应该尽可能地小。
    示例 1：
        输入：arr = [40, 10, 20, 30]
        输出：[4, 1, 2, 3]
        解释：40 是最大的元素。 10 是最小的元素。 20 是第二小的数字。 30 是第三小的数字。
    示例 2：
        输入：arr = [100, 100, 100]
        输出：[1,1,1]
        解释：所有元素有相同的序号。
    示例 3：
        输入：arr = [37, 12, 28, 9, 100, 56, 80, 5, 12]
        输出：[5, 3, 4, 2, 8, 6, 7, 1, 3]
    提示：
        0 <= arr.length <= 105
        -109 <= arr[i] <= 109
*/
public class NO1331_E_ArrayRankTransform_x2 {

    @Test
    public void test() {
        assertArrayEquals(new int[]{4, 1, 2, 3},
                arrayRankTransform(new int[]{40, 10, 20, 30}));
        assertArrayEquals(new int[]{1, 1, 1},
                arrayRankTransform(new int[]{100, 100, 100}));
        assertArrayEquals(new int[]{5, 3, 4, 2, 8, 6, 7, 1, 3},
                arrayRankTransform(new int[]{37, 12, 28, 9, 100, 56, 80, 5, 12}));
    }

    public int[] arrayRankTransform(int[] arr) {
        // 2024/2/25 NO.3
        int[] ans = new int[arr.length];

        return ans;
    }

}
























/*
// 方法1：
public int[] arrayRankTransform(int[] arr) {
    int[] sortedArr = new int[arr.length];
    System.arraycopy(arr, 0, sortedArr, 0, arr.length);
    Arrays.sort(sortedArr);

    Map<Integer, Integer> ranks = new HashMap<>();
    for (int a : sortedArr)
        if (!ranks.containsKey(a))
            ranks.put(a, ranks.size() + 1);

    int[] ans = new int[arr.length];
    for (int i = 0; i < arr.length; i++)
        ans[i] = ranks.get(arr[i]);

    return ans;
}
*/
