/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

/**
    [ARRAY] |||
    (简单)
    1566. 重复至少K次且长度为M的模式
        给你一个正整数数组 arr，请你找出一个长度为 m 且在数组中至少重复 k 次的模式。
        模式 是由一个或多个值组成的子数组（连续的子序列），连续 重复多次但 不重叠 。
        模式由其长度和重复次数定义。
        如果数组中存在至少重复 k 次且长度为 m 的模式，则返回 true ，否则返回  false 。
    示例 1：
        输入：arr = [1, 2, 4, 4, 4, 4], m = 1, k = 3
        输出：true
        解释：模式 (4) 的长度为 1 ，且连续重复 4 次。注意，模式可以重复 k 次或更多次，但不能少于 k 次。
    示例 2：
        输入：arr = [1, 2, 1, 2, 1, 1, 1, 3], m = 2, k = 2
        输出：true
        解释：模式 (1,2) 长度为 2 ，且连续重复 2 次。另一个符合题意的模式是 (2,1) ，同样重复 2 次。
    示例 3：
        输入：arr = [1, 2, 1, 2, 1, 3], m = 2, k = 3
        输出：false
        解释：模式 (1,2) 长度为 2 ，但是只连续重复 2 次。不存在长度为 2 且至少重复 3 次的模式。
    示例 4：
        输入：arr = [1, 2, 3, 1, 2], m = 2, k = 2
        输出：false
        解释：模式 (1,2) 出现 2 次但并不连续，所以不能算作连续重复 2 次。
    示例 5：
        输入：arr = [2, 2, 2, 2], m = 2, k = 3
        输出：false
        解释：长度为 2 的模式只有 (2,2) ，但是只连续重复 2 次。注意，不能计算重叠的重复次数。

    方法2：枚举
        题目要求我们找到一个连续出现k次且长度为m的子数组。也就是说如果这个子数组的左端点是l，
    那么对于任意offset ∈ [0, m × k)，都有a[l + offset] = a[l + (offset % m)]。
    因此，我们可以枚举左端点l，对于每个l枚举 offset ∈ [0, m × k)，判断是否满足条件即可。

    方法3：双指针
    让两个指针相隔m,一起向前走，如果有题目中要求情况存在，必然会连续走
        m * (k - 1) 次 arr[i] == arr[j];
    记录连续相等的次数，达到要求就返回true,不相等就重置为0
*/
public class NO1566_E_ContainsPattern {

    @Test
    public void test() {
        assert !containsPattern(
            new int[]{1, 2}, 1, 3);
        assert containsPattern(
                new int[]{1, 2, 4, 4, 4, 4}, 1, 3);
        assert containsPattern(
            new int[]{1, 2, 1, 2, 1, 1, 1, 3}, 2, 2);
        assert !containsPattern(
            new int[]{1, 2, 1, 2, 1, 3}, 2, 3);
        assert !containsPattern(
            new int[]{1, 2, 3, 1, 2}, 2, 2);
        assert !containsPattern(
            new int[]{2, 2, 2, 2}, 2, 3);
    }

    // 方法3：双指针法
    public boolean containsPattern(int[] arr, int m, int k) {
        // 2024/3/4 NO.1 双指针
        // 2024/3/6 NO.2 双指针，不是快慢指针，两个指针间隔为m
        // 2024/3/9 NO.3 还是不会做
        return false;
    }

}


















/*
// 方法1：
// 时间复杂度O(n) 空间复杂度O(1)
public boolean containsPattern(int[] arr, int m, int k) {
    if (m * k > arr.length)
        return false;

    int tmp = m;
    for (int i = m; i < arr.length; i++) {
        if (arr[i] == arr[i - m])
            tmp ++;
        else
            tmp = m;

        if (tmp / m == k)
            return true;
    }
    return false;
}


// 方法2：
public boolean containsPattern(int[] arr, int m, int k) {
    for (int i = 0; i <= arr.length - m * k; i++) {
        int j;
        for (j = 0; j < m * k; j++)
            if (arr[i + j] != arr[i + j % m])
                break;

        if (j == m * k)
            return true;
    }
    return false;
}


// 方法3：双指针法
public boolean containsPattern(int[] arr, int m, int k) {
    if (arr.length < m * k)
        return false;

    int i = 0;
    int j = m;
    int count = 0;
    while (j < arr.length) {
        if (arr[i] == arr[j]) {
            // 连续走 m * (k - 1) 次的 arr[i] == arr[j]
            if (++count == m * (k - 1))
                return true;
        } else {
            count = 0;
        }
        i++;
        j++;
    }
    return false;
}
*/