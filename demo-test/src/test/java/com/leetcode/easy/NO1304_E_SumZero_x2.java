/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;
import java.util.Arrays;

/**
    (简单)
    1304. 和为零的 N 个不同整数
        给你一个整数 n，请你返回 任意 一个由 n 个 各不相同 的整数组成的数组，并且这 n 个数相加和为 0 。
    示例 1：
        输入：n = 5
        输出：{-7, -1, 1, 3, 4}
        解释：这些数组也是正确的 {-5, -1, 1, 2, 3}，{-3, -1, 2, -2, 4}。
    示例 2：
        输入：n = 3
        输出：{-1, 0, 1}
    示例 3：
        输入：n = 1
        输出：{0}
*/
public class NO1304_E_SumZero_x2 {

    @Test
    public void test() {
        assert 0 == Arrays.stream(sumZero(5)).sum();
        assert 0 == Arrays.stream(sumZero(3)).sum();
        assert 0 == Arrays.stream(sumZero(1)).sum();
    }

    public int[] sumZero(int n) {
        int[] ans = new int[n];
        int sum = 0;
        for(int i = 1; i < n; i++) {
            ans[i] = i;
            sum -=i;
        }
        ans[0] = sum;
        return ans;
    }

}
















/**
// 方法1：
public int[] sumZero(int n) {
    int[] arr = new int[n];
    for (int left = 0, right = n - 1, num = n / 2; left < right; left++, right--, num--) {
        arr[left] = -num;
        arr[right] = num;
    }
    return arr;
}

// 方法2：
public int[] sumZero(int n) {
    int[] ans = new int[n];
    int sum = 0;
    for(int i = 1; i < n; i++) {
        ans[i] = i;
        sum -=i;
    }
    ans[0] = sum;
    return ans;
}
*/