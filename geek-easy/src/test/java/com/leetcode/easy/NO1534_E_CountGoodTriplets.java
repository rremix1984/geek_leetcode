/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;
import static java.lang.Math.abs;

/**
    [ARRAY] |
    (简单)
    1534. 统计好三元组
        给你一个整数数组arr，以及 a、b、c 三个整数。请你统计其中好三元组的数量。
        如果三元组 (arr[i], arr[j], arr[k]) 满足下列全部条件，则认为它是一个好三元组。
          1）0 <= i < j < k < arr.length
          2）|arr[i] - arr[j]| <= a
          3）|arr[j] - arr[k]| <= b
          4）|arr[i] - arr[k]| <= c
        其中 |x| 表示 x 的绝对值。返回 好三元组的数量 。
    示例 1：
        输入：arr = {3, 0, 1, 1, 9, 7},  a = 7,  b = 2,  c = 3
        输出：4
        解释：一共有 4 个好三元组：{(3, 0, 1),  (3, 0, 1),  (3, 1, 1),  (0, 1, 1)} 。
    示例 2：
        输入：arr = {1, 1, 2, 2, 3},  a = 0,  b = 0,  c = 1
        输出：0
        解释：不存在满足所有条件的三元组。
*/
@SuppressWarnings("all")
public class NO1534_E_CountGoodTriplets {

    @Test
    public void test() {
        assert 4 == countGoodTriplets(
            new int[]{3, 0, 1, 1, 9, 7}, 7, 2, 3);
        assert 0 == countGoodTriplets(
            new int[]{1, 1, 2, 2, 3}, 0, 0, 1);
        assert 12 == countGoodTriplets(
            new int[]{7, 3, 7, 3, 12, 1, 12, 2, 3},5, 8, 1);
    }

    public int countGoodTriplets(int[] arr, int a, int b, int c) {
        int res = 0;
        for (int i = 0; i < arr.length; i++)
            for (int j = i + 1; j < arr.length; j++) {
                if (abs(arr[i] - arr[j]) > a)
                    continue;

                for (int k = j + 1; k < arr.length; k++) {
                    if(abs(arr[j] - arr[k]) > b)
                        continue; // 条件不满足提前退出

                    if(abs(arr[i] - arr[k]) <= c)
                        res++; // 满足好三元组条件
                }
            }
        return res;
    }

}

















/*
// 方法1：
public int countGoodTriplets(int[] arr, int a, int b, int c) {
    int res = 0;
    for (int i = 0; i < arr.length; i++)
        for (int j = i + 1; j < arr.length; j++) {
            if (abs(arr[i] - arr[j]) > a)
                continue;

            for (int k = j + 1; k < arr.length; k++) {
                if(abs(arr[j] - arr[k]) > b)
                    continue; // 条件不满足提前退出

                if(abs(arr[i] - arr[k]) <= c)
                    res++; // 满足好三元组条件
            }
        }
    return res;
}
*/