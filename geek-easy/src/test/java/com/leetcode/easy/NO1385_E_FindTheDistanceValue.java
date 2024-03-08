/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;
import static java.lang.Math.abs;

/**
    [ARRAY] |
    (简单)
    1385. 两个数组间的距离值
        给你两个整数数组arr1，arr2和一个整数d，请你返回两个数组之间的距离值。
        「距离值」定义为符合此距离要求的元素数目：对于元素arr1[i]，
        不存在任何元素 arr2[j] 满足 |arr1[i] - arr2[j]| <= d。
    示例 1：
        输入：arr1 = {4, 5, 8},  arr2 = {10, 9, 1, 8},  d = 2
        输出：2
        解释：
        对于 arr1[0] = 4 我们有：
            |4 - 10| = 6 > d = 2
            |4 - 9| = 5 > d = 2
            |4 - 1| = 3 > d = 2
            |4 - 8| = 4 > d = 2
        所以 arr1[0] = 4 符合距离要求
        对于 arr1[1] = 5 我们有：
            |5 - 10| = 5 > d = 2
            |5 - 9| = 4 > d = 2
            |5 - 1| = 4 > d = 2
            |5 - 8| = 3 > d = 2
        所以 arr1[1] = 5 也符合距离要求
        对于 arr1[2] = 8 我们有：
            |8-10|=2 <= d=2
            |8-9|=1 <= d=2
            |8-1|=7 > d=2  <<<<<<<< 所以 arr1[2] 就不是了
            |8-8|=0 <= d=2
        存在距离小于等于 2 的情况，不符合距离要求
        故而只有 arr1{0}=4 和 arr1{1}=5 两个符合距离要求，距离值为 2
    示例 2：
        输入：arr1 = {1, 4, 2, 3},  arr2 = {-4, -3, 6, 10, 20, 30},  d = 3
        输出：2
    示例 3：
        输入：arr1 = {2, 1, 100, 3},  arr2 = {-5, -2, 10, -3, 7},  d = 6
        输出：1
*/
public class NO1385_E_FindTheDistanceValue {

    @Test
    public void test() {
        assert 2 == findTheDistanceValue(
            new int[]{4, 5, 8}, new int[]{10, 9, 1, 8},2);
        assert 2 == findTheDistanceValue(
            new int[]{1, 4, 2, 3}, new int[]{-4, -3, 6, 10, 20, 30},3);
        assert 1 == findTheDistanceValue(
            new int[]{2, 1, 100, 3}, new int[]{-5, -2, 10, -3, 7},6);
    }

    public int findTheDistanceValue(int[] arr1, int[] arr2, int d) {
        // 2024/3/6 NO.1
        int res = 0;
        return res;
    }

}














/*
// 方法1：
public int findTheDistanceValue(int[] arr1, int[] arr2, int d) {
    int res = 0;
    for (int x : arr1) {
        boolean ok = true;
        for (int y : arr2)
            // 每一个都要满足 绝对值 > d 的条件
            // 所以用 "&" 号
            ok &= abs(x - y) > d;

        if (ok)
            res++;
    }
    return res;
}
*/