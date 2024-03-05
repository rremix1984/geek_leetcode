/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

/**
    [MATRIX]
    (简单)
    2133. 检查是否每一行每一列都包含全部整数
        对一个大小为 n x n 的矩阵而言，如果其每一行和每一列都包含从 1 到 n 的 全部 整数（含 1 和 n），则认为该矩阵是一个 有效 矩阵。
        给你一个大小为 n x n 的整数矩阵 matrix ，请你判断矩阵是否为一个有效矩阵：如果是，返回 true ；否则，返回 false 。
    示例 1：
        输入：matrix = {{1, 2, 3}, {3, 1, 2}, {2, 3, 1}}
        输出：true
        解释：在此例中，n = 3 ，每一行和每一列都包含数字 1、2、3 。
        因此，返回 true 。
    示例 2：
        输入：matrix = {{1, 1, 1}, {1, 2, 3}, {1, 2, 3}}
        输出：false
        解释：在此例中，n = 3 ，但第一行和第一列不包含数字 2 和 3 。
        因此，返回 false 。
*/
public class NO2133_E_CheckValid_x2 {

    @Test
    public void test() {
        assert checkValid(new int[][]{{1, 2, 3}, {3, 1, 2}, {2, 3, 1}});
        assert !checkValid(new int[][]{{1, 1, 1}, {1, 2, 3}, {1, 2, 3}});
    }

    public boolean checkValid(int[][] matrix) {
        int n = matrix.length;
        for (int i = 0; i < matrix.length; i++) {
            int[] row = new int[n + 1];
            int[] col = new int[n + 1];
            for (int j = 0; j < n; j++) {
                if (++row[matrix[i][j]] > 1)
                    return false;

                if (++col[matrix[j][i]] > 1)
                    return false;
            }
        }
        return true;
    }

}
















/**
public boolean checkValid(int[][] matrix) {
    int n = matrix.length;
    for (int i = 0; i < n; i++) {
        // 用哈希表同时检查第i+1行，第i+1列
        int[] row = new int[n + 1];
        int[] col = new int[n + 1];
        for (int j = 0; j < n; j++) {
            // 只要行或者列出现重复即无效
            if (++row[matrix[i][j]] > 1)
                return false;

            if (++col[matrix[j][i]] > 1)
                return false;
        }
    }
    return true;
}
*/