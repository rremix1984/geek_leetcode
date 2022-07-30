/**
 * copyright 2022/1/19
 */
package com.leetcode.undo;

import org.junit.Test;
import static com.leetcode.util.LogUtil.info;

/**
    363. 矩形区域不超过 K 的最大数值和
        给你一个 m x n 的矩阵 matrix 和一个整数 k ，找出并返回矩阵内部矩形区域的不超过 k 的最大数值和。
        题目数据保证总会存在一个数值和不超过 k 的矩形区域。
    示例 1：
        输入：matrix = [[1, 0, 1],[0, -2, 3]], k = 2
        输出：2
        解释：蓝色边框圈出来的矩形区域 [[0, 1], [-2, 3]] 的数值和是 2，且 2 是不超过 k 的最大数字（k = 2）。
    示例 2：
        输入：matrix = [[2,2,-1]], k = 3
        输出：3
*/
public class NO363_MaxSumOfRectangleNoLargerThanK {

    @Test
    public void test() {
        info(maxSumSubmatrix(
                new int[][]{{1,  0, 1},
                            {0, -2, 3}}, 2));// 2
        info(maxSumSubmatrix(
                new int[][]{{2, 2, -1}}, 3
        ));// 3
    }

    public int maxSumSubmatrix(int[][] matrix, int k) {
        return -1;
    }

}













/**
// 方法1：动态规划
public int maxSumSubmatrix(int[][] matrix, int k) {
    int rows = matrix.length, cols = matrix[0].length, max = Integer.MIN_VALUE;
    // O(cols ^ 2 * rows)
    for (int l = 0; l < cols; l++) { // 枚举左边界
        int[] rowSum = new int[rows]; // 左边界改变才算区域的重新开始
        for (int r = l; r < cols; r++) { // 枚举右边界
            for (int i = 0; i < rows; i++) { // 按每一行累计到 dp
                rowSum[i] += matrix[i][r];
            }
            max = Math.max(max, dpmax(rowSum, k));
            if (max == k) return k; // 尽量提前
        }
    }
    return max;
}

// 在数组 arr 中，求不超过 k 的最大值
private int dpmax(int[] arr, int k) {
    int rollSum = arr[0], rollMax = rollSum;
    // O(rows)
    for (int i = 1; i < arr.length; i++) {
        if (rollSum > 0) rollSum += arr[i];
        else rollSum = arr[i];
        if (rollSum > rollMax) rollMax = rollSum;
    }
    if (rollMax <= k) return rollMax;
    // O(rows ^ 2)
    int max = Integer.MIN_VALUE;
    for (int l = 0; l < arr.length; l++) {
        int sum = 0;
        for (int r = l; r < arr.length; r++) {
            sum += arr[r];
            if (sum > max && sum <= k) max = sum;
            if (max == k) return k; // 尽量提前
        }
    }
    return max;
}
*/