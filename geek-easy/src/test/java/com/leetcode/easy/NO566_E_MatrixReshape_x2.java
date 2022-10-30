/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;

/**
    (简单)
    566. 重塑矩阵
        在MATLAB中，有一个非常有用的函数reshape，它可以将一个mxn矩阵重塑为另一个大小不同
        （r x c）的新矩阵，但保留其原始数据。
        给你一个由二维数组 mat 表示的 m x n 矩阵，以及两个正整数 r 和 c ，
        分别表示想要的重构的矩阵的行数和列数。
        重构后的矩阵需要将原始矩阵的所有元素以相同的行遍历顺序填充。
        如果具有给定参数的reshape操作是可行且合理的，
        则输出新的重塑矩阵；否则，输出原始矩阵。
    示例 1：
        输入：mat = {{1, 2}, {3, 4}},  r = 1,  c = 4
        输出：{{1, 2, 3, 4}}
    示例 2：
        输入：mat = {{1, 2}, {3, 4}},  r = 2,  c = 4
        输出：{{1, 2}, {3, 4}}
*/
public class NO566_E_MatrixReshape_x2 {

    @Test
    public void test() {
        assertArrayEquals(new int[][]{{1, 2, 3, 4}},
                matrixReshape(new int[][]{{1, 2}, {3, 4}},1,4));
        assertArrayEquals(new int[][]{{1, 2}, {3, 4}},
                matrixReshape(new int[][]{{1, 2}, {3, 4}},2,4));
    }

    public int[][] matrixReshape(int[][] nums, int r, int c) {
        int[][] ans = new int[r][c];
        return ans;
    }

}



















/**
// 方法1：
public int[][] matrixReshape(int[][] nums, int r, int c) {
    int m = nums.length;
    int n = nums[0].length;
    if (m * n != r * c)
        return nums;

    int[][] ans = new int[r][c];
    for (int x = 0; x < m * n; x++)
        ans[x / c][x % c] = nums[x / n][x % n];

    return ans;
}
*/