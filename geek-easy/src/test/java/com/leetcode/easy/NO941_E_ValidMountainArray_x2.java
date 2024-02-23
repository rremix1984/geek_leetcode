/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

/**
    [ARRAY]
    (简单)
    941. 有效的山脉数组
        给定一个整数数组 arr，如果它是有效的山脉数组就返回 true，否则返回 false。
        让我们回顾一下，如果 arr 满足下述条件，那么它是一个山脉数组：
        arr.length >= 3
        在 0 < i < arr.length - 1 条件下，存在 i 使得：
        arr[0] < arr[1] < ... arr[i-1] < arr[i]
        arr[i] > arr[i+1] > ... > arr[arr.length - 1]
    示例 1：
        输入：arr = [2, 1]
        输出：false
    示例 2：
        输入：arr = [3, 5, 5]
        输出：false
    示例 3：
        输入：arr = [0, 3, 2, 1]
        输出：true
    提示：
        1 <= arr.length <= 104
        0 <= arr[i] <= 104
*/
public class NO941_E_ValidMountainArray_x2 {

    @Test
    public void test() {
        assert !validMountainArray(new int[]{2, 1});
        assert !validMountainArray(new int[]{3, 5, 5});
        assert validMountainArray(new int[]{0, 3, 2, 1});
    }

    public boolean validMountainArray(int[] arr) {
        return false;
    }

}



















/**
// 方法1：
public boolean validMountainArray(int[] arr) {
    int idx = 0;

    // 递增扫描
    while (idx < arr.length - 1 && arr[idx] < arr[idx + 1])
        idx++;

    // 最高点不能是数组的第一个位置或最后一个位置
    if (idx == 0 || idx == arr.length - 1)
        return false;

    // 递减扫描
    while (idx < arr.length - 1 && arr[idx] > arr[idx + 1])
        idx++;

    // 如果遍历到最后了，就代表可以做到
    return idx == arr.length - 1;
}
*/