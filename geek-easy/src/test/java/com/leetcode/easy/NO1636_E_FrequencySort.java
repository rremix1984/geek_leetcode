/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;
import java.util.*;
import static com.leetcode.util.SystemUtil.printArr;
import static org.junit.Assert.assertArrayEquals;

/**
    [ARRAY] |
    (简单)
    1636. 按照频率将数组升序排序
        1）给你一个整数数组nums，请你将数组按照每个值的频率【升序】排序。
        2）如果有多个值的频率相同，请你按照数值本身将它们【降序】排序。
        请你返回排序后的数组。
    示例 1：
        输入：nums = {1, 1, 2, 2, 2, 3}
        输出：{3, 1, 1, 2, 2, 2}
        解释：'3' 频率为 1，'1' 频率为 2，'2' 频率为 3 。
    示例 2：
        输入：nums = {2, 3, 1, 3, 2}
        输出：{1, 3, 3, 2, 2}
        解释：'2' 和 '3' 频率都为 2 ，所以它们之间按照数值本身降序排序。
    示例 3：
        输入：nums = {-1, 1, -6, 4, 5, -6, 1, 4, 1}
        输出：{5, -1, 4, 4, -6, -6, 1, 1, 1}
*/
public class NO1636_E_FrequencySort {

    @Test
    public void test() {
        assertArrayEquals(new int[]{3, 1, 1, 2, 2, 2},
            frequencySort(new int[]{1, 1, 2, 2, 2, 3}));
        assertArrayEquals(new int[]{1, 3, 3, 2, 2},
            frequencySort(new int[]{2, 3, 1, 3, 2}));
        assertArrayEquals(new int[]{5, -1, 4, 4, -6, -6, 1, 1, 1},
            frequencySort(new int[]{-1, 1, -6, 4, 5, -6, 1, 4, 1}));
    }

    public int[] frequencySort(int[] nums) {
        // 2024/3/7 NO.1
        return null;
    }

}
















/*
// 方法1：
public int[] frequencySort(int[] nums) {
    Map<Integer, Integer> map = new HashMap<>();
    for (int num: nums)
        map.put(num, map.getOrDefault(num, 0) + 1);

    return Arrays.stream(nums).boxed().sorted((a, b) -> {
        // 1.频率按照升序排列
        if (map.get(a) != map.get(b))
            return map.get(a) - map.get(b);
        // 2.频率相同，按照数字降序排列
        else // map.get(a) == map.get(b)
            return b - a;
    }).mapToInt(Integer::valueOf).toArray();
}

// 方法2：
public int[] frequencySort(int[] nums) {

}
*/