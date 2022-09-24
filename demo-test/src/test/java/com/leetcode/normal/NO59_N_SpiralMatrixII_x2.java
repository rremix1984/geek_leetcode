/**
 * copyright 2022/1/19
 */
package com.leetcode.normal;

import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;

/**
    (中等)
    59. 螺旋矩阵 II
        给你一个正整数 n ，生成一个包含 1 到 n ^ 2 所有元素，
        且元素按顺时针顺序螺旋排列的 n x n 正方形矩阵 matrix。
    示例 1：
        输入：n = 3
        输出：[[1, 2, 3], [8, 9, 4], [7, 6, 5]]
    示例 2：
        输入：n = 1
        输出：[[1]]
*/
public class NO59_N_SpiralMatrixII_x2 {

    @Test
    public void test() {
        assertArrayEquals(new int[][]{{1, 2, 3},
                                      {8, 9, 4},
                                      {7, 6, 5}}, generateMatrix(3));
        assertArrayEquals(new int[0][0], generateMatrix(0));
    }

    public int[][] generateMatrix(int n) {
        int[][] matrix = new int[n][n];
        return matrix;
    }
}




















/**
// 方法1：
public int[][] generateMatrix(int n) {
    int num = 1;

    int[][] matrix = new int[n][n];

    int left = 0;
    int top = 0;
    int right = n - 1;
    int bottom = n - 1;

    while (left <= right && top <= bottom) {
        for (int i = left; i <= right; i++)
            matrix[top][i] = num++;

        for (int i = top + 1; i <= bottom; i++)
            matrix[i][right] = num++;

        if (left < right && top < bottom) {
            for (int i = right - 1; i > left; i--)
                matrix[bottom][i] = num++;

            for (int i = bottom; i > top; i--)
                matrix[i][left] = num++;
        }
        left++;
        right--;
        top++;
        bottom--;
    }
    return matrix;
}
*/