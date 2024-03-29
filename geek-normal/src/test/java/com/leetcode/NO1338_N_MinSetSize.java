package com.leetcode;

import org.junit.Test;
import java.util.*;
import static com.leetcode.util.MathUtils.getDict;
import static com.leetcode.util.MathUtils.getDictInteger;
import static java.util.Arrays.*;

/**
    [ARRAY]
    (中等)
    NO.1338 数组大小减半
    给你一个整数数组 arr。你可以从中选出一个整数集合，
    并删除这些整数在数组中的每次出现。
    返回 至少 能删除数组中的一半整数的整数集合的最小大小。
    示例 1：
        输入：arr = [3, 3, 3, 3, 5, 5, 5, 2, 2, 7]
        输出：2
        解释：选择 {3, 7} 使得结果数组为 [5, 5, 5, 2, 2]、长度为 5（原数组长度的一半）。
        大小为 2 的可行集合有 {3, 5}, {3, 2}, {5, 2}。
        选择 {2,7} 是不可行的，它的结果数组为 [3,3,3,3,5,5,5]，新数组长度大于原数组的二分之一。
    示例 2：
        输入：arr = [7, 7, 7, 7, 7, 7]
        输出：1
        解释：我们只能选择集合 {7}，结果数组为空。
    提示：
        1 <= arr.length <= 10^5 (arr.length 为偶数)
        1 <= arr[i] <= 10^5
    Related Topics:贪心,数组,哈希表,排序,堆（优先队列）
*/
public class NO1338_N_MinSetSize {

    @Test
    public void test() {
        assert 2 == minSetSize(new int[]{3, 3, 3, 3, 5, 5, 5, 2, 2, 7});
        assert 1 == minSetSize(new int[]{7, 7, 7, 7, 7, 7});
    }

    public int minSetSize(int[] arr) {
        // 2024/3/17 NO.1 没看懂，需要复习
        return 0;
    }

}















/*
// 方法1：字典法
public int minSetSize(int[] arr) {
    // 2024/3/17 NO.1
    Integer[] dict = getDictInteger(10001, arr);

    sort(dict, (a, b) -> b - a);
    int sum = 0;
    for (int i = 0; i < dict.length; i++) {
        sum += dict[i];
        if (sum >= arr.length / 2)
            return i + 1;
    }
    return 0;
}
*/