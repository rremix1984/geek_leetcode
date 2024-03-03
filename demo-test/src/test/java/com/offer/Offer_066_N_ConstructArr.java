/**
 * copyright 2022/1/19
 */
package com.offer;

import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;

/**
    [ARRAY]
    (中等)
    剑指 Offer 66. 构建乘积数组
        给定一个数组 A[0,1,…,n-1]，请构建一个数组 B[0,1,…,n-1]，
        其中 B[i] 的值是数组 A 中除了下标 i 以外的元素的积,
        即 B[i]=A[0]×A[1]×…×A[i-1]×A[i+1]×…×A[n-1]。不能使用除法。
    示例:
        输入: {1, 2, 3, 4, 5}
        输出: {120, 60, 40, 30, 24}
*/
public class Offer_066_N_ConstructArr {

    @Test
    public void test() {
        assertArrayEquals(new int[]{120, 60, 40, 30, 24}, constructArr(new int[]{1, 2, 3, 4, 5}));
        assertArrayEquals(new int[]{}, constructArr(new int[]{}));
    }

    public int[] constructArr(int[] a) {
        int[] ans = new int[a.length];

        if (a.length == 0)
            return a;

        // answer[i] 表示索引 i 左侧所有元素的乘积
        // 因为索引为 '0' 的元素左侧没有元素， 所以 answer[0] = 1
        ans[0] = 1;
        for (int i = 1; i < a.length; i++)
            ans[i] = a[i - 1] * ans[i - 1];

        // res 为右侧所有元素的乘积
        // 刚开始右边没有元素，所以 R = 1
        int res = 1;
        for (int i = a.length - 1; i >= 0; i--) {
            // 对于索引 i，左边的乘积为 answer[i]，右边的乘积为 res
            ans[i] *= res;

            // res 需要包含右边所有的乘积，所以计算下一个结果时需要将当前值乘到 res 上
            res *= a[i];
        }
        return ans;
    }

}


















/**
// 方法1：
public int[] constructArr(int[] a) {
    int[] ans = new int[a.length];

    if (a.length == 0)
        return a;

    // answer[i] 表示索引 i 左侧所有元素的乘积
    // 因为索引为 '0' 的元素左侧没有元素， 所以 answer[0] = 1
    ans[0] = 1;
    for (int i = 1; i < a.length; i++)
        ans[i] = a[i - 1] * ans[i - 1];

    // res 为右侧所有元素的乘积
    // 刚开始右边没有元素，所以 R = 1
    int res = 1;
    for (int i = a.length - 1; i >= 0; i--) {
        // 对于索引 i，左边的乘积为 answer[i]，右边的乘积为 res
        ans[i] *= res;

        // res 需要包含右边所有的乘积，所以计算下一个结果时需要将当前值乘到 res 上
        res *= a[i];
    }
    return ans;
}
*/