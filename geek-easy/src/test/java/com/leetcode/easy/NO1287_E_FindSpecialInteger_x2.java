/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

/**
    [ARRAY]
    (简单)
    1287. 有序数组中出现次数超过 25% 的元素
        给你一个非递减的 有序 整数数组，已知这个数组中恰好有一个整数，它的出现次数超过数组元素总数的 25%。
        请你找到并返回这个整数
    示例：
        输入：arr = {1, 2, 2, 6, 6, 6, 6, 7, 10}
        输出：6
    提示：
        1 <= arr.length <= 10^4
        0 <= arr[i] <= 10^5
*/
public class NO1287_E_FindSpecialInteger_x2 {

    @Test
    public void test() {
        assert 6 == findSpecialInteger(new int[]{1, 2, 2, 6, 6, 6, 6, 7, 10});
    }

    public int findSpecialInteger(int[] arr) {
        return -1;
    }
    
}














/**
public int findSpecialInteger(int[] arr) {
    int step = arr.length / 4;
    for (int i = 0; i < arr.length - step; i++)
        if (arr[i] == arr[i + step])
            return arr[i];

    return -1;
}
*/