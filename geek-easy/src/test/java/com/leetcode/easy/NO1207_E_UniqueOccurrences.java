/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
    [ARRAY]
    (简单)
    1207. 独一无二的出现次数
        给你一个整数数组arr，请你帮忙统计数组中每个数的出现次数。
        如果每个数的出现次数都是独一无二的，就返回true；否则返回false。
    示例 1：
        输入：arr = {1, 2, 2, 1, 1, 3}
        输出：true
        解释：在该数组中，1出现了3次，2出现了2次，3只出现了1次。
             没有两个数的出现次数相同。
    示例 2：
        输入：arr = {1, 2}
        输出：false
    示例 3：
        输入：arr = {-3, 0, 1, -3, 1, 1, 1, -3, 10, 0}
        输出：true
    提示：
        1 <= arr.length <= 1000
        -1000 <= arr[i] <= 1000
*/
public class NO1207_E_UniqueOccurrences {

    @Test
    public void test() {
//        assert uniqueOccurrences(
//            new int[]{1, 2, 2, 1, 1, 3});
        assert !uniqueOccurrences(
            new int[]{1, 2});
//        assert uniqueOccurrences(
//            new int[]{-3, 0, 1, -3, 1, 1, 1, -3, 10, 0});
    }

    public boolean uniqueOccurrences(int[] arr) {
        // 2024/3/6 NO.1
        return true;
    }

}

















/*
// 方法1：
public boolean uniqueOccurrences(int[] arr) {
    int[] count = new int[2001];
    for (int j : arr)
        count[1000 + j]++;

    Set<Integer> set = new HashSet<>();
    for (int value : count)
        if (value != 0 && !set.add(value))//如果存储失败，说明有重复的
            return false;

    return true;
}
*/