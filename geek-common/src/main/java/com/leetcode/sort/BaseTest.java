/**
 * copyright 2022/1/19
 */
package com.leetcode.sort;

public class BaseTest {

    // Math.random() -> [0, 1) 所有的小数，等概率返回
    // Math.random() * N -> [0, n) 所有小数，等概率返回
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxValue + 1) * Math.random())
                    - (int) (maxValue * Math.random());
        }
        return arr;
    }

}
