/**
 * copyright 2022/1/19
 */
package com.leetcode.normal;

import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;

/**
    [MATRIX]
    (中等)
    73. 矩阵置零
        给定一个 m x n 的矩阵，如果一个元素为 0 ，
        则将其所在行和列的所有元素都设为 0 。请使用 原地 算法。
    示例 1：
        输入：matrix = [[1,1,1],
                       [1,0,1],
                       [1,1,1]]
        输出：[[1,0,1],
              [0,0,0],
              [1,0,1]]
    示例 2：
        输入：matrix = [[0,1,2,0],
                       [3,4,5,2],
                       [1,3,1,5]]
        输出：[[0,0,0,0],
              [0,4,5,0],
              [0,3,1,0]]
    提示：
        m == matrix.length
        n == matrix[0].length
        1 <= m, n <= 200
        -231 <= matrix[i][j] <= 231 - 1
*/
public class NO073_N_SetZeroes_x2 {

    @Test
    public void test() {
        int[][] source = new int[][]{{1, 1, 1}, {1, 0, 1}, {1, 1, 1}};
        int[][] target = new int[][]{{1, 0, 1}, {0, 0, 0}, {1, 0, 1}};
        setZeroes(source);
        assertArrayEquals(target,  source);

        int[][] source2 = new int[][]{{0, 1, 2, 0}, {3, 4, 5, 2}, {1, 3, 1, 5}};
        int[][] target2 = new int[][]{{0, 0, 0, 0}, {0, 4, 5, 0}, {0, 3, 1, 0}};
        setZeroes(source2);
        assertArrayEquals(target2, source2);
    }

    public void setZeroes(int[][] matrix) {

    }

}














/**
// 方法1：
public void setZeroes(int[][] matrix) {
    boolean[] row = new boolean[matrix.length];
    boolean[] col = new boolean[matrix[0].length];

    for (int i = 0; i < matrix.length; i++)
        for (int j = 0; j < matrix[0].length; j++)
            if (matrix[i][j] == 0)
                row[i] = col[j] = true;

    for (int i = 0; i < matrix.length; i++)
        for (int j = 0; j < matrix[0].length; j++)
            if (row[i] || col[j])
                matrix[i][j] = 0;
}
*/