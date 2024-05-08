package com.leetcode.todo;

import org.junit.Test;
import java.util.HashSet;
import java.util.Set;

/**
    [ARRAY]
    （简单）
    NO.2605 从两个数字数组里生成最小数字
    给你两个只包含 1 到 9 之间数字的数组 nums1 和 nums2 ，每个数组中的元素 互不相同 ，请你返回 最小 的数字，两个数组都 至少 包含这个数字的某个数位。
    示例 1：
        输入：nums1 = [4,1,3], nums2 = [5,7]
        输出：15
        解释：数字 15 的数位 1 在 nums1 中出现，数位 5 在 nums2 中出现。15 是我们能得到的最小数字。
    示例 2：
        输入：nums1 = [3,5,2,6], nums2 = [3,1,7]
        输出：3
        解释：数字 3 的数位 3 在两个数组中都出现了。
    提示：
        1 <= nums1.length, nums2.length <= 9
        1 <= nums1[i], nums2[i] <= 9
        每个数组中，元素 互不相同 。
    Related Topics:数组,哈希表,枚举
*/
@SuppressWarnings("all")
public class NO2605_E_MinNumber {

    @Test
    public void test() {
        assert 15 == minNumber(
            new int[]{4, 1, 3}, new int[]{5, 7});
        assert 3 == minNumber(
            new int[]{3, 5, 2, 6}, new int[]{3, 1, 7});
    }

    public int minNumber(int[] nums1, int[] nums2) {
        return -1;
    }

}


















/*
// 方法1：
public int minNumber(int[] nums1, int[] nums2) {
    int s = 10;
    Set<Integer> set = new HashSet<>();
    for (int x : nums1)
        set.add(x);

    for (int x : nums2)
        if (set.contains(x))
            s = Math.min(s, x);

    if (s != 10)
        return s;

    int x = 10, y = 10;
    // int x = Arrays.stream(nums1).min().getAsInt();
    for (int v : nums1)
        x = Math.min(x, v);

    for (int v : nums2)
        y = Math.min(y, v);

    // int y = Arrays.stream(nums2).min().getAsInt();
    return Math.min(x * 10 + y, y * 10 + x);
}
*/