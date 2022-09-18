/**
 * copyright 2022/1/19
 */
package com.leetcode.interval;

import com.leetcode.sort.BaseTest;
import org.junit.Test;

import java.util.Arrays;

import static com.leetcode.sort.BaseTest.generateRandomArray;
import static com.leetcode.util.LogUtil.info;
import static java.lang.Math.max;

public class Code08_GetMax {

    @Test
    public void test() {
        int[] origin = generateRandomArray(100,100);
        assert Arrays.stream(origin).max().getAsInt() ==
            getMax(origin);
    }

    public static int getMax(int[] arr) {
        return process(arr, 0, arr.length - 1);
    }

    private static int process(int[] arr, int L, int R) {
        if (L == R)
            return arr[L];

        int mid = L + (R - L) / 2;

        int lMAX = process(arr, L, mid);
        int rMAX = process(arr,mid + 1, R);
        return max(lMAX, rMAX);
    }

}
